package com.rayyau.eshop.security.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LoginRequest {
    @NotBlank
    private final String username;
    @NotBlank
    private final String password;
}
