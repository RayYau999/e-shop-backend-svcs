package com.rayyau.eshop.eshop.api.gateway.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ClientLoggingAspect {
    @Pointcut("execution(public * com.rayyau.eshop.security.library.client.*.*(..)) " +
            "|| execution(public * com.rayyau.eshop.payment.library.client.*.*(..))")
    public void feignClientMethod() {}

    @Before(value = "feignClientMethod()")
    public void logClientAndMethod(JoinPoint joinPoint) {
        log.info("[Before] Calling client: " + joinPoint.getSignature().getDeclaringTypeName() +
                ", method: " + joinPoint.getSignature().getName());
    }

}
