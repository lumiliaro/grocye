package com.grocye.grocyerest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@Data
public class RegistrationDto {

    @Valid

    @NotBlank(message = "Invalid username: Empty username")
    @NotNull(message = "Invalid username: Username is NULL")
    @Size(min = 3, max = 20, message = "Invalid Username: Must be of 3 - 20 characters")
    private String username;

    @Email(message = "Invalid E-Mail")
    private String email;

    @NotBlank(message = "Invalid password: Empty password")
    @NotNull(message = "Invalid password: Password is NULL")
    private String password;

    @NotBlank(message = "Invalid password confirmation: Empty password confirmation")
    @NotNull(message = "Invalid password confirmation: Password confirmation is NULL")
    private String passwordConfirmation;

    @URL(message = "Invalid profile-image url: Must be a valid url")
    private String profileImageUrl;
}
