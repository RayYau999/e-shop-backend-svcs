package com.rayyau.eshop.security.library.client;

import com.rayyau.eshop.security.library.config.FeignConfig;
import com.rayyau.eshop.security.library.dto.LoginRequest;
import com.rayyau.eshop.security.library.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(configuration = FeignConfig.class, name = "eshop-login-svc", url = "${eshop.login.service.url}")
public interface LoginClient {
    @PostMapping("/login")
    LoginResponse login(@RequestBody @Valid LoginRequest request);
}
