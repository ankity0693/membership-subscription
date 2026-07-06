package com.assignment.firstclub.membership.service;

import com.assignment.firstclub.common.data.CrudOperation;
import com.assignment.firstclub.common.data.Storage;
import com.assignment.firstclub.membership.model.entity.TierBenefit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BenefitService extends CrudOperation<TierBenefit> {
    public BenefitService(Storage storage) {
        super(storage, TierBenefit.class);
    }

    public TierBenefit addBenefit(TierBenefit tierBenefit) {
        return create(tierBenefit);
    }

    public List<TierBenefit> fetchAllActiveBenefits() {
        return getAll().stream().filter(TierBenefit::getIsActive).collect(Collectors.toList());
    }


    public List<TierBenefit> getBenefitsForTier(Long tierId) {
        List<TierBenefit> benefits = fetchAllActiveBenefits();
        return benefits.stream().filter(tierBenefit -> tierBenefit.getTierId().equals(tierId)).toList();
    }
}
