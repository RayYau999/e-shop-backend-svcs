package com.rayyau.eshop.eshop.api.gateway.aspect;

import com.rayyau.eshop.security.library.dto.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoginLoggingAspect {
    @Pointcut("execution(* com.rayyau.eshop.eshop.api.gateway.service.LoginService.login(..)) && args(request)")
    public void loginMethod(LoginRequest request) {}

    @Before(value = "loginMethod(request)", argNames = "request")
    public void logUserId(LoginRequest request) {
        log.info("User login attempt, username: {}", request.getUsername());
    }
}
