package com.masbro.yasriman.api.service;

import com.masbro.yasriman.model.Inventory;
import com.masbro.yasriman.api.repository.InventoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventory() {
        List<Inventory> inventoryItems = inventoryRepository.findAll();
        System.out.println("Retrieved inventory items: " + inventoryItems);
        return inventoryItems;
    }

    public Optional<Inventory> getInventoryById(int inventoryId) {
        Optional<Inventory> inventory = inventoryRepository.findById(inventoryId);
        System.out.println("Retrieved inventory by ID: " + inventory);
        return inventory;
    }

    public Inventory saveInventory(Inventory inventory) {
        Inventory savedInventory = inventoryRepository.save(inventory);
        System.out.println("Saved inventory: " + savedInventory);
        return savedInventory;
    }

    public List<Inventory> getInventoryByRole(String role) {
        List<Inventory> inventoryItems = inventoryRepository.findByInventoryRole(role);
        System.out.println("Retrieved inventory items by role: " + inventoryItems);
        return inventoryItems;
    }

    public List<Inventory> getInventoryByStatus(String status) {
        List<Inventory> inventoryItems = inventoryRepository.findByInventoryStatus(status);
        System.out.println("Retrieved inventory items by status: " + inventoryItems);
        return inventoryItems;
    }

    public List<Inventory> getLowStockInventory(int threshold) {
        List<Inventory> lowStockItems = inventoryRepository.findByInventoryQuantityExistingLessThan(threshold);
        System.out.println("Retrieved low stock inventory items: " + lowStockItems);
        return lowStockItems;
    }
}