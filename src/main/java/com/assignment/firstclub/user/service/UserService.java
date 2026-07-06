package com.assignment.firstclub.user.service;

import com.assignment.firstclub.common.data.CrudOperation;
import com.assignment.firstclub.common.data.Storage;
import com.assignment.firstclub.user.dto.UserCreateRequest;
import com.assignment.firstclub.user.exception.UserNotFoundException;
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
        boolean emailExists = getAll().stream()
                .anyMatch(u -> u.getEmailId().equalsIgnoreCase(request.getEmailId()));
        if (emailExists) {
            throw new IllegalArgumentException(
                    "A user with email '" + request.getEmailId() + "' already exists");
        }
        User user = User.builder().name(request.getName()).emailId(request.getEmailId()).build();
        return storage.save(user);
    }

    /**
     * Returns the user with the given ID, throwing {@link UserNotFoundException}
     * if no such user exists.
     */
    public User getUser(Long userId) throws UserNotFoundException {
        return getAll().stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
