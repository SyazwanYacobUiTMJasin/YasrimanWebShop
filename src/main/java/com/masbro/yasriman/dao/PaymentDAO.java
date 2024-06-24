package com.masbro.yasriman.dao; 

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import org.springframework.stereotype.Repository;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.masbro.yasriman.connection.ConnectionManager;
import java.sql.Date;
import com.masbro.yasriman.model.accounts;
import com.masbro.yasriman.model.Payment;

@Repository
public class PaymentDAO {
	private static final String VIEW_ONE_ACCOUNT = "SELECT * FROM accounts WHERE accountid=?";
	
	public static void insertOrderAndPayment(int accountID, int inventoryID, Date orderDate, String orderStatus,
            double orderTotalPrice, int orderQuantity, Payment payment) throws SQLException {
    Connection con = null;

    try {
        con = ConnectionManager.getConnection();
        con.setAutoCommit(false);

        // Insert order
        System.out.println("insertOrderAndPayment :" + orderQuantity);
        int orderId = OrderDAO.insertOrderIntoDatabase(con, accountID, inventoryID, orderDate, orderStatus, orderTotalPrice, orderQuantity);

        // Set the retrieved orderId in the payment object
        payment.setOrderid(orderId);

        // Insert payment info
        insertPaymentInfo(con, payment);

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
String insertPaymentSQL = "INSERT INTO payment (orderid, paymentproof) VALUES (?, ?)";
stmt = con.prepareStatement(insertPaymentSQL);
stmt.setInt(1, payment.getOrderid()); 
stmt.setBytes(2, payment.getPaymentproof()); 

stmt.executeUpdate();
} finally {
if (stmt != null) {
stmt.close();
}
}
}

	
	public static accounts viewCustomerAddress(int accountid) {
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
	            Blob blob = rs.getBlob("accountpicture");
	            byte[] accountpicture = null;
	            if (blob != null) {
	                accountpicture = blob.getBytes(1, (int) blob.length());
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
