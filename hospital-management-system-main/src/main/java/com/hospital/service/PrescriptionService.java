package com.hospital.service;

import com.hospital.model.Prescription;
import com.hospital.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Prescription Service - Business logic for prescription operations
 */
@Service
@SuppressWarnings("null")
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public Prescription createPrescription(Prescription prescription) {
        prescription.setCreatedAt(LocalDateTime.now());
        if (prescription.getStatus() == null || prescription.getStatus().isEmpty()) {
            prescription.setStatus("Pending");
        }
        return prescriptionRepository.save(prescription);
    }

    public Prescription updatePrescription(Prescription prescription) {
        prescription.setUpdatedAt(LocalDateTime.now());
        return prescriptionRepository.save(prescription);
    }

    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }

    public void deleteByRxNo(String rxNo) {
        Optional<Prescription> rx = prescriptionRepository.findByRxNo(rxNo);
        rx.ifPresent(prescriptionRepository::delete);
    }

    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }

    public Optional<Prescription> getPrescriptionByRxNo(String rxNo) {
        return prescriptionRepository.findByRxNo(rxNo);
    }

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Prescription> getPrescriptionsByStatus(String status) {
        return prescriptionRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    public List<Prescription> getPrescriptionsByPriority(String priority) {
        return prescriptionRepository.findByPriority(priority);
    }

    public List<Prescription> searchByPatientName(String patientName) {
        return prescriptionRepository.findByPatientNameContainingIgnoreCase(patientName);
    }

    public List<Prescription> searchByDoctorName(String doctorName) {
        return prescriptionRepository.findByDoctorNameContainingIgnoreCase(doctorName);
    }

    public long countByStatus(String status) {
        return prescriptionRepository.countByStatus(status);
    }

    public long countPendingUrgent() {
        return prescriptionRepository.countByStatusAndPriority("Pending", "urgent");
    }

    public Prescription dispensePrescription(String rxNo) {
        Optional<Prescription> rxOpt = prescriptionRepository.findByRxNo(rxNo);
        if (rxOpt.isPresent()) {
            Prescription rx = rxOpt.get();
            rx.setStatus("Dispensed");
            rx.setUpdatedAt(LocalDateTime.now());
            return prescriptionRepository.save(rx);
        }
        return null;
    }
}
