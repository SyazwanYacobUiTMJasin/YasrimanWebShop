package com.masbro.yasriman.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masbro.yasriman.api.model.InventoryAPI;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryAPI, Integer> {
    
    // Find all inventory items by their role
    List<InventoryAPI> findByInventoryRole(String inventoryRole);
    
    // Find inventory items by status
    List<InventoryAPI> findByInventoryStatus(String status);
    
    // Find inventory items with quantity less than a given amount
    List<InventoryAPI> findByInventoryQuantityExistingLessThan(int quantity);
}