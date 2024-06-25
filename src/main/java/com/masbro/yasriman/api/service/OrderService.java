package com.masbro.yasriman.api.service; 

import com.masbro.yasriman.api.model.OrderAPI;
import com.masbro.yasriman.api.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderAPI> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<OrderAPI> getOrderById(int orderId) {
        return orderRepository.findById(orderId);
    }

    public OrderAPI saveOrder(OrderAPI order) {
        return orderRepository.save(order);
    }

    public boolean updateOrderStatus(int orderId, String orderStatus) {
        Optional<OrderAPI> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            OrderAPI order = orderOpt.get();
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
            return true;
        }
        return false;
    }
}