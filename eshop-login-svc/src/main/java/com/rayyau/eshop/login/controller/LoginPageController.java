package com.rayyau.eshop.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class LoginPageController {
    //use for test the login authentication
    @GetMapping("/login-success")
    String loginSuccess() {
        return "loginSuccess.html";
    }
}
