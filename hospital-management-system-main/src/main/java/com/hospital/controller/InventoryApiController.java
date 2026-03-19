package com.hospital.controller;

import com.hospital.model.Inventory;
import com.hospital.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryApiController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<Inventory> list() {
        return inventoryService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> get(@PathVariable Long id) {
        return inventoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Inventory> create(@RequestBody Inventory inv) {
        Inventory saved = inventoryService.save(inv);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> update(@PathVariable Long id, @RequestBody Inventory inv) {
        return inventoryService.findById(id)
                .map(existing -> {
                    existing.setMedicineName(inv.getMedicineName());
                    existing.setCategory(inv.getCategory());
                    existing.setStockLevel(inv.getStockLevel());
                    existing.setMinThreshold(inv.getMinThreshold());
                    existing.setUnitPrice(inv.getUnitPrice());
                    existing.setManufacturer(inv.getManufacturer());
                    existing.setLocation(inv.getLocation());
                    Inventory saved = inventoryService.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inventoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/adjust")
    public ResponseEntity<Inventory> adjust(@PathVariable Long id, @RequestParam int delta) {
        try {
            Inventory updated = inventoryService.adjustStock(id, delta);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
