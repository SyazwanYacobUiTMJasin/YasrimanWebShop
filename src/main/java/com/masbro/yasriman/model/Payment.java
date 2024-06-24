package com.masbro.yasriman.model; 

public class Payment {
	private int paymentid;
	private String paymentstatus;
	private int orderid;
	private byte[] paymentproof;
	
	
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
