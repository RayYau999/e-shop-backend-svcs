package com.rayyau.eshop.eshop.api.gateway.service;

import com.rayyau.eshop.security.library.client.LoginClient;
import com.rayyau.eshop.security.library.dto.LoginRequest;
import com.rayyau.eshop.security.library.dto.LoginResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LoginService {
    private final LoginClient loginClient;

    public LoginResponse login(LoginRequest request) {
        log.info("logging in from api gateway");
        return loginClient.login(request);
    }
}
