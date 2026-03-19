package com.hospital.controller;

import com.hospital.service.DoctorService;
import com.hospital.service.PatientService;
import com.hospital.service.HospitalStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Dashboard Controller - Handles main dashboard and navigation
 */
@Controller
public class DashboardController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private HospitalStaffService staffService;

    @GetMapping(value = {"/", "/home", "/dashboard", "/index"})
    public String dashboard(Model model) {
        model.addAttribute("totalPatients", patientService.getAllPatients().size());
        model.addAttribute("totalDoctors", doctorService.getAllDoctors().size());
        model.addAttribute("totalStaff", staffService.getAllStaff().size());
        return "index";
    }
}
