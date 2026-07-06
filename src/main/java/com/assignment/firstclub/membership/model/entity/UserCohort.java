package com.assignment.firstclub.membership.model.entity;

import com.assignment.firstclub.common.data.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCohort implements BaseEntity {
    private Long id;
    private Long userId;
    private Long cohortId;
}