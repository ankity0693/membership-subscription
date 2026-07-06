package com.assignment.firstclub.membership.service;

import com.assignment.firstclub.common.data.CrudOperation;
import com.assignment.firstclub.common.data.Storage;
import com.assignment.firstclub.membership.exception.TierException;
import com.assignment.firstclub.membership.model.TierStatus;
import com.assignment.firstclub.membership.model.TierType;
import com.assignment.firstclub.membership.model.entity.Tier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class TierService extends CrudOperation<Tier> {
    @Autowired
    public TierService(Storage storage) {
        super(storage, Tier.class);
    }

    public Tier addTier(TierType type) {
       Tier tier =  Tier.builder().tierType(type).status(TierStatus.ACTIVE).build();
       return create(tier);
    }

    public Tier getNextUpgraded(Long tierId) throws TierException {
        return getAdjacentTier(tierId, 1);
    }

    public Tier getNextDowngraded(Long tierId) throws TierException {
        return getAdjacentTier(tierId, -1);
    }

    public List<Tier> getTierByPriorityAsc() {
        return getAll().stream()
                .sorted(Comparator.comparingInt(t -> t.getTierType().getPriority()))
                .toList();
    }

    private Tier getAdjacentTier(Long tierId, int offset) throws TierException {
        List<Tier> tiers = getAll().stream()
                .sorted(Comparator.comparingInt(t -> t.getTierType().getPriority()))
                .toList();

        int index = IntStream.range(0, tiers.size())
                .filter(i -> tiers.get(i).getId().equals(tierId))
                .findFirst()
                .orElseThrow(() -> new TierException("Tier not found"));

        int targetIndex = index + offset;

        if (targetIndex < 0 || targetIndex >= tiers.size()) {
            throw new TierException(
                    offset > 0
                            ? "Upgrade cannot be done"
                            : "Downgrade cannot be done");
        }

        return tiers.get(targetIndex);
    }
}
