package com.masbro.yasriman.api.service;

import com.masbro.yasriman.api.model.OrderAPI;
import com.masbro.yasriman.api.repository.OrderRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return orderRepository.findAll();
    }

    public Optional<OrderAPI> getOrderById(int orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional
    public OrderAPI saveOrder(OrderAPI order) {
        return orderRepository.save(order);
    }

    @Transactional
    public Optional<OrderAPI> updateOrder(int orderId, OrderAPI updatedOrder) {
        return orderRepository.findById(orderId)
                .map(existingOrder -> {
                    existingOrder.setOrderStatus(updatedOrder.getOrderStatus());
                    return orderRepository.save(existingOrder);
                });
    }

    @Transactional
    public boolean updateOrderStatus(int orderId, String orderStatus) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setOrderStatus(orderStatus);
                    orderRepository.save(order);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public boolean deleteOrder(int orderId) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    orderRepository.delete(order);
                    return true;
                })
                .orElse(false);
    }
}