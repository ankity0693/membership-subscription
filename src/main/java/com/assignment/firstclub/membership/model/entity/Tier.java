package com.assignment.firstclub.membership.model.entity;

import com.assignment.firstclub.common.data.BaseEntity;
import com.assignment.firstclub.membership.model.TierStatus;
import com.assignment.firstclub.membership.model.TierType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tier implements BaseEntity {
    Long id;
    TierType tierType;
    TierStatus status;
}
