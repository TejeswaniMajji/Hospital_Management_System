package com.hospital.repository;

import com.hospital.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Prescription Repository - Data access layer for Prescription entity
 */
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    Optional<Prescription> findByRxNo(String rxNo);

    List<Prescription> findByStatus(String status);

    List<Prescription> findByStatusOrderByCreatedAtDesc(String status);

    List<Prescription> findByPriority(String priority);

    List<Prescription> findByPatientNameContainingIgnoreCase(String patientName);

    List<Prescription> findByDoctorNameContainingIgnoreCase(String doctorName);

    List<Prescription> findByPrescriptionDateBetween(LocalDate startDate, LocalDate endDate);

    List<Prescription> findByStatusAndPriority(String status, String priority);

    long countByStatus(String status);

    long countByStatusAndPriority(String status, String priority);

    List<Prescription> findAllByOrderByCreatedAtDesc();
}
