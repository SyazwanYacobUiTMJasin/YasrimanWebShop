package com.masbro.yasriman.model;


import java.util.Base64;
import java.util.Date;

public class Inventory {
    private int inventoryID;
    private String inventoryName;
    private double inventoryPricePerItem;
    private int inventoryQuantityExisting;
    private String inventoryDesc;
    private String inventoryStatus;
    private byte[] inventoryImage;
    private String inventoryRole;
    private int inventoryQuantityIn;
    private int accountID;
    private Date invmanageDateChanged;
    private String accountUsername;

    // Getters and Setters for all fields
    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public double getInventoryPricePerItem() {
        return inventoryPricePerItem;
    }

    public void setInventoryPricePerItem(double inventoryPricePerItem) {
        this.inventoryPricePerItem = inventoryPricePerItem;
    }

    public int getInventoryQuantityExisting() {
        return inventoryQuantityExisting;
    }

    public void setInventoryQuantityExisting(int inventoryQuantityExisting) {
        this.inventoryQuantityExisting = inventoryQuantityExisting;
    }

    public String getInventoryDesc() {
        return inventoryDesc;
    }

    public void setInventoryDesc(String inventoryDesc) {
        this.inventoryDesc = inventoryDesc;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public byte[] getInventoryImage() {
        return inventoryImage;
    }

    public void setInventoryImage(byte[] inventoryImage) {
        this.inventoryImage = inventoryImage;
    }

    public String getInventoryRole() {
        return inventoryRole;
    }

    public void setInventoryRole(String inventoryRole) {
        this.inventoryRole = inventoryRole;
    }

    public int getInventoryQuantityIn() {
        return inventoryQuantityIn;
    }

    public void setInventoryQuantityIn(int inventoryQuantityIn) {
        this.inventoryQuantityIn = inventoryQuantityIn;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public Date getInvmanageDateChanged() {
        return invmanageDateChanged;
    }

    public void setInvmanageDateChanged(Date invmanageDateChanged) {
        this.invmanageDateChanged = invmanageDateChanged;
    }
    
    public String getInventoryImageBase64() {
        return (inventoryImage != null) ? Base64.getEncoder().encodeToString(inventoryImage) : null;
    }
    
    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }


}