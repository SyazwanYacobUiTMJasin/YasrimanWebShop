package com.masbro.yasriman.api.controller; 

import com.masbro.yasriman.api.model.OrderAPI;
import com.masbro.yasriman.api.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderControllerAPI {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderAPI> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderAPI> getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public OrderAPI createOrder(@RequestBody OrderAPI order) {
        return orderService.saveOrder(order);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable int id, @RequestParam String status) {
        boolean updated = orderService.updateOrderStatus(id, status);
        if (updated) {
            return ResponseEntity.ok("Order status updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}