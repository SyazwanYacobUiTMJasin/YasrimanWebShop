package com.masbro.yasriman.api.controller;

import com.masbro.yasriman.api.model.PaymentAPI;
import com.masbro.yasriman.api.model.PaymentRequest;
import com.masbro.yasriman.api.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentControllerAPI {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public List<PaymentAPI> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentAPI> getPaymentById(@PathVariable int id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentAPI> createPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            PaymentAPI payment = new PaymentAPI();
            payment.setAccountId(paymentRequest.getAccountId());
            payment.setInventoryId(paymentRequest.getInventoryId());
            payment.setOrderid(paymentRequest.getOrderid());
            payment.setPaymentstatus(paymentRequest.getPaymentstatus());
            payment.setOrderDate(LocalDateTime.now());

            byte[] paymentProofBytes = Base64.getDecoder().decode(paymentRequest.getPaymentproof().getData());
            payment.setPaymentproof(paymentProofBytes);

            PaymentAPI createdPayment = paymentService.createPayment(payment);
            return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PaymentAPI> updatePayment(@PathVariable int id, @RequestBody PaymentAPI paymentDetails) {
        try {
            PaymentAPI updatedPayment = paymentService.updatePayment(id, paymentDetails);
            return ResponseEntity.ok(updatedPayment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable int id) {
        try {
            paymentService.deletePayment(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}