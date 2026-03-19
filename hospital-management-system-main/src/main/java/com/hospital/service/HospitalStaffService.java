package com.hospital.service;

import com.hospital.model.HospitalStaff;
import com.hospital.repository.HospitalStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Hospital Staff Service - Business logic for staff operations
 */
@Service
@SuppressWarnings("null")
public class HospitalStaffService {

    @Autowired
    private HospitalStaffRepository staffRepository;

    public HospitalStaff createStaff(HospitalStaff staff) {
        return staffRepository.save(staff);
    }

    public HospitalStaff updateStaff(HospitalStaff staff) {
        return staffRepository.save(staff);
    }

    public void deleteStaff(Long staffId) {
        staffRepository.deleteById(staffId);
    }

    public Optional<HospitalStaff> getStaffById(Long staffId) {
        return staffRepository.findById(staffId);
    }

    public List<HospitalStaff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Optional<HospitalStaff> getStaffByEmail(String email) {
        return staffRepository.findByEmail(email);
    }

    public List<HospitalStaff> getStaffByDepartment(String department) {
        return staffRepository.findByDepartment(department);
    }

    public List<HospitalStaff> getStaffByPosition(String position) {
        return staffRepository.findByPosition(position);
    }

    public List<HospitalStaff> getStaffByStatus(String status) {
        return staffRepository.findByStatus(status);
    }

    public List<HospitalStaff> searchStaffByFirstName(String firstName) {
        return staffRepository.findByFirstNameContainingIgnoreCase(firstName);
    }
}
