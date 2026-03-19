package com.hospital.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web Configuration for Hospital Management System
 * Handles view mapping and MVC configuration
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Register view controllers for root and home URLs
     */
    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        // Map root paths to dashboard controller
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/home").setViewName("index");
        registry.addViewController("/dashboard").setViewName("index");
    }
}
