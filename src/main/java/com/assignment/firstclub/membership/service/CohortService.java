package com.assignment.firstclub.membership.service;

import com.assignment.firstclub.common.data.CrudOperation;
import com.assignment.firstclub.common.data.Storage;
import com.assignment.firstclub.membership.model.entity.Cohort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CohortService extends CrudOperation<Cohort>{
    @Autowired
    public CohortService(Storage storage) {
        super(storage, Cohort.class);
    }

    public Cohort addCohort(String name) {
        return create(Cohort.builder()
                .name(name)
                .isActive(true)
                .build());
    }

}
