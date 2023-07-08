package com.spring.main.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderManagerController {
    
    @GetMapping("/Manager/order")
    public String getOrderPage(){
        return "Manager/Manager-order-page";
    }

}
