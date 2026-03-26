package com.rayyau.eshop.eshop.api.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rayyau.eshop.eshop.api.gateway.service.LoginService;
import com.rayyau.eshop.security.library.dto.LoginRequest;
import com.rayyau.eshop.security.library.dto.LoginResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LoginService loginService;
    /**
     * 測試 RateLimiter：前10次成功，第11次開始被限流
     * Make sure this test case runs without a break point, or else the limit-refresh-period will refresh automatically
     * and cannot test the rate limit settings.
     */
    @Test
    void whenExceedRateLimit_thenShouldReturn429AndFallback() throws Exception {
        LoginRequest loginRequest = new LoginRequest("username", "password");
        Date mockDate = new Date();

        LoginResponse expectedLoginResponse = new LoginResponse("Request processed successfully!", mockDate, mockDate) ;

        when(loginService.login(any(LoginRequest.class)))
                .thenAnswer(invocation -> expectedLoginResponse);

        String remoteAddr = "127.0.0.1";
        System.out.println("testing rate limiter");
        // 第 1 ~ 10 次請求應該成功
        for (int i = 1; i <= 10; i++) {
            mockMvc.perform(MockMvcRequestBuilders.post("/login")
                            .with(request -> { request.setRemoteAddr(remoteAddr); return request; })
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest))
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expectedLoginResponse)));
        }

        // 第 11 次開始應該被限流，執行 fallback
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .with(request -> { request.setRemoteAddr(remoteAddr); return request; })
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
                )
                .andExpect(status().isTooManyRequests());   // 429

    }
}
