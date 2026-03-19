package com.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Main entry point for the Hospital Management System application
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.hospital.repository")
@EntityScan(basePackages = "com.hospital.model")
public class HospitalManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalManagementApplication.class, args);
    }
}
