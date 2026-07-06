package com.assignment.firstclub.membership.model.entity;

import com.assignment.firstclub.common.data.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cohort implements BaseEntity {
    Long id;
    String name;
    Boolean isActive;
}
