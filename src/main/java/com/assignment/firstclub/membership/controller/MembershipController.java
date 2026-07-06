package com.assignment.firstclub.membership.controller;


import com.assignment.firstclub.membership.dto.request.SubscribeRequest;
import com.assignment.firstclub.membership.dto.response.MembershipPlanDetails;
import com.assignment.firstclub.membership.dto.response.SubscriptionResponse;
import com.assignment.firstclub.membership.exception.SubscriptionException;
import com.assignment.firstclub.membership.model.BenefitUseDuring;
import com.assignment.firstclub.membership.model.entity.TierBenefit;
import com.assignment.firstclub.membership.service.MembershipManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.assignment.firstclub.common.Constants.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "/memberships")
@RequiredArgsConstructor
@Tag(name = "Membership", description = "Membership management APIs")
public class MembershipController {

    private final MembershipManagerService membershipManagerService;

    @GetMapping("/plans")
    @Operation(summary = "Get membership plans")
    public ResponseEntity<MembershipPlanDetails> getPlanDetails() {
        return ResponseEntity.ok(membershipManagerService.getPlanDetails());
    }

    @PostMapping("/subscribe")
    @Operation(summary = "Subscribe to a membership plan")
    public ResponseEntity<SubscriptionResponse> subscribe(
            @Valid @RequestBody SubscribeRequest request) throws SubscriptionException {

        return ResponseEntity.ok(membershipManagerService.subscribe(request));
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Get user's active subscription")
    public ResponseEntity<SubscriptionResponse> getSubscription(
            @PathVariable Long userId) throws SubscriptionException {

        return ResponseEntity.ok(membershipManagerService.getSubscriptionDetails(userId));
    }

    @DeleteMapping("/subscriptions/{subscriptionId}")
    @Operation(summary = "Cancel a membership")
    public ResponseEntity<Void> cancelMembership(
            @PathVariable Long subscriptionId) {

        membershipManagerService.cancelMembership(subscriptionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/benefits")
    @Operation(summary = "Get applicable benefits for a user")
    public ResponseEntity<List<TierBenefit>> getBenefits(
            @PathVariable Long userId,
            @RequestParam BenefitUseDuring useDuring) throws SubscriptionException {

        return ResponseEntity.ok(
                membershipManagerService.getBenefits(userId, useDuring));
    }
}
