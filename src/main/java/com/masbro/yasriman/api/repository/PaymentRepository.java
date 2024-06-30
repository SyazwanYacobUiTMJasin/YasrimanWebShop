package com.masbro.yasriman.api.repository;

import com.masbro.yasriman.api.model.PaymentAPI;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentAPI, Integer> {
}