package com.masbro.yasriman.api.service;

import com.masbro.yasriman.api.model.PaymentAPI;
import com.masbro.yasriman.api.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<PaymentAPI> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<PaymentAPI> getPaymentById(int id) {
        return paymentRepository.findById(id);
    }

    @Transactional
    public PaymentAPI createPayment(PaymentAPI payment) {
        return paymentRepository.save(payment);
    }

    public PaymentAPI updatePayment(int id, PaymentAPI paymentDetails) {
        PaymentAPI payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        
        payment.setAccountId(paymentDetails.getAccountId());
        payment.setInventoryId(paymentDetails.getInventoryId());
        payment.setOrderDate(paymentDetails.getOrderDate());
        payment.setPaymentstatus(paymentDetails.getPaymentstatus());
        payment.setOrderid(paymentDetails.getOrderid());
        payment.setPaymentproof(paymentDetails.getPaymentproof());

        return paymentRepository.save(payment);
    }

    @Transactional
    public void deletePayment(int id) {
        PaymentAPI payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        paymentRepository.delete(payment);
    }
}