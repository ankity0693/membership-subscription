package com.assignment.firstclub.membership.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class UserLockManager {

    private final ConcurrentHashMap<Long, ReentrantLock> locks = new ConcurrentHashMap<>();

    public ReentrantLock getLock(Long userId) {
        return locks.computeIfAbsent(userId, id -> new ReentrantLock());
    }
}
