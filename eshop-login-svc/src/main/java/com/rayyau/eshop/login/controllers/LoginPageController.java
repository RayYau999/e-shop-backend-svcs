package com.rayyau.eshop.login.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class LoginPageController {
    @GetMapping("/login")
    String login() {
        return "login.html";
    }

    @GetMapping("/login-success")
    String loginSuccess() {
        return "loginSuccess.html";
    }
}
