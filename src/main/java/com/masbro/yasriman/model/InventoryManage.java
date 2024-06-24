package com.masbro.yasriman.model;
 

import java.util.Date;

public class InventoryManage {
    private int invManageID;
    private int accountID;
    private int inventoryID;
    private Date invmanageDateChanged;

    // Getters and setters
    public int getInvManageID() {
        return invManageID;
    }

    public void setInvManageID(int invManageID) {
        this.invManageID = invManageID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public Date getInvmanageDateChanged() {
        return invmanageDateChanged;
    }

    public void setInvmanageDateChanged(Date invmanageDateChanged) {
        this.invmanageDateChanged = invmanageDateChanged;
    }
}