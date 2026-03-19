package com.hospital.service;

import com.hospital.model.Inventory;
import com.hospital.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("null")
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> listAll() {
        return inventoryRepository.findAll();
    }

    public Inventory save(Inventory inv) {
        inv.setUpdatedAt(LocalDateTime.now());
        if (inv.getLastRestocked() == null) inv.setLastRestocked(LocalDateTime.now());
        return inventoryRepository.save(inv);
    }

    public Optional<Inventory> findById(Long id) {
        return inventoryRepository.findById(id);
    }

    public Optional<Inventory> findByName(String name) {
        return inventoryRepository.findByMedicineNameIgnoreCase(name);
    }

    public void delete(Long id) {
        inventoryRepository.deleteById(id);
    }

    public Inventory adjustStock(Long id, int delta) {
        Inventory inv = inventoryRepository.findById(id).orElseThrow();
        inv.setStockLevel(Math.max(0, inv.getStockLevel() + delta));
        if (delta > 0) inv.setLastRestocked(LocalDateTime.now());
        inv.setUpdatedAt(LocalDateTime.now());
        return inventoryRepository.save(inv);
    }
}
