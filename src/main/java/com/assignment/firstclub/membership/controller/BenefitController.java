package com.assignment.firstclub.membership.controller;

import com.assignment.firstclub.membership.model.entity.TierBenefit;
import com.assignment.firstclub.membership.service.BenefitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.assignment.firstclub.common.Constants.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "/benefits")
@RequiredArgsConstructor
@Tag(name = "Benefits", description = "Membership Benefit APIs")
public class BenefitController {

    private final BenefitService benefitService;

    @PostMapping
    @Operation(summary = "Add a tier benefit")
    public ResponseEntity<TierBenefit> addBenefit(@RequestBody TierBenefit benefit) {
        return ResponseEntity.ok(benefitService.addBenefit(benefit));
    }

    @GetMapping
    @Operation(summary = "Get all active benefits")
    public ResponseEntity<List<TierBenefit>> getActiveBenefits() {
        return ResponseEntity.ok(benefitService.fetchAllActiveBenefits());
    }

    @GetMapping("/tier/{tierId}")
    @Operation(summary = "Get benefits for a tier")
    public ResponseEntity<List<TierBenefit>> getBenefitsForTier(@PathVariable Long tierId) {
        return ResponseEntity.ok(benefitService.getBenefitsForTier(tierId));
    }
}