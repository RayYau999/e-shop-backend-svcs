package com.rayyau.eshop.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class LoginResponse {
    private final String token;
    private final Date createdAt;
    private final Date expiresAt;

}
