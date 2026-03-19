package com.hospital.controller;

import com.hospital.model.Doctor;
import com.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * Doctor Controller - Handles doctor-related HTTP requests
 */
@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/list")
    public String listDoctors(Model model) {
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        return "doctor/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctor/add";
    }

    @PostMapping("/save")
    public String saveDoctor(@ModelAttribute Doctor doctor) {
        doctor.setStatus("Active");
        doctorService.createDoctor(doctor);
        return "redirect:/doctor/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Doctor> doctor = doctorService.getDoctorById(id);
        if (doctor.isPresent()) {
            model.addAttribute("doctor", doctor.get());
            return "doctor/edit";
        }
        return "redirect:/doctor/list";
    }

    @PostMapping("/update")
    public String updateDoctor(@ModelAttribute Doctor doctor) {
        doctorService.updateDoctor(doctor);
        return "redirect:/doctor/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctor/list";
    }

    @GetMapping("/view/{id}")
    public String viewDoctor(@PathVariable Long id, Model model) {
        Optional<Doctor> doctor = doctorService.getDoctorById(id);
        if (doctor.isPresent()) {
            model.addAttribute("doctor", doctor.get());
            return "doctor/view";
        }
        return "redirect:/doctor/list";
    }

    @PostMapping("/search")
    public String searchDoctor(@RequestParam String searchType, @RequestParam String searchValue, Model model) {
        List<Doctor> results = null;

        switch (searchType) {
            case "firstName":
                results = doctorService.searchDoctorByFirstName(searchValue);
                break;
            case "specialization":
                results = doctorService.getDoctorsBySpecialization(searchValue);
                break;
            case "department":
                results = doctorService.getDoctorsByDepartment(searchValue);
                break;
            case "licenseNumber":
                Optional<Doctor> doctor = doctorService.getDoctorByLicenseNumber(searchValue);
                if (doctor.isPresent()) {
                    results = List.of(doctor.get());
                }
                break;
        }

        model.addAttribute("doctors", results != null ? results : List.of());
        return "doctor/list";
    }
}
