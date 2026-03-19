package com.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Intercepts H2 Console requests and redirects them to our custom console
 */
@Controller
@RequestMapping("/h2-console")
public class H2ConsoleInterceptor {

    // Handle all GET requests to /hospital/h2-console/*
    @GetMapping("/**")
    public String interceptAllH2GetRequests() {
        return "redirect:/hospital/db/login";
    }

    // Handle all POST requests to /hospital/h2-console/*
    @PostMapping("/**")
    public String interceptAllH2PostRequests() {
        return "redirect:/hospital/db/console";
    }
}
