package com.masbro.yasriman.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class orders {
    private int orderId;
    private int accountId;
    private String accountUsername;
    private int inventoryId;
    private String inventoryName;
    private byte[] inventoryImage;
    private LocalDateTime  orderDate;
    private String orderStatus;
    private double orderTotalPrice;
    private int orderQuantity;
    private String paymentStatus;
    private byte[] paymentProof;
    private double sumOrderTotalPrice;
    private int paymentID;
    private byte[] base64Image;
    
    public byte[] getPaymentProof() {
		return paymentProof;
	}

	public void setPaymentProof(byte[] paymentproof) {
		this.paymentProof = paymentproof;
	}

	public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public orders(int accountId, int inventoryId,  LocalDateTime  orderDate, String orderStatus, double orderTotalPrice,
			int orderQuantity) {
		// TODO Auto-generated constructor stub
    	this.accountId = accountId;
    	this.inventoryId = inventoryId;
    	this.orderDate = orderDate;
    	this.orderStatus = orderStatus;
    	this.orderTotalPrice = orderTotalPrice; 
    	this.orderQuantity = orderQuantity; 
	}

    public orders()
    {}
	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getInventoryName() {
		return inventoryName;
	}

	public void setInventoryName(String inventoryName) {
		this.inventoryName = inventoryName;
	}

	public byte[] getInventoryImage() {
		return inventoryImage;
	}

	public void setInventoryImage(byte[] inventoryImage) {
		this.inventoryImage = inventoryImage;
	}

    

    // getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public LocalDateTime  getOrderDate() {
        return orderDate;
    }


    public void setOrderDate(LocalDateTime  orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public static java.sql.Timestamp toSqlDate(LocalDateTime  dateTime) {
        return dateTime == null ? null : java.sql.Timestamp.valueOf(dateTime);
    }

    public static LocalDateTime  toLocalDate(java.sql.Timestamp  timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

	public void setSumOrderTotalPrice(double sumOrderTotalPrice) {
		this.sumOrderTotalPrice = sumOrderTotalPrice;
	}
	
	public double getSumOrderTotalPrice() {
		return sumOrderTotalPrice;
	}

	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}
	
	public int getPaymentID() {
		return paymentID;
	}

	public void setImageBase64(byte[] base64Image) {
		this.base64Image = base64Image;
	}
	
	public byte[] getImageBase64() {
		return base64Image;
	}
}
