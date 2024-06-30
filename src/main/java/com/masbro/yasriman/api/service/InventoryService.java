package com.masbro.yasriman.api.service;

import com.masbro.yasriman.api.model.InventoryAPI;
import com.masbro.yasriman.api.repository.InventoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<InventoryAPI> getAllInventory() {
        List<InventoryAPI> inventoryItems = inventoryRepository.findAll();
        System.out.println("Retrieved inventory items: " + inventoryItems);
        return inventoryItems;
    }

    public Optional<InventoryAPI> getInventoryById(int inventoryId) {
        Optional<InventoryAPI> inventory = inventoryRepository.findById(inventoryId);
        System.out.println("Retrieved inventory by ID: " + inventory);
        return inventory;
    }

    public InventoryAPI saveInventory(InventoryAPI inventory) {
        InventoryAPI savedInventory = inventoryRepository.save(inventory);
        System.out.println("Saved inventory: " + savedInventory);
        return savedInventory;
    }

    public List<InventoryAPI> getInventoryByRole(String role) {
        List<InventoryAPI> inventoryItems = inventoryRepository.findByInventoryRole(role);
        System.out.println("Retrieved inventory items by role: " + inventoryItems);
        return inventoryItems;
    }

    public List<InventoryAPI> getInventoryByStatus(String status) {
        List<InventoryAPI> inventoryItems = inventoryRepository.findByInventoryStatus(status);
        System.out.println("Retrieved inventory items by status: " + inventoryItems);
        return inventoryItems;
    }

    public List<InventoryAPI> getLowStockInventory(int threshold) {
        List<InventoryAPI> lowStockItems = inventoryRepository.findByInventoryQuantityExistingLessThan(threshold);
        System.out.println("Retrieved low stock inventory items: " + lowStockItems);
        return lowStockItems;
    }
}