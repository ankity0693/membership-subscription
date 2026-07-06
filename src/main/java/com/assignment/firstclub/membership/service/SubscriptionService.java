package com.assignment.firstclub.membership.service;

import com.assignment.firstclub.common.data.CrudOperation;
import com.assignment.firstclub.common.data.Storage;
import com.assignment.firstclub.membership.exception.SubscriptionException;
import com.assignment.firstclub.membership.model.SubscriptionStatus;
import com.assignment.firstclub.membership.model.entity.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService extends CrudOperation<Subscription> {
    @Autowired
    public SubscriptionService(Storage storage) {
        super(storage, Subscription.class);
    }

    public void validateNoActiveSubscription(Long userId) throws SubscriptionException {
        Optional<Subscription> subscription = getActiveSubscription(userId);
        if(subscription.isPresent()) {
            throw new SubscriptionException("User is already subscribed to subscription: " + subscription.get().getId());
        }
    }

    public Optional<Subscription> getActiveSubscription(Long userId) throws SubscriptionException {
        List<Subscription> subscriptions = getAllSubscriptionForUser(userId);
        List<Subscription> activeSubscription = subscriptions.stream().filter(subs -> subs.getStatus().equals(SubscriptionStatus.ACTIVE)).toList();
        if(activeSubscription.size() > 1) {
            throw new SubscriptionException("Multiple subscription found for user: " + userId);
        }

        if(activeSubscription.size() == 1) {
            return Optional.of(activeSubscription.getFirst());
        }
        return Optional.empty();
    }

    public List<Subscription> getAllSubscriptionForUser(Long userId) {
        return getAll().stream().filter(subs -> subs.getUserId().equals(userId)).toList();
    }

    public Subscription updateTier(Long subscriptionId, Long tierId) {
        Subscription subscription = get(subscriptionId);
        subscription.setTierId(tierId);
        update(subscription);
        return subscription;
    }

    public void cancelSubscription(Long subscriptionId) {
        Subscription subscription = get(subscriptionId);
        subscription.setStatus(SubscriptionStatus.CANCELLED);
        update(subscription);
    }
}
