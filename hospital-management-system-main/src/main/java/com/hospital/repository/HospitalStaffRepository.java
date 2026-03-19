package com.hospital.repository;

import com.hospital.model.HospitalStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Hospital Staff Repository - Data access layer for HospitalStaff entity
 */
@Repository
public interface HospitalStaffRepository extends JpaRepository<HospitalStaff, Long> {

    Optional<HospitalStaff> findByEmail(String email);

    Optional<HospitalStaff> findByPhoneNumber(String phoneNumber);

    List<HospitalStaff> findByDepartment(String department);

    List<HospitalStaff> findByPosition(String position);

    List<HospitalStaff> findByStatus(String status);

    List<HospitalStaff> findByFirstNameContainingIgnoreCase(String firstName);
}
