package com.hospital.service;

import com.hospital.model.Supplier;
import com.hospital.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Supplier Service - Business logic for supplier operations
 */
@Service
@SuppressWarnings("null")
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier createSupplier(Supplier supplier) {
        supplier.setCreatedAt(LocalDateTime.now());
        if (supplier.getStatus() == null || supplier.getStatus().isEmpty()) {
            supplier.setStatus("Active");
        }
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Supplier supplier) {
        supplier.setUpdatedAt(LocalDateTime.now());
        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }

    public void deleteBySupplierCode(String code) {
        Optional<Supplier> s = supplierRepository.findBySupplierCode(code);
        s.ifPresent(supplierRepository::delete);
    }

    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    public Optional<Supplier> getSupplierByCode(String code) {
        return supplierRepository.findBySupplierCode(code);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Supplier> getSuppliersByStatus(String status) {
        return supplierRepository.findByStatus(status);
    }

    public List<Supplier> searchByName(String name) {
        return supplierRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Supplier> searchByProduct(String product) {
        return supplierRepository.findByProductsContainingIgnoreCase(product);
    }

    public long countByStatus(String status) {
        return supplierRepository.countByStatus(status);
    }

    public long totalCount() {
        return supplierRepository.count();
    }
}
