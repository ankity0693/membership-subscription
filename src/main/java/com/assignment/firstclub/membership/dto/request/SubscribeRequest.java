package com.assignment.firstclub.membership.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeRequest {
    Long userId;
    Long planId;
    Long tierId;
}
