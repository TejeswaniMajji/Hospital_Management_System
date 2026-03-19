package com.hospital.repository;

import com.hospital.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Doctor Repository - Data access layer for Doctor entity
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findByPhoneNumber(String phoneNumber);

    List<Doctor> findBySpecialization(String specialization);

    List<Doctor> findByDepartment(String department);

    List<Doctor> findByStatus(String status);

    List<Doctor> findByFirstNameContainingIgnoreCase(String firstName);
}
