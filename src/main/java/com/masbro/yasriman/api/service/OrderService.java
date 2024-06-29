package com.masbro.yasriman.api.service; 

import com.masbro.yasriman.api.model.OrderAPI;
import com.masbro.yasriman.api.repository.OrderRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderAPI> getAllOrders() {
        entityManager.clear();
        List<OrderAPI> orders = orderRepository.findAll();
        System.out.println("Retrieved orders: " + orders); // Debugging statement
        return orders;
    }

    public Optional<OrderAPI> getOrderById(int orderId) {
        Optional<OrderAPI> order = orderRepository.findById(orderId);
        System.out.println("Retrieved order by ID: " + order); // Debugging statement
        return order;
    }

    public OrderAPI saveOrder(OrderAPI order) {
        OrderAPI savedOrder = orderRepository.save(order);
        System.out.println("Saved order: " + savedOrder); // Debugging statement
        return savedOrder;
    }

    public boolean updateOrderStatus(int orderId, String orderStatus) {
        Optional<OrderAPI> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            OrderAPI order = orderOpt.get();
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
            System.out.println("Updated order status: " + order); // Debugging statement
            return true;
        }
        return false;
    }
}
