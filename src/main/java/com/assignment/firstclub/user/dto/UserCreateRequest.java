package com.assignment.firstclub.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "Name must not be blank")
    String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid email address")
    String emailId;
}
