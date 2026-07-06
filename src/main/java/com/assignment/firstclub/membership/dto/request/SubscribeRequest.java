package com.assignment.firstclub.membership.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeRequest {

    @NotNull(message = "userId must not be null")
    Long userId;

    @NotNull(message = "planId must not be null")
    Long planId;

    @NotNull(message = "tierId must not be null")
    Long tierId;
}
