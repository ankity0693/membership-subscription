package com.assignment.firstclub.user.controller;

import com.assignment.firstclub.user.dto.UserCreateRequest;
import com.assignment.firstclub.user.model.User;
import com.assignment.firstclub.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.assignment.firstclub.common.Constants.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management APIs")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a user")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserCreateRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }
}