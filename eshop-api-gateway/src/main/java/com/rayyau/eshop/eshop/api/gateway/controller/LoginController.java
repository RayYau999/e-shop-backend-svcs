package com.rayyau.eshop.eshop.api.gateway.controller;

import com.rayyau.eshop.eshop.api.gateway.service.LoginService;
import com.rayyau.eshop.security.library.dto.LoginRequest;
import com.rayyau.eshop.security.library.dto.LoginResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@AllArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;

    @RateLimiter(name = "loginController", fallbackMethod = "loginControllerFallback")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        log.info("login running from api gateway controller");
        LoginResponse loginResponse = loginService.login(request);
        if(loginResponse.getToken() != null) {
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public ResponseEntity<LoginResponse>  loginControllerFallback(LoginRequest request, Throwable throwable) {
        log.error("fallback running from api gateway controller, date {}, error {}", new Date(), throwable.getMessage());
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}
