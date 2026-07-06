package com.assignment.firstclub.membership.controller;


import com.assignment.firstclub.membership.model.entity.UserCohort;
import com.assignment.firstclub.membership.service.CohortManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.assignment.firstclub.common.Constants.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "/cohorts")
@RequiredArgsConstructor
@Tag(name = "Cohorts", description = "User Cohort APIs")
public class CohortController {

    private final CohortManagerService cohortManagerService;

    @PostMapping("/users/{userId}/{cohortId}")
    @Operation(summary = "Assign a user to a cohort")
    public ResponseEntity<UserCohort> assign(
            @PathVariable Long userId,
            @PathVariable Long cohortId) {

        return ResponseEntity.ok(cohortManagerService.assign(userId, cohortId));
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Get cohorts for a user")
    public ResponseEntity<Set<Long>> getUserCohorts(@PathVariable Long userId) {
        return ResponseEntity.ok(cohortManagerService.getUserCohort(userId));
    }
}