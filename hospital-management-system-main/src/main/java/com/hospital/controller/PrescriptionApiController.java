package com.hospital.controller;

import com.hospital.model.Prescription;
import com.hospital.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Prescription REST API Controller - Handles prescription-related API requests
 */
@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionApiController {

    @Autowired
    private PrescriptionService prescriptionService;

    /**
     * Get all prescriptions
     */
    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        return ResponseEntity.ok(prescriptionService.getAllPrescriptions());
    }

    /**
     * Get prescriptions by status (Pending, Dispensed, etc.)
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Prescription>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByStatus(status));
    }

    /**
     * Get a single prescription by Rx number
     */
    @GetMapping("/rx/{rxNo}")
    public ResponseEntity<Prescription> getByRxNo(@PathVariable String rxNo) {
        Optional<Prescription> rx = prescriptionService.getPrescriptionByRxNo(rxNo);
        return rx.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Get a single prescription by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getById(@PathVariable Long id) {
        Optional<Prescription> rx = prescriptionService.getPrescriptionById(id);
        return rx.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new prescription
     */
    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody Map<String, String> payload) {
        Prescription rx = new Prescription();
        rx.setRxNo(payload.getOrDefault("rxNo", generateRxNo()));
        rx.setPatientName(payload.get("patientName"));
        rx.setDoctorName(payload.get("doctorName"));
        rx.setMedicines(payload.get("medicines"));
        rx.setPriority(payload.getOrDefault("priority", "normal"));
        rx.setStatus("Pending");
        rx.setNotes(payload.getOrDefault("notes", ""));
        rx.setCreatedAt(LocalDateTime.now());

        String dateStr = payload.get("prescriptionDate");
        if (dateStr != null && !dateStr.isEmpty()) {
            rx.setPrescriptionDate(LocalDate.parse(dateStr));
        } else {
            rx.setPrescriptionDate(LocalDate.now());
        }

        Prescription saved = prescriptionService.createPrescription(rx);
        return ResponseEntity.ok(saved);
    }

    /**
     * Update an existing prescription
     */
    @PutMapping("/rx/{rxNo}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable String rxNo, @RequestBody Map<String, String> payload) {
        Optional<Prescription> existingOpt = prescriptionService.getPrescriptionByRxNo(rxNo);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Prescription rx = existingOpt.get();
        if (payload.containsKey("patientName")) rx.setPatientName(payload.get("patientName"));
        if (payload.containsKey("doctorName")) rx.setDoctorName(payload.get("doctorName"));
        if (payload.containsKey("medicines")) rx.setMedicines(payload.get("medicines"));
        if (payload.containsKey("priority")) rx.setPriority(payload.get("priority"));
        if (payload.containsKey("notes")) rx.setNotes(payload.get("notes"));
        if (payload.containsKey("status")) rx.setStatus(payload.get("status"));
        if (payload.containsKey("prescriptionDate")) {
            rx.setPrescriptionDate(LocalDate.parse(payload.get("prescriptionDate")));
        }

        Prescription updated = prescriptionService.updatePrescription(rx);
        return ResponseEntity.ok(updated);
    }

    /**
     * Dispense a prescription (mark as Dispensed)
     */
    @PutMapping("/rx/{rxNo}/dispense")
    public ResponseEntity<Prescription> dispensePrescription(@PathVariable String rxNo) {
        Prescription dispensed = prescriptionService.dispensePrescription(rxNo);
        if (dispensed != null) {
            return ResponseEntity.ok(dispensed);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Delete a prescription by Rx number
     */
    @DeleteMapping("/rx/{rxNo}")
    public ResponseEntity<Map<String, String>> deletePrescription(@PathVariable String rxNo) {
        Optional<Prescription> rx = prescriptionService.getPrescriptionByRxNo(rxNo);
        if (rx.isPresent()) {
            prescriptionService.deleteByRxNo(rxNo);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Prescription " + rxNo + " deleted successfully");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get prescription statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalPending", prescriptionService.countByStatus("Pending"));
        stats.put("totalDispensed", prescriptionService.countByStatus("Dispensed"));
        stats.put("urgentPending", prescriptionService.countPendingUrgent());
        stats.put("normalPending", prescriptionService.countByStatus("Pending") - prescriptionService.countPendingUrgent());
        return ResponseEntity.ok(stats);
    }

    /**
     * Search prescriptions by patient name
     */
    @GetMapping("/search")
    public ResponseEntity<List<Prescription>> search(@RequestParam(required = false) String patient,
                                                      @RequestParam(required = false) String doctor) {
        if (patient != null && !patient.isEmpty()) {
            return ResponseEntity.ok(prescriptionService.searchByPatientName(patient));
        }
        if (doctor != null && !doctor.isEmpty()) {
            return ResponseEntity.ok(prescriptionService.searchByDoctorName(doctor));
        }
        return ResponseEntity.ok(prescriptionService.getAllPrescriptions());
    }

    private String generateRxNo() {
        return "RX-" + LocalDate.now().getYear() + "-" + String.format("%05d", (int)(Math.random() * 99999));
    }
}
