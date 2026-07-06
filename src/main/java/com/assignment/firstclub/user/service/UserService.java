package com.assignment.firstclub.user.service;

import com.assignment.firstclub.common.data.CrudOperation;
import com.assignment.firstclub.common.data.Storage;
import com.assignment.firstclub.user.dto.UserCreateRequest;
import com.assignment.firstclub.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CrudOperation<User> {

    @Autowired
    public UserService(Storage storage) {
        super(storage, User.class);
    }

    public User createUser(UserCreateRequest request) {
        User user = User.builder().name(request.getName()).emailId(request.getEmailId()).build();
        return storage.save(user);
    }
}
