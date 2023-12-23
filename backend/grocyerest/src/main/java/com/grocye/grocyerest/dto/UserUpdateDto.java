package com.grocye.grocyerest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class UserUpdateDto {

    @Valid

    @Size(min = 3, max = 20, message = "Invalid Username: Must be of 3 - 20 characters")
    private String username;

    @Email(message = "Invalid E-Mail")
    private String email;

    @URL(message = "Invalid profile-image url: Must be a valid url")
    private String profileImageUrl;
}
