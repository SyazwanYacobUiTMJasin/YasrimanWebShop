package com.masbro.yasriman.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masbro.yasriman.api.model.OrderAPI;

public interface OrderRepository extends JpaRepository<OrderAPI, Integer> {
}