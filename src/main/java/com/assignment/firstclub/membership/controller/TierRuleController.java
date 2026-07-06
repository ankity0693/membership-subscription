package com.assignment.firstclub.membership.controller;

import com.assignment.firstclub.common.exception.MembershipApplicationException;
import com.assignment.firstclub.membership.model.entity.TierRule;
import com.assignment.firstclub.membership.rule.impl.TierRuleEngineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tier-rules")
@Tag(name = "Tier Rules", description = "Tier eligibility rule management APIs")
public class TierRuleController {

    private final TierRuleEngineService tierRuleEngineService;

    public TierRuleController(TierRuleEngineService tierRuleEngineService) {
        this.tierRuleEngineService = tierRuleEngineService;
    }

    @PostMapping
    @Operation(summary = "Create a tier eligibility rule")
    public TierRule createRule(@Valid @RequestBody TierRule tierRule)
            throws MembershipApplicationException {

        return tierRuleEngineService.create(tierRule);
    }
}