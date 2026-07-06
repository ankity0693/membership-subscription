package com.assignment.firstclub.membership.controller;

import com.assignment.firstclub.membership.model.PlanType;
import com.assignment.firstclub.membership.model.TierType;
import com.assignment.firstclub.membership.model.entity.MembershipPlan;
import com.assignment.firstclub.membership.model.entity.Tier;
import com.assignment.firstclub.membership.service.PlanService;
import com.assignment.firstclub.membership.service.TierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.assignment.firstclub.common.Constants.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "/plans")
@RequiredArgsConstructor
@Tag(name = "Plans", description = "Membership plan APIs")
public class PlanController {

    private final PlanService planService;
    private final TierService tierService;

    @PostMapping
    @Operation(summary = "Create a membership plan")
    public ResponseEntity<MembershipPlan> addPlan(
            @RequestParam PlanType planType,
            @RequestParam BigDecimal price) {

        return ResponseEntity.ok(planService.addPlan(planType, price));
    }

    @PostMapping("/tiers")
    @Operation(summary = "Create a tier")
    public ResponseEntity<Tier> addTier(@RequestParam TierType tierType) {
        return ResponseEntity.ok(tierService.addTier(tierType));
    }

    @GetMapping("/tiers")
    @Operation(summary = "Get all tiers by priority")
    public ResponseEntity<List<Tier>> getTiers() {
        return ResponseEntity.ok(tierService.getTierByPriorityAsc());
    }
}