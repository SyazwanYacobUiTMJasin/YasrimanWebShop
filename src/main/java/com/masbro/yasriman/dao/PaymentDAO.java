package com.masbro.yasriman.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import com.masbro.yasriman.model.accounts;
import com.masbro.yasriman.connection.SpringConnectionManager;
import com.masbro.yasriman.model.Payment;

@Repository
public class PaymentDAO {
	@Autowired
    private SpringConnectionManager ConnectionManager;

	@Autowired
    public PaymentDAO(SpringConnectionManager connectionManager) {
        this.ConnectionManager = connectionManager;
    }
	private static final String VIEW_ONE_ACCOUNT = "SELECT * FROM accounts WHERE accountid=?";
	private static final String insertPaymentSQL = "INSERT INTO payment (orderid, accountid, inventoryid, orderdate, paymentproof) VALUES (?, ?, ?, ?, ?)";
	
	public void insertOrderAndPayment(int accountID, int inventoryID, LocalDateTime  orderDate, String orderStatus,
	        double orderTotalPrice, int orderQuantity, Payment payment, int count) throws SQLException {
    Connection con = null;
    
    try {
        con = ConnectionManager.getConnection();
        con.setAutoCommit(false);

     // Convert LocalDateTime to Timestamp for database insertion
        Timestamp orderTimestamp = Timestamp.valueOf(orderDate);
        
        // Insert order
        System.out.println("insertOrderAndPayment :" + orderQuantity);
        int orderId = OrderDAO.insertOrderIntoDatabase(con, accountID, inventoryID, orderTimestamp , orderStatus, orderTotalPrice, orderQuantity);
        
        // Set the retrieved orderId in the payment object
        payment.setOrderid(orderId);
        payment.setAccountId(accountID);
        payment.setInventoryId(inventoryID);
        payment.setOrderDate(orderDate);
        
        // Insert payment info
        if(count==0)
        	insertPaymentInfo(con, payment);
        count++;
        // Commit the transaction
        con.commit();
    } catch (SQLException e) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
        throw e;
    } finally {
        if (con != null) {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


	public static void insertPaymentInfo(Connection con, Payment payment) throws SQLException {
	    PreparedStatement stmt = null;

	    try {
	        // Truncate the orderDate to seconds
	        System.out.println("Timestamp.valueOf(payment.getOrderDate()" + Timestamp.valueOf(payment.getOrderDate()));

	        LocalDateTime truncatedDate = payment.getOrderDate().withNano(0);
	        Timestamp truncatedTimestamp = Timestamp.valueOf(truncatedDate);

	        stmt = con.prepareStatement(insertPaymentSQL);
	        stmt.setInt(1, payment.getOrderid());
	        stmt.setInt(2, payment.getAccountId());
	        stmt.setInt(3, payment.getInventoryId());
	        stmt.setTimestamp(4, truncatedTimestamp);
	        stmt.setBytes(5, payment.getPaymentproof());

	        stmt.executeUpdate();
	    } finally {
	        if (stmt != null) {
	            stmt.close();
	        }
	    }
	}


	
	public accounts viewCustomerAddress(int accountid) {
		// TODO Auto-generated method stub
		accounts account = null;
	    try {
	        Connection con = ConnectionManager.getConnection();
	        PreparedStatement ps = con.prepareStatement(VIEW_ONE_ACCOUNT);
	        ps.setInt(1, accountid);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int accountidsql = rs.getInt("accountid");
	            String accountrole = rs.getString("accountrole");
	            String accountusername = rs.getString("accountusername");
	            String accountpassword = rs.getString("accountpassword");
	            String accountemail = rs.getString("accountemail");
	            String accountfirstname = rs.getString("accountfirstname");
	            String accountlastname = rs.getString("accountlastname");
	            String accountphonenum = rs.getString("accountphonenum");
	            String accountstreet = rs.getString("accountstreet");
	            String accountstate = rs.getString("accountstate");
	            String accountcity = rs.getString("accountcity");
	            int accountpostalcode = rs.getInt("accountpostalcode");
	            byte[] blobdata = rs.getBytes("accountpicture");
	            byte[] accountpicture = null;
	            if (blobdata != null) {
	                accountpicture = blobdata;
	            }

	            account = new accounts(accountidsql, accountrole, accountusername, accountpassword, accountemail, accountfirstname, accountlastname, accountphonenum, accountstreet, accountstate, accountcity, accountpostalcode, accountpicture);
	        }

	        con.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return account;
	}



	
	
}