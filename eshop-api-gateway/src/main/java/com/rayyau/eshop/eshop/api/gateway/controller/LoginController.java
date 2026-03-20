package com.rayyau.eshop.eshop.api.gateway.controller;

import com.rayyau.eshop.eshop.api.gateway.service.LoginService;
import com.rayyau.eshop.security.library.dto.LoginRequest;
import com.rayyau.eshop.security.library.dto.LoginResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@AllArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;

    @CircuitBreaker(name = "loginService", fallbackMethod = "fallbackResponse")
    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        log.info("login running from api gateway controller");
        return loginService.login(request);
    }

    // 這是你的 Fallback 方法
    public LoginResponse fallbackResponse(LoginRequest request, Throwable e) {
        log.error("觸發斷路器或服務異常！原因: {}", e.getMessage());

        Date currentDate = new Date();

        // 預設的備援回覆
        return new LoginResponse("服務暫時不可用，請稍後再試。", currentDate, currentDate);
    }
}
