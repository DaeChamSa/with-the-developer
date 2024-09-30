package com.developer.payment.command.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/h")
    public String home() {
        return "home";
    }
}
