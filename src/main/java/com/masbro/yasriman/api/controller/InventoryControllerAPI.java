package com.masbro.yasriman.api.controller;

import com.masbro.yasriman.model.Inventory;
import com.masbro.yasriman.api.service.InventoryService;

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
    public ResponseEntity<List<Inventory>> getAllInventory() {
        List<Inventory> inventoryItems = inventoryService.getAllInventory();
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable("id") int id) {
        Optional<Inventory> inventory = inventoryService.getInventoryById(id);
        return inventory.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        Inventory savedInventory = inventoryService.saveInventory(inventory);
        return new ResponseEntity<>(savedInventory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable("id") int id, @RequestBody Inventory inventory) {
        Optional<Inventory> existingInventory = inventoryService.getInventoryById(id);
        if (existingInventory.isPresent()) {
            inventory.setInventoryID(id);
            Inventory updatedInventory = inventoryService.saveInventory(inventory);
            return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<Inventory>> getInventoryByRole(@PathVariable("role") String role) {
        List<Inventory> inventoryItems = inventoryService.getInventoryByRole(role);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Inventory>> getInventoryByStatus(@PathVariable("status") String status) {
        List<Inventory> inventoryItems = inventoryService.getInventoryByStatus(status);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    @GetMapping("/lowstock/{threshold}")
    public ResponseEntity<List<Inventory>> getLowStockInventory(@PathVariable("threshold") int threshold) {
        List<Inventory> lowStockItems = inventoryService.getLowStockInventory(threshold);
        return new ResponseEntity<>(lowStockItems, HttpStatus.OK);
    }
}