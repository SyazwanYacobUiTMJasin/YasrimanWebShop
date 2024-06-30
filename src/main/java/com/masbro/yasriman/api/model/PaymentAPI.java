package com.masbro.yasriman.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "payment")
public class PaymentAPI {

    @Id
    @Column(name = "paymentid")
	private int paymentid;

    @Column(name = "orderid")
    private int orderid;

    @Column(name = "accountId")
	private int accountId;

    @Column(name = "inventoryId")
    private int inventoryId;

    @Column(name = "orderDate")
    private LocalDateTime orderDate;

    @Column(name = "paymentstatus")
	private String paymentstatus;
	
    @Lob
    @Transient
    private byte[] paymentproof;

    public PaymentAPI() { 
    }
    
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
