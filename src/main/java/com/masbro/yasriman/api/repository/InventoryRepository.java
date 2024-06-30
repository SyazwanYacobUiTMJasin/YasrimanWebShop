package com.masbro.yasriman.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masbro.yasriman.model.Inventory;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    
    // Find all inventory items by their role
    List<Inventory> findByInventoryRole(String inventoryRole);
    
    // Find inventory items by status
    List<Inventory> findByInventoryStatus(String status);
    
    // Find inventory items with quantity less than a given amount
    List<Inventory> findByInventoryQuantityExistingLessThan(int quantity);
}