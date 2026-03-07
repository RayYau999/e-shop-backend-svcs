package com.rayyau.eshop.login.controller;

import com.rayyau.eshop.security.library.dto.LoginRequest;
import com.rayyau.eshop.security.library.dto.LoginResponse;
import com.rayyau.eshop.security.library.dto.UserDetailsWithoutPasswordDto;
import com.rayyau.eshop.security.library.impl.UserDetailsServiceImp;
import com.rayyau.eshop.security.library.security.JwtUtil;
import com.rayyau.eshop.security.library.security.SecurityConfigProperties;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImp userDetailsService;
    private final JwtUtil jwtUtil;
    private final Integer jwtTtl;

    public LoginController(
            AuthenticationManager authenticationManager, UserDetailsServiceImp userDetailsService,
            JwtUtil jwtUtil,
            SecurityConfigProperties securityConfigProperties
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.jwtTtl = Math.toIntExact(securityConfigProperties.getJwtTtl());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        log.info("calling login api");
        UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.unauthenticated(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        if (!authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Failed to authenticate");
        }

        String authenticatedUserName = userDetailsService.loadUserByUsername(authentication.getName()).getUsername();

        try {
            UserDetailsWithoutPasswordDto userDetailsWithoutPasswordDto = userDetailsService.getUserDetailsWithoutPassword(authenticatedUserName);
            String token = jwtUtil.generate(request.getUsername(), jwtTtl, userDetailsWithoutPasswordDto.getId());
            return new LoginResponse(
                    token,
                    jwtUtil.extractCreatedAt(token),
                    jwtUtil.extractExpirationDate(token)
            );
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage());
            throw new UsernameNotFoundException("User Id not found by username");
        }
    }

    @GetMapping("/login-testing")
    public String loginTesting() {
        log.info("login service is up and running");
        return "Login service is up and running!";
    }

}
