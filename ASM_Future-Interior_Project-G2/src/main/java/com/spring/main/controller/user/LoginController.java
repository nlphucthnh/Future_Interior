package com.spring.main.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login-page")
    public String getLoginPage() {
        return "dangnhap";
    }
}
