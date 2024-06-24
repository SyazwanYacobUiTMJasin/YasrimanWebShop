package com.masbro.yasriman.dao; 

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.masbro.yasriman.model.accounts;
import com.masbro.yasriman.model.orders;
import com.masbro.yasriman.connection.ConnectionManager;

/**
 * OrderDAO.java This DAO class provides CRUD database operations for the
 * table orders in the database.
 *
 */
@Repository
public class OrderDAO {
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM orders JOIN accounts USING (accountid) JOIN inventory USING (inventoryid) JOIN PAYMENT USING (ORDERID) WHERE orderid=?";
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM orders JOIN accounts USING (accountid) JOIN PAYMENT USING (ORDERID)";
    private static final String UPDATE_ORDER_STATUS = "UPDATE orders SET orderstatus=? WHERE orderid=?";
    private static final String INSERT_ORDER_SQL = "INSERT INTO orders (accountID, inventoryID, orderDate, orderStatus, orderTotalPrice, orderQuantity) VALUES (?, ?, ?, ?, ?, ?)";

    public OrderDAO() {}

    public orders selectOrderById(int orderId) throws SQLException {
        orders order = null;
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ORDER_BY_ID)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order = new orders();
                    int orderid = rs.getInt("orderid");
                    int accountId = rs.getInt("accountid");
                    String accountUsername = rs.getString("accountusername");
                    int inventoryId = rs.getInt("inventoryid");
                    String inventoryName = rs.getString("inventoryname");
                    byte[] inventoryImage = rs.getBytes("inventoryimage");
                    LocalDate orderDate = orders.toLocalDate(rs.getDate("orderdate"));
                    String orderStatus = rs.getString("orderstatus");
                    double orderTotalPrice = rs.getDouble("ordertotalprice");
                    String paymentStatus = rs.getString("paymentstatus"); // Retrieve payment status
                    byte[] paymentProof = rs.getBytes("paymentproof");
//                    int orderQuantity = rs.getInt("orderquantity");

                    System.out.println("Order ID: " + orderid);
                    System.out.println("Account ID: " + accountId);
                    System.out.println("Account Username: " + accountUsername);
                    System.out.println("Inventory ID: " + inventoryId);
                    System.out.println("Inventory Name: " + inventoryName);
                    System.out.println("Order Date: " + orderDate);
                    System.out.println("Order Status: " + orderStatus);
                    System.out.println("Order Total Price: " + orderTotalPrice);
//                    System.out.println("Order Quantity: " + orderQuantity);

                    order.setOrderId(orderid);
                    order.setAccountId(accountId);
                    order.setAccountUsername(accountUsername);
                    order.setInventoryId(inventoryId);
                    order.setInventoryName(inventoryName);
                    order.setInventoryImage(inventoryImage);
                    order.setOrderDate(orderDate);
                    order.setOrderStatus(orderStatus);
                    order.setOrderTotalPrice(orderTotalPrice);
                    order.setPaymentStatus(paymentStatus); // Set payment status
                    order.setPaymentProof(paymentProof); // Set payment proof
//                    order.setOrderQuantity(orderQuantity);
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        
        return order;
    }


    public List<orders> selectAllOrders() throws SQLException {
        List<orders> orders = new ArrayList<>();
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL_ORDERS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
            	System.out.println("inside while loop of listallorders");
                orders order = new orders(); 
                    order.setOrderId(rs.getInt("orderid"));
                    order.setAccountId(rs.getInt("accountid"));
                    order.setAccountUsername(rs.getString("accountusername"));
                    order.setOrderDate(order.toLocalDate(rs.getDate("orderdate")));  
                    order.setOrderStatus(rs.getString("orderstatus"));
                    order.setOrderTotalPrice(rs.getDouble("ordertotalprice"));
                    order.setPaymentStatus(rs.getString("paymentstatus"));
                    order.setPaymentProof(rs.getBytes("paymentproof"));
                    orders.add(order);
                    
                    System.out.println("Retrieved Order: " +
                            "OrderId=" + order.getOrderId() +
                            ", AccountId=" + order.getAccountId() +
                            ", AccountUsername=" + order.getAccountUsername() +
                            ", OrderDate=" + order.getOrderDate() +
                            ", OrderStatus=" + order.getOrderStatus() +
                            ", OrderTotalPrice=" + order.getOrderTotalPrice() +
                    		", PaymentStatus=" + order.getPaymentStatus()+
                            ", Paymentproof=" + order.getPaymentProof());;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        
        System.out.println("returning orderList" + orders);
        return orders;
    }

    public boolean updateOrderStatus(int orderId, String orderstatus) throws SQLException {
        boolean rowUpdated = false;
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_ORDER_STATUS)) {
            ps.setString(1, orderstatus);
            ps.setInt(2, orderId);
            rowUpdated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
    }



    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public static int insertOrderIntoDatabase(Connection con, int accountID, int inventoryID, Date orderDate, String orderStatus,
            double orderTotalPrice, int orderQuantity) throws SQLException {
    PreparedStatement stmt = null;
    PreparedStatement selectStmt = null;
    ResultSet rs = null;
    int orderId = 0;

    try {
        // Insert order into the database
        stmt = con.prepareStatement("INSERT INTO orders (accountID, inventoryID, orderDate, orderStatus, orderTotalPrice, orderQuantity) VALUES (?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, accountID);
        stmt.setInt(2, inventoryID);
        stmt.setDate(3, orderDate);
        stmt.setString(4, orderStatus);
        stmt.setDouble(5, orderTotalPrice);
        stmt.setInt(6, orderQuantity);
        int affectedRows = stmt.executeUpdate();
        con.commit();

        // Check if insertion was successful
        if (affectedRows > 0) {
            // Select the orderid for the inserted order
            selectStmt = con.prepareStatement("SELECT orderid FROM orders WHERE accountID = ? AND orderDate = ? ORDER BY orderid DESC");
            selectStmt.setInt(1, accountID);
            selectStmt.setDate(2, orderDate);
            rs = selectStmt.executeQuery();

            if (rs.next()) {
                orderId = rs.getInt("orderid");

                // Use orderId as needed
                System.out.println("Inserted Order ID: " + orderId);

                // Optionally update orders.orderid if necessary
                // UPDATE orders SET orderid = ? WHERE accountID = ? AND orderDate = ?
            } else {
                throw new SQLException("Failed to retrieve orderid for the inserted order");
            }
        }
    } finally {
        // Close resources in the finally block
        if (rs != null) {
            rs.close();
        }
        if (selectStmt != null) {
            selectStmt.close();
        }
        if (stmt != null) {
            stmt.close();
        }
    }

    return orderId; // Return the generated orderId
}

}
