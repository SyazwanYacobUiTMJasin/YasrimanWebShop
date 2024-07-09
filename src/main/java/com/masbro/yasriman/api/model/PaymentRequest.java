package com.masbro.yasriman.api.model;

public class PaymentRequest {
    private int accountId;
    private int inventoryId;
    private int orderid;
    private String paymentstatus;
    private PaymentProof paymentproof;

    // Getters and Setters
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public PaymentProof getPaymentproof() {
        return paymentproof;
    }

    public void setPaymentproof(PaymentProof paymentproof) {
        this.paymentproof = paymentproof;
    }
}
