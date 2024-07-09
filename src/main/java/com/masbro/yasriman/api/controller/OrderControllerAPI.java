package com.masbro.yasriman.api.controller;

import com.masbro.yasriman.api.model.OrderAPI;
import com.masbro.yasriman.api.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderControllerAPI {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderAPI>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderAPI> getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderAPI> createOrder(@RequestBody OrderAPI order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.saveOrder(order));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable int id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status)
                ? ResponseEntity.ok("Order status updated successfully")
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        return orderService.deleteOrder(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}