package com.assignment.firstclub.membership.dto.benefit;

import com.assignment.firstclub.membership.model.BenefitType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

@Data
@Builder
public class AppliedBenefit {

    /**
     * Benefit type
     * Example: DISCOUNT, FREE_DELIVERY
     */
    private BenefitType type;

    /**
     * User-friendly title
     * Example: "10% Membership Discount"
     */
    private String title;

    /**
     * Optional description
     */
    private String description;

    /**
     * Monetary savings.
     * Null if the benefit is non-monetary.
     */
    private BigDecimal amount;

    /**
     * Whether this benefit was successfully applied.
     */
    @Builder.Default
    private boolean applied = true;

    /**
     * Additional benefit-specific information.
     *
     * Examples:
     *  percentage -> 10
     *  coupon -> "GOLD100"
     *  category -> "ELECTRONICS"
     *  deliveryType -> "EXPRESS"
     */
    @Builder.Default
    private Map<String, Object> metadata = Collections.emptyMap();
}
