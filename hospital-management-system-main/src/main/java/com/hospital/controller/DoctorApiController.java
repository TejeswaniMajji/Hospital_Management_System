package com.hospital.controller;

import com.hospital.model.Doctor;
import com.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * Doctor REST API Controller - Handles doctor-related API requests for AJAX calls
 */
@RestController
@RequestMapping("/api/doctors")
public class DoctorApiController {

    @Autowired
    private DoctorService doctorService;

    /**
     * Get all doctors as JSON
     */
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    /**
     * Get doctor by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Optional<Doctor> doctor = doctorService.getDoctorById(id);
        return doctor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Save new doctor
     */
    @PostMapping("/save")
    public ResponseEntity<Doctor> saveDoctor(@ModelAttribute Doctor doctor) {
        doctor.setStatus("Active");
        Doctor savedDoctor = doctorService.createDoctor(doctor);
        return ResponseEntity.ok(savedDoctor);
    }

    /**
     * Update existing doctor
     */
    @PostMapping("/update")
    public ResponseEntity<Doctor> updateDoctor(@ModelAttribute Doctor doctor) {
        Doctor updatedDoctor = doctorService.updateDoctor(doctor);
        return ResponseEntity.ok(updatedDoctor);
    }

    /**
     * Delete doctor by ID
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Search doctors by specialization
     */
    @GetMapping("/search/specialization/{specialization}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialization(@PathVariable String specialization) {
        List<Doctor> doctors = doctorService.getDoctorsBySpecialization(specialization);
        return ResponseEntity.ok(doctors);
    }

    /**
     * Search doctors by department
     */
    @GetMapping("/search/department/{department}")
    public ResponseEntity<List<Doctor>> getDoctorsByDepartment(@PathVariable String department) {
        List<Doctor> doctors = doctorService.getDoctorsByDepartment(department);
        return ResponseEntity.ok(doctors);
    }
}
