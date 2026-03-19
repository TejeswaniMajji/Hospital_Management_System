package com.hospital.repository;

import com.hospital.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Supplier Repository - Data access layer for Supplier entity
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findBySupplierCode(String supplierCode);

    List<Supplier> findByStatus(String status);

    List<Supplier> findByNameContainingIgnoreCase(String name);

    List<Supplier> findByProductsContainingIgnoreCase(String product);

    long countByStatus(String status);

    List<Supplier> findAllByOrderByCreatedAtDesc();
}
