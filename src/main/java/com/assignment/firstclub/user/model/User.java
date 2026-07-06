package com.assignment.firstclub.user.model;

import com.assignment.firstclub.common.data.BaseEntity;
import lombok.*;

@Data
@Builder
public class User implements BaseEntity {
    Long id;
    String name;
    String emailId;
}
