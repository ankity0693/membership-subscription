package com.assignment.firstclub.membership.model.entity;

import com.assignment.firstclub.common.data.BaseEntity;
import com.assignment.firstclub.membership.model.RuleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TierRule implements BaseEntity {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long id;
    Long tierId;
    RuleType type;
    Integer value;
    Set<Long> cohortIds;
}
