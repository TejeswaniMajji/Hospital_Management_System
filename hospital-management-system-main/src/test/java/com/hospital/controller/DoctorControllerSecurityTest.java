package com.hospital.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Verifies the @PreAuthorize rules on DoctorController actually hold: only ADMIN
 * may create doctors, every other role gets 403.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class DoctorControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "PATIENT")
    void patientCannotSaveDoctor() throws Exception {
        mockMvc.perform(post("/doctor/save")
                        .param("firstName", "Test")
                        .param("lastName", "Doctor")
                        .param("licenseNumber", "TEST-LIC-1")
                        .param("specialization", "General")
                        .param("phoneNumber", "0000000001")
                        .param("email", "test.doctor1@example.com"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "NURSE")
    void nurseCannotSaveDoctor() throws Exception {
        mockMvc.perform(post("/doctor/save")
                        .param("firstName", "Test")
                        .param("lastName", "Doctor")
                        .param("licenseNumber", "TEST-LIC-2")
                        .param("specialization", "General")
                        .param("phoneNumber", "0000000002")
                        .param("email", "test.doctor2@example.com"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanSaveDoctor() throws Exception {
        mockMvc.perform(post("/doctor/save")
                        .param("firstName", "Test")
                        .param("lastName", "Doctor")
                        .param("licenseNumber", "TEST-LIC-3")
                        .param("specialization", "General")
                        .param("phoneNumber", "0000000003")
                        .param("email", "test.doctor3@example.com"))
                .andExpect(status().is3xxRedirection());
    }
}
