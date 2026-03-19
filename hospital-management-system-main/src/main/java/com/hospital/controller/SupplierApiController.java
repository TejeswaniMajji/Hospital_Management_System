package com.hospital.controller;

import com.hospital.model.Supplier;
import com.hospital.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Supplier REST API Controller - Handles supplier-related API requests
 */
@RestController
@RequestMapping("/api/suppliers")
public class SupplierApiController {

    @Autowired
    private SupplierService supplierService;

    /**
     * Get all suppliers
     */
    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    /**
     * Get suppliers by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Supplier>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(supplierService.getSuppliersByStatus(status));
    }

    /**
     * Get a single supplier by code
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<Supplier> getByCode(@PathVariable String code) {
        Optional<Supplier> s = supplierService.getSupplierByCode(code);
        return s.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Get a single supplier by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getById(@PathVariable Long id) {
        Optional<Supplier> s = supplierService.getSupplierById(id);
        return s.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new supplier
     */
    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody Map<String, String> payload) {
        Supplier s = new Supplier();
        s.setSupplierCode(payload.getOrDefault("supplierCode", generateCode()));
        s.setName(payload.get("name"));
        s.setContactPerson(payload.getOrDefault("contactPerson", ""));
        s.setPhone(payload.getOrDefault("phone", ""));
        s.setEmail(payload.getOrDefault("email", ""));
        s.setAddress(payload.getOrDefault("address", ""));
        s.setProducts(payload.getOrDefault("products", ""));
        s.setStatus(payload.getOrDefault("status", "Active"));
        s.setRating(payload.getOrDefault("rating", "4"));
        s.setNotes(payload.getOrDefault("notes", ""));
        s.setCreatedAt(LocalDateTime.now());

        String dateStr = payload.get("lastOrderDate");
        if (dateStr != null && !dateStr.isEmpty()) {
            s.setLastOrderDate(LocalDate.parse(dateStr));
        }

        Supplier saved = supplierService.createSupplier(s);
        return ResponseEntity.ok(saved);
    }

    /**
     * Update an existing supplier
     */
    @PutMapping("/code/{code}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable String code, @RequestBody Map<String, String> payload) {
        Optional<Supplier> existingOpt = supplierService.getSupplierByCode(code);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Supplier s = existingOpt.get();
        if (payload.containsKey("name")) s.setName(payload.get("name"));
        if (payload.containsKey("contactPerson")) s.setContactPerson(payload.get("contactPerson"));
        if (payload.containsKey("phone")) s.setPhone(payload.get("phone"));
        if (payload.containsKey("email")) s.setEmail(payload.get("email"));
        if (payload.containsKey("address")) s.setAddress(payload.get("address"));
        if (payload.containsKey("products")) s.setProducts(payload.get("products"));
        if (payload.containsKey("status")) s.setStatus(payload.get("status"));
        if (payload.containsKey("rating")) s.setRating(payload.get("rating"));
        if (payload.containsKey("notes")) s.setNotes(payload.get("notes"));
        if (payload.containsKey("lastOrderDate") && !payload.get("lastOrderDate").isEmpty()) {
            s.setLastOrderDate(LocalDate.parse(payload.get("lastOrderDate")));
        }

        Supplier updated = supplierService.updateSupplier(s);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a supplier by code
     */
    @DeleteMapping("/code/{code}")
    public ResponseEntity<Map<String, String>> deleteSupplier(@PathVariable String code) {
        Optional<Supplier> s = supplierService.getSupplierByCode(code);
        if (s.isPresent()) {
            supplierService.deleteBySupplierCode(code);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Supplier " + code + " deleted successfully");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get supplier statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", supplierService.totalCount());
        stats.put("active", supplierService.countByStatus("Active"));
        stats.put("inactive", supplierService.countByStatus("Inactive"));
        stats.put("pending", supplierService.countByStatus("Pending Order"));
        return ResponseEntity.ok(stats);
    }

    /**
     * Search suppliers
     */
    @GetMapping("/search")
    public ResponseEntity<List<Supplier>> search(@RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String product) {
        if (name != null && !name.isEmpty()) {
            return ResponseEntity.ok(supplierService.searchByName(name));
        }
        if (product != null && !product.isEmpty()) {
            return ResponseEntity.ok(supplierService.searchByProduct(product));
        }
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    private String generateCode() {
        return "SUP-" + String.format("%04d", (int)(Math.random() * 9999));
    }
}
