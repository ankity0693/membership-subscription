package com.assignment.firstclub.membership.rule.impl;

import com.assignment.firstclub.common.Constants;
import com.assignment.firstclub.common.data.CrudOperation;
import com.assignment.firstclub.common.data.Storage;
import com.assignment.firstclub.common.exception.MembershipApplicationException;
import com.assignment.firstclub.common.exception.UneligibleTierException;
import com.assignment.firstclub.membership.rule.RuleEvaluator;
import com.assignment.firstclub.membership.rule.UserMetrics;
import com.assignment.firstclub.membership.service.CohortManagerService;
import com.assignment.firstclub.membership.service.TierService;
import com.assignment.firstclub.membership.model.entity.Tier;
import com.assignment.firstclub.membership.model.entity.TierRule;
import com.assignment.firstclub.order.dao.OrderDao;
import com.assignment.firstclub.order.dto.OrderDetails;
import com.assignment.firstclub.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class TierRuleEngineService extends CrudOperation<TierRule> {

    TierService tierService;
    OrderDao orderDao;
    CohortManagerService cohortManagerService;
    RuleEvaluatorFactory ruleEvaluatorFactory;

    public TierRuleEngineService(Storage storage, TierService tierService,
                                 OrderDao orderDao, CohortManagerService cohortManagerService,
                                 RuleEvaluatorFactory ruleEvaluatorFactory) {
        super(storage, TierRule.class);
        this.tierService = tierService;
        this.orderDao = orderDao;
        this.cohortManagerService = cohortManagerService;
        this.ruleEvaluatorFactory = ruleEvaluatorFactory;
    }

    public Tier evaluateTier(Long userId) throws MembershipApplicationException {
        List<Tier> tiers = tierService.getTierByPriorityAsc();
        return computeTier(tiers.reversed(), userId);
    }

    public List<TierRule> getRulesByTierId(Long tierId) {
        return getAll().stream().filter(rule -> rule.getTierId().equals(tierId)).toList();
    }

    private Tier computeTier(List<Tier> tiers, Long userId) throws MembershipApplicationException {
        UserMetrics userMetrics = formUserMetrics(userId);
        for(Tier tier : tiers) {
            List<TierRule> rules = getRulesByTierId(tier.getId());
            boolean isEligible = true;
            for(TierRule rule : rules) {
                RuleEvaluator evaluator = ruleEvaluatorFactory.getRuleEvaluator(rule.getType());
                boolean result = evaluator.evaluate(userMetrics, rule);
                if(!result) {
                    isEligible = false;
                    break;
                }
            }
            if(isEligible) return tier;
        }
        throw new UneligibleTierException(userId);
    }


    private UserMetrics formUserMetrics(Long userId) throws MembershipApplicationException {
        OrderDetails details = orderDao.getOrderDetails(userId, LocalDate.now().minusMonths(Constants.MONTH_DURATION), LocalDate.now());
        Set<Long> cohortIds = cohortManagerService.getUserCohort(userId);
        return new UserMetrics(userId,details.ordersCount(), details.expense(), cohortIds);
    }
}
