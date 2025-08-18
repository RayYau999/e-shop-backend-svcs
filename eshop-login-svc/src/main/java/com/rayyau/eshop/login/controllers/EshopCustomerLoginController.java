package com.rayyau.eshop.login.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/eshop/login")
@Slf4j
public class EshopCustomerLoginController {

    @GetMapping("/")
    public String hello(HttpServletRequest request) {
        log.info("this is login logger test");
        return "Hello from Eshop Customer Login Service! " + request.getSession().getId();
    }

}
