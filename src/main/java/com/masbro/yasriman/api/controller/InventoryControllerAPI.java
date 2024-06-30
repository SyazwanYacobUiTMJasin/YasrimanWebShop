package com.masbro.yasriman.api.controller;

import com.masbro.yasriman.api.model.InventoryAPI;
import com.masbro.yasriman.api.service.InventoryService;
import com.masbro.yasriman.model.Inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
public class InventoryControllerAPI {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryAPI>> getAllInventory() {
        List<InventoryAPI> inventoryItems = inventoryService.getAllInventory();
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryAPI> getInventoryById(@PathVariable("id") int id) {
        Optional<InventoryAPI> inventory = inventoryService.getInventoryById(id);
        return inventory.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<InventoryAPI> createInventory(@RequestBody InventoryAPI inventory) {
        InventoryAPI savedInventory = inventoryService.saveInventory(inventory);
        return new ResponseEntity<>(savedInventory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryAPI> updateInventory(@PathVariable("id") int id, @RequestBody InventoryAPI inventory) {
        Optional<InventoryAPI> existingInventory = inventoryService.getInventoryById(id);
        if (existingInventory.isPresent()) {
            inventory.setInventoryID(id);
            InventoryAPI updatedInventory = inventoryService.saveInventory(inventory);
            return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<InventoryAPI>> getInventoryByRole(@PathVariable("role") String role) {
        List<InventoryAPI> inventoryItems = inventoryService.getInventoryByRole(role);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<InventoryAPI>> getInventoryByStatus(@PathVariable("status") String status) {
        List<InventoryAPI> inventoryItems = inventoryService.getInventoryByStatus(status);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    @GetMapping("/lowstock/{threshold}")
    public ResponseEntity<List<InventoryAPI>> getLowStockInventory(@PathVariable("threshold") int threshold) {
        List<InventoryAPI> lowStockItems = inventoryService.getLowStockInventory(threshold);
        return new ResponseEntity<>(lowStockItems, HttpStatus.OK);
    }
}