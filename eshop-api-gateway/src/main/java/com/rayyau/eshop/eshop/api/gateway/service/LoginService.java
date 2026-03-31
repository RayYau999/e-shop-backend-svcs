package com.rayyau.eshop.eshop.api.gateway.service;

import com.rayyau.eshop.security.library.client.LoginClient;
import com.rayyau.eshop.security.library.dto.LoginRequest;
import com.rayyau.eshop.security.library.dto.LoginResponse;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
@CircuitBreaker(name = "loginService", fallbackMethod = "fallbackResponse")
@Retry(name = "loginService")
public class LoginService {
    private final LoginClient loginClient;

    @Bulkhead(name = "loginService", fallbackMethod = "loginBulkheadFallback")
    public LoginResponse login(LoginRequest request) {
        log.info("logging in from api gateway");
        return loginClient.login(request);
    }

    // 這是你的 Fallback 方法
    public LoginResponse fallbackResponse(LoginRequest request, Throwable e) {
        log.error("觸發斷路器或服務異常！原因: {}", e.getMessage());

        Date currentDate = new Date();

        // 預設的備援回覆
        return new LoginResponse("服務暫時不可用，請稍後再試。", currentDate, currentDate);
    }

    public LoginResponse loginBulkheadFallback(LoginRequest request, Throwable e) {
        log.error("觸發 Bulkhead 限流！原因: {}", e.getMessage());

        Date currentDate = new Date();

        // 預設的備援回覆
        return new LoginResponse("系統繁忙，請稍後再試。", currentDate, currentDate);
    }
}
