package com.masbro.yasriman.api.model;  

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class OrderAPI {
    @Id
    @Column(name = "orderid")
    private int orderId;
    @Column(name = "accountid")
    private int accountId; 
    @Column(name = "inventoryid")
    private int inventoryId;  
    @Column(name = "orderdate")
    private LocalDate orderDate;
    @Column(name = "orderstatus")
    private String orderStatus;
    @Column(name = "ordertotalprice")
    private double orderTotalPrice;
    @Column(name = "orderquantity")
    private double orderQuantity;

    
    public void orders(int accountId, int inventoryId,  LocalDate orderDate, String orderStatus, double orderTotalPrice,
            int orderQuantity) {
        // TODO Auto-generated constructor stub
        this.accountId = accountId;
        this.inventoryId = inventoryId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderTotalPrice = orderTotalPrice; 
        this.orderQuantity = orderQuantity; 
    }

    public void orders()
    {}
    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
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

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public static Date toSqlDate(LocalDate date) {
        return date == null ? null : Date.valueOf(date);
    }

    public static LocalDate toLocalDate(Date date) {
        return date == null ? null : date.toLocalDate();
    }
}