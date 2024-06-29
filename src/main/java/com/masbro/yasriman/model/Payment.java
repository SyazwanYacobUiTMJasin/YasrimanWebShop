package com.masbro.yasriman.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Payment {
	private int accountId;
    private int inventoryId;
    private LocalDateTime orderDate;
	private int paymentid;
	private String paymentstatus;
	private int orderid;
	private byte[] paymentproof;
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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate2) {
        this.orderDate = orderDate2;
    }
    
	public byte[] getPaymentproof() {
		return paymentproof;
	}

	public void setPaymentproof(byte[] paymentproof) {
		this.paymentproof = paymentproof;
	}

	public Payment() { 
	}
	
	public int getPaymentid() {
		return paymentid;
	}
	public void setPaymentid(int paymentid) {
		this.paymentid = paymentid;
	}
	public String getPaymentstatus() {
		return paymentstatus;
	}
	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}


	
}
