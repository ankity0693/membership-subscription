package com.assignment.firstclub.membership.service;

import com.assignment.firstclub.common.data.CrudOperation;
import com.assignment.firstclub.common.data.Storage;
import com.assignment.firstclub.membership.model.entity.UserCohort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CohortManagerService extends CrudOperation<UserCohort> {

    @Autowired
    public CohortManagerService(Storage storage) {
        super(storage, UserCohort.class);
    }

    public UserCohort assign(Long userId, Long cohortId) {
        return create(UserCohort.builder()
                .userId(userId)
                .cohortId(cohortId)
                .build());
    }

    public Set<Long> getUserCohort(Long userId) {
        return getAll().stream().filter(userCohort -> userCohort.getUserId().equals(userId))
                .map(UserCohort::getCohortId).collect(Collectors.toSet());
    }
}