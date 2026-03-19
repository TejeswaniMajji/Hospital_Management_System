package com.hospital.service;

import com.hospital.model.Patient;
import com.hospital.model.Appointment;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Patient Service - Business logic for patient operations
 */
@Service
@SuppressWarnings("null")
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatient(Long patientId) {
        // First, delete all appointments related to this patient
        List<Appointment> appointments = appointmentRepository.findByPatientPatientId(patientId);
        if (appointments != null && !appointments.isEmpty()) {
            appointmentRepository.deleteAll(appointments);
        }
        // Then delete the patient
        patientRepository.deleteById(patientId);
    }

    public Optional<Patient> getPatientById(Long patientId) {
        return patientRepository.findById(patientId);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public Optional<Patient> getPatientByPhoneNumber(String phoneNumber) {
        return patientRepository.findByPhoneNumber(phoneNumber);
    }

    public List<Patient> searchPatientByFirstName(String firstName) {
        return patientRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    public List<Patient> searchPatientByLastName(String lastName) {
        return patientRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    public List<Patient> getPatientsByStatus(String status) {
        return patientRepository.findByStatus(status);
    }
}
