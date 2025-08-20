package com.rayyau.eshop.login.controller;

import com.rayyau.eshop.login.dto.LoginRequest;
import com.rayyau.eshop.login.dto.LoginResponse;
import com.rayyau.eshop.login.security.JwtUtil;
import com.rayyau.eshop.login.security.SecurityConfigProperties;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final Integer jwtTtl;

    public LoginController(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            SecurityConfigProperties securityConfigProperties
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.jwtTtl = Math.toIntExact(securityConfigProperties.getJwtTtl());
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.unauthenticated(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        if (!authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Failed to authenticate");
        }

        String token = jwtUtil.generate(request.getUsername(), jwtTtl);
        return new LoginResponse(
                token,
                jwtUtil.extractCreatedAt(token),
                jwtUtil.extractExpirationDate(token)
        );
    }
}
