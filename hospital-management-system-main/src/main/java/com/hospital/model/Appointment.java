package com.hospital.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Appointment Entity - Represents an appointment between patient and doctor
 */
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;

    @Column(length = 50)
    private String status;

    @Column(length = 500)
    private String reason;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(length = 50)
    private String priority;

    @Column
    private LocalDateTime followUpDate;

    @Column
    private LocalDateTime consultationExpiryDate;

    public Appointment() {}

    public Appointment(Long appointmentId, Patient patient, Doctor doctor, LocalDateTime appointmentDateTime, String status, String reason, String notes, LocalDateTime createdAt, String priority) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDateTime = appointmentDateTime;
        this.status = status;
        this.reason = reason;
        this.notes = notes;
        this.createdAt = createdAt;
        this.priority = priority;
        this.consultationExpiryDate = appointmentDateTime.plusWeeks(1);
    }

    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public LocalDateTime getAppointmentDateTime() { return appointmentDateTime; }
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) { this.appointmentDateTime = appointmentDateTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public LocalDateTime getFollowUpDate() { return followUpDate; }
    public void setFollowUpDate(LocalDateTime followUpDate) { this.followUpDate = followUpDate; }

    public LocalDateTime getConsultationExpiryDate() { return consultationExpiryDate; }
    public void setConsultationExpiryDate(LocalDateTime consultationExpiryDate) { this.consultationExpiryDate = consultationExpiryDate; }
}


