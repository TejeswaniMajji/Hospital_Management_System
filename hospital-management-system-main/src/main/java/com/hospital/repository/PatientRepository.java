package com.hospital.repository;

import com.hospital.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Patient Repository - Data access layer for Patient entity
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);

    Optional<Patient> findByPhoneNumber(String phoneNumber);

    List<Patient> findByFirstNameContainingIgnoreCase(String firstName);

    List<Patient> findByLastNameContainingIgnoreCase(String lastName);

    List<Patient> findByStatus(String status);
}
