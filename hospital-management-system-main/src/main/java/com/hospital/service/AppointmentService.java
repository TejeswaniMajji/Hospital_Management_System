package com.hospital.service;

import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Appointment Service - Business logic for appointment operations
 */
@Service
@SuppressWarnings("null")
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment createAppointment(Appointment appointment) {
        if (appointment.getAppointmentDateTime() != null) {
            appointment.setConsultationExpiryDate(appointment.getAppointmentDateTime().plusWeeks(1));
        }
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    public Optional<Appointment> getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getAppointmentsByPatient(Patient patient) {
        return appointmentRepository.findByPatient(patient);
    }

    public List<Appointment> getAppointmentsByDoctor(Doctor doctor) {
        return appointmentRepository.findByDoctor(doctor);
    }

    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }

    public List<Appointment> getAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepository.findByAppointmentDateTimeBetween(startDate, endDate);
    }

    public List<Appointment> getPatientAppointmentsByStatus(Patient patient, String status) {
        return appointmentRepository.findByPatientAndStatus(patient, status);
    }

    public List<Appointment> getDoctorAppointmentsByStatus(Doctor doctor, String status) {
        return appointmentRepository.findByDoctorAndStatus(doctor, status);
    }

    public Appointment scheduleFollowUp(Long appointmentId, int days) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            LocalDateTime followUpDate = LocalDateTime.now().plusDays(days);
            appointment.setFollowUpDate(followUpDate);
            return appointmentRepository.save(appointment);
        }
        throw new RuntimeException("Appointment not found");
    }
}
