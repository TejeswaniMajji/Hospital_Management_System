package com.hospital.repository;

import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Appointment Repository - Data access layer for Appointment entity
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatient(Patient patient);

    List<Appointment> findByPatientPatientId(Long patientId);

    List<Appointment> findByDoctor(Doctor doctor);

    List<Appointment> findByStatus(String status);

    List<Appointment> findByAppointmentDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Appointment> findByPatientAndStatus(Patient patient, String status);

    List<Appointment> findByDoctorAndStatus(Doctor doctor, String status);
}
