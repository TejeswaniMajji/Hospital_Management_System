package com.hospital.controller;

import com.hospital.model.Appointment;
import com.hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * REST API Controller for Appointment operations
 * Used by the Notification Center for accept/reject/delete actions
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentApiController {

    @Autowired
    private AppointmentService appointmentService;

    /**
     * Get all appointments
     */
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        List<Map<String, Object>> result = appointments.stream()
                .map(this::toMap)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /**
     * Get appointments by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Map<String, Object>>> getByStatus(@PathVariable String status) {
        List<Appointment> appointments = appointmentService.getAppointmentsByStatus(status);
        List<Map<String, Object>> result = appointments.stream()
                .map(this::toMap)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /**
     * Get a single appointment by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        Optional<Appointment> apt = appointmentService.getAppointmentById(id);
        if (apt.isPresent()) {
            return ResponseEntity.ok(toMap(apt.get()));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get appointment stats for the notification center
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        List<Appointment> all = appointmentService.getAllAppointments();
        long total = all.size();
        long scheduled = all.stream().filter(a -> "Scheduled".equals(a.getStatus())).count();
        long confirmed = all.stream().filter(a -> "Confirmed".equals(a.getStatus())).count();
        long pending = all.stream().filter(a -> "Pending".equals(a.getStatus())).count();
        long accepted = all.stream().filter(a -> "Accepted".equals(a.getStatus())).count();
        long rejected = all.stream().filter(a -> "Rejected".equals(a.getStatus())).count();
        long completed = all.stream().filter(a -> "Completed".equals(a.getStatus())).count();
        long cancelled = all.stream().filter(a -> "Cancelled".equals(a.getStatus())).count();

        // "Actionable" = Scheduled + Pending (need accept/reject)
        long actionable = scheduled + pending;

        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("total", total);
        stats.put("scheduled", scheduled);
        stats.put("confirmed", confirmed);
        stats.put("pending", pending);
        stats.put("accepted", accepted);
        stats.put("rejected", rejected);
        stats.put("completed", completed);
        stats.put("cancelled", cancelled);
        stats.put("actionable", actionable);
        return ResponseEntity.ok(stats);
    }

    /**
     * Accept ALL pending/scheduled appointments at once
     */
    @PutMapping("/accept-all")
    public ResponseEntity<Map<String, Object>> acceptAllAppointments() {
        List<Appointment> all = appointmentService.getAllAppointments();
        int count = 0;
        for (Appointment apt : all) {
            String st = apt.getStatus();
            if ("Scheduled".equals(st) || "Pending".equals(st) || "Confirmed".equals(st)) {
                apt.setStatus("Accepted");
                appointmentService.updateAppointment(apt);
                count++;
            }
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("message", count + " appointments accepted");
        result.put("acceptedCount", count);
        return ResponseEntity.ok(result);
    }

    /**
     * Accept an appointment — sets status to "Accepted"
     */
    @PutMapping("/{id}/accept")
    public ResponseEntity<Map<String, Object>> acceptAppointment(@PathVariable Long id) {
        Optional<Appointment> opt = appointmentService.getAppointmentById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Appointment apt = opt.get();
        apt.setStatus("Accepted");
        appointmentService.updateAppointment(apt);
        return ResponseEntity.ok(toMap(apt));
    }

    /**
     * Reject an appointment — sets status to "Rejected"
     */
    @PutMapping("/{id}/reject")
    public ResponseEntity<Map<String, Object>> rejectAppointment(@PathVariable Long id) {
        Optional<Appointment> opt = appointmentService.getAppointmentById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Appointment apt = opt.get();
        apt.setStatus("Rejected");
        appointmentService.updateAppointment(apt);
        return ResponseEntity.ok(toMap(apt));
    }

    /**
     * Mark an appointment as Confirmed
     */
    @PutMapping("/{id}/confirm")
    public ResponseEntity<Map<String, Object>> confirmAppointment(@PathVariable Long id) {
        Optional<Appointment> opt = appointmentService.getAppointmentById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Appointment apt = opt.get();
        apt.setStatus("Confirmed");
        appointmentService.updateAppointment(apt);
        return ResponseEntity.ok(toMap(apt));
    }

    /**
     * Delete an appointment
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAppointment(@PathVariable Long id) {
        Optional<Appointment> opt = appointmentService.getAppointmentById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        String patientName = opt.get().getPatient().getFirstName() + " " + opt.get().getPatient().getLastName();
        appointmentService.deleteAppointment(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Appointment for " + patientName + " deleted successfully");
        response.put("id", id.toString());
        return ResponseEntity.ok(response);
    }

    /**
     * Update appointment status generically
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Optional<Appointment> opt = appointmentService.getAppointmentById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        String newStatus = body.get("status");
        if (newStatus == null || newStatus.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Appointment apt = opt.get();
        apt.setStatus(newStatus);
        appointmentService.updateAppointment(apt);
        return ResponseEntity.ok(toMap(apt));
    }

    /**
     * Convert Appointment entity to a flat Map for JSON serialization
     */
    private Map<String, Object> toMap(Appointment apt) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", apt.getAppointmentId());
        map.put("patientName", apt.getPatient().getFirstName() + " " + apt.getPatient().getLastName());
        map.put("patientId", apt.getPatient().getPatientId());
        String docFirst = apt.getDoctor().getFirstName();
        String docName = (docFirst.startsWith("Dr.") ? "" : "Dr. ") + docFirst + " " + apt.getDoctor().getLastName();
        map.put("doctorName", docName);
        map.put("doctorId", apt.getDoctor().getDoctorId());
        map.put("specialization", apt.getDoctor().getSpecialization());
        map.put("appointmentDateTime", apt.getAppointmentDateTime() != null ? apt.getAppointmentDateTime().toString() : null);
        map.put("status", apt.getStatus());
        map.put("reason", apt.getReason());
        map.put("notes", apt.getNotes());
        map.put("priority", apt.getPriority());
        map.put("createdAt", apt.getCreatedAt() != null ? apt.getCreatedAt().toString() : null);
        return map;
    }
}
