package com.assignment.firstclub;

import com.assignment.firstclub.common.data.Storage;
import com.assignment.firstclub.membership.dto.request.SubscribeRequest;
import com.assignment.firstclub.membership.dto.response.MembershipPlanDetails;
import com.assignment.firstclub.membership.dto.response.SubscriptionResponse;
import com.assignment.firstclub.membership.exception.SubscriptionException;
import com.assignment.firstclub.membership.mapper.Mapper;
import com.assignment.firstclub.membership.service.*;
import com.assignment.firstclub.membership.model.PlanType;
import com.assignment.firstclub.membership.model.TierType;
import com.assignment.firstclub.user.dto.UserCreateRequest;
import com.assignment.firstclub.user.model.User;
import com.assignment.firstclub.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

public class SubscriptionTest {
    /*
    3. User Actions:
        ● Get Membership Plans and Tier to be selected by the user
        ● Subscribe to a plan (plan + tier).
        ● Upgrade, downgrade(Membership Tier), or cancel a subscription.
        ● Track current membership and expiry.
    * */
    private PlanService planService;
    private TierService tierService;
    private UserService userService;
    private BenefitService benefitService;

    private SubscriptionService subscriptionService;

    private MembershipManagerService membershipManagerService;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        Storage storage = new Storage();
        this.planService = new PlanService(storage);
        this.tierService = new TierService(storage);
        this.userService = new UserService(storage);
        this.benefitService = new BenefitService(storage);
        this.subscriptionService = new SubscriptionService(storage);
        this.membershipManagerService = new MembershipManagerService(planService, tierService, benefitService, subscriptionService, new Mapper() , userService, new UserLockManager());

        setupMembershipPlanTierAndUser();
    }

    private void setupMembershipPlanTierAndUser() {
        planService.addPlan(PlanType.MONTHLY, BigDecimal.valueOf(199));
        planService.addPlan(PlanType.QUARTERLY, BigDecimal.valueOf(399));
        planService.addPlan(PlanType.YEARLY, BigDecimal.valueOf(599));

        tierService.addTier(TierType.SILVER);
        tierService.addTier(TierType.GOLD);
        tierService.addTier(TierType.PLATINUM);
    }

    @Test
    public void testUserAction() {
        try {
            User user1 = userService.createUser(UserCreateRequest.builder().name("JOHN").emailId("john@abc.com").build());
            print(user1);
            User user2 = userService.createUser(UserCreateRequest.builder().name("RAM").emailId("ram@abc.com").build());
            print(user2);
            User user3 = userService.createUser(UserCreateRequest.builder().name("JOE").emailId("joe@abc.com").build());
            print(user3);

            MembershipPlanDetails membershipPlanDetails = membershipManagerService.getPlanDetails();
            print(membershipPlanDetails);

            SubscribeRequest request = SubscribeRequest.builder()
                    .userId(user1.getId())
                    .planId(membershipPlanDetails.getPlans().getFirst().getId())
                    .tierId(membershipPlanDetails.getTierInfo().getFirst().getTierId())
                    .build();

            //User1 subscribed to Monthly plan
            SubscriptionResponse subscriptionResponse = membershipManagerService.subscribe(request);
            print(subscriptionResponse);

            membershipManagerService.upgradeTier(subscriptionResponse.getSubscriptionId(), 2l);

            SubscriptionResponse subscriptionDetails = membershipManagerService.getSubscriptionDetails(user1.getId());
            print(subscriptionDetails);

            //User1 cancel subscriptions
            membershipManagerService.cancelMembership(subscriptionResponse.getSubscriptionId());

            try {
                SubscriptionResponse subscriptionDetails2 = membershipManagerService.getSubscriptionDetails(user2.getId());
            } catch (SubscriptionException e) {
                print(e.getMessage());
            }

            SubscribeRequest request2 = SubscribeRequest.builder()
                    .userId(user1.getId())
                    .planId(membershipPlanDetails.getPlans().getFirst().getId())
                    .tierId(membershipPlanDetails.getTierInfo().getFirst().getTierId())
                    .build();
            //User1 subscribed to Monthly plan
            SubscriptionResponse subscriptionResponse2 = membershipManagerService.subscribe(request2);
            print(subscriptionResponse2);

            membershipManagerService.getSubscriptionDetails(user1.getId());


        } catch (Exception e) {
            print(e);
        }
    }

    @Test
    public void testTiers() {
        try {
            User user4 = userService.createUser(UserCreateRequest.builder().name("AMY").emailId("amy@abc.com").build());
            print(user4);

            MembershipPlanDetails membershipPlanDetails = membershipManagerService.getPlanDetails();
            print(membershipPlanDetails);

            SubscribeRequest request = SubscribeRequest.builder()
                    .userId(user4.getId())
                    .planId(membershipPlanDetails.getPlans().getFirst().getId())
                    .tierId(membershipPlanDetails.getTierInfo().getFirst().getTierId())
                    .build();

            SubscriptionResponse subscriptionResponse = membershipManagerService.subscribe(request);
            print(subscriptionResponse);

            //Add user to cohort

            //Create order and place order

        }catch (Exception e) {
            print(e);
        }
    }



    private void print(Object object) {
        System.out.println(mapper.writeValueAsString(object));
    }
}
