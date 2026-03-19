package com.hospital.controller;

import com.hospital.model.HospitalStaff;
import com.hospital.service.HospitalStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Staff REST API Controller - Handles staff-related API requests for AJAX calls
 */
@RestController
@RequestMapping("/api/staff")
public class StaffApiController {

    @Autowired
    private HospitalStaffService staffService;

    /**
     * Get all staff as JSON
     */
    @GetMapping
    public ResponseEntity<List<HospitalStaff>> getAllStaff() {
        List<HospitalStaff> staff = staffService.getAllStaff();
        return ResponseEntity.ok(staff);
    }

    /**
     * Get staff by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<HospitalStaff> getStaffById(@PathVariable Long id) {
        Optional<HospitalStaff> staff = staffService.getStaffById(id);
        return staff.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Save new staff
     */
    @PostMapping("/save")
    public ResponseEntity<HospitalStaff> saveStaff(@RequestBody HospitalStaff staff) {
        if (staff.getJoinDate() == null) {
            staff.setJoinDate(LocalDateTime.now());
        }
        if (staff.getStatus() == null || staff.getStatus().isEmpty()) {
            staff.setStatus("Active");
        }
        HospitalStaff savedStaff = staffService.createStaff(staff);
        return ResponseEntity.ok(savedStaff);
    }

    /**
     * Update existing staff
     */
    @PostMapping("/update")
    public ResponseEntity<HospitalStaff> updateStaff(@RequestBody HospitalStaff staff) {
        HospitalStaff updatedStaff = staffService.updateStaff(staff);
        return ResponseEntity.ok(updatedStaff);
    }

    /**
     * Delete staff by ID
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Get staff by department
     */
    @GetMapping("/department/{department}")
    public ResponseEntity<List<HospitalStaff>> getStaffByDepartment(@PathVariable String department) {
        List<HospitalStaff> staff = staffService.getStaffByDepartment(department);
        return ResponseEntity.ok(staff);
    }

    /**
     * Get staff by position
     */
    @GetMapping("/position/{position}")
    public ResponseEntity<List<HospitalStaff>> getStaffByPosition(@PathVariable String position) {
        List<HospitalStaff> staff = staffService.getStaffByPosition(position);
        return ResponseEntity.ok(staff);
    }

    /**
     * Get staff by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<HospitalStaff>> getStaffByStatus(@PathVariable String status) {
        List<HospitalStaff> staff = staffService.getStaffByStatus(status);
        return ResponseEntity.ok(staff);
    }
}
