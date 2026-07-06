package com.assignment.firstclub.membership.model;

public enum TierType {
    SILVER(1),
    GOLD(10),
    PLATINUM(30);

    private Integer priority;

    TierType(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return this.priority;
    }
}
