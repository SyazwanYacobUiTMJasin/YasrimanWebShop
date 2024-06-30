package com.masbro.yasriman.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.masbro.yasriman.model.orders;
import com.masbro.yasriman.connection.SpringConnectionManager;

/**
 * OrderDAO.java This DAO class provides CRUD database operations for the
 * table orders in the database.
 *
 */
@Repository
public class OrderDAO {
    @Autowired
    private SpringConnectionManager ConnectionManager;

    @Autowired
    public OrderDAO(SpringConnectionManager connectionManager) {
        this.ConnectionManager = connectionManager;
    }
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM orders JOIN accounts USING (accountid) JOIN inventory using (INVENTORYID) JOIN PAYMENT USING (ORDERID) WHERE orderid=?";
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM orders JOIN accounts USING (accountid) JOIN inventory using (INVENTORYID) JOIN PAYMENT USING (ORDERID)";
    private static final String UPDATE_ORDER_STATUS = "UPDATE orders SET orderstatus = ? WHERE orderid = ?";
    private static final String UPDATE_PAYMENT_STATUS = "UPDATE payment SET paymentstatus = ? WHERE orderid = ?";
    private static final String INSERT_ORDER_SQL = "INSERT INTO orders (accountID, inventoryID, orderDate, orderStatus, orderTotalPrice, orderQuantity) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_ORDERS_GROUPBY_ORDERID = "SELECT \r\n"
                    + "    ORDERS.orderid, \r\n"
                    + "    ORDERS.orderdate, \r\n"
                    + "    ACCOUNTS.accountusername, \r\n"
                    + "    ORDERS.orderstatus, \r\n"
                    + "    PAYMENT.paymentstatus, \r\n"
                    + "    SUM(ORDERS.ordertotalprice)\r\n"
                    + "FROM ORDERS\r\n"
                    + "JOIN ACCOUNTS\r\n"
                    + "    ON ORDERS.ACCOUNTID = ACCOUNTS.ACCOUNTID\r\n"
                    + "JOIN PAYMENT\r\n"
                    + "    ON ORDERS.ORDERID = PAYMENT.ORDERID\r\n"
                    + "GROUP BY  \r\n"
                    + "    ORDERS.orderid, \r\n"
                    + "    ORDERS.orderdate, \r\n"
                    + "    ACCOUNTS.accountusername, \r\n"
                    + "    ORDERS.orderstatus, \r\n"
                    + "    PAYMENT.paymentstatus";
    
    public OrderDAO() {}

    public orders selectOrderById(int orderId) throws SQLException {
        orders order = null;
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ORDER_BY_ID)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order = mapOrder(rs);
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        
        return order;
    }


    private orders mapOrder(ResultSet rs) throws SQLException {
        orders order = new orders();
        order.setOrderId(rs.getInt("orderid"));
        order.setAccountId(rs.getInt("accountid"));
        order.setAccountUsername(rs.getString("accountusername"));
        order.setInventoryId(rs.getInt("inventoryid"));
        order.setInventoryName(rs.getString("inventoryname"));
        // Assuming "orderDate" in database is of type TIMESTAMP
        Timestamp timestamp = rs.getTimestamp("orderdate");
        if (timestamp != null) {
            order.setOrderDate(timestamp.toLocalDateTime()); // Directly set LocalDateTime
        }
        order.setOrderStatus(rs.getString("orderstatus"));
        order.setOrderTotalPrice(rs.getDouble("ordertotalprice"));
        // Assuming these fields are present in your result set
        order.setPaymentStatus(rs.getString("paymentstatus"));
        order.setPaymentProof(rs.getBytes("paymentproof"));
        return order;
    }


    public List<orders> selectAllOrders() throws SQLException {
        List<orders> ordersList = new ArrayList<>();
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL_ORDERS_GROUPBY_ORDERID);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
            	orders order = new orders();
                order.setOrderId(rs.getInt("orderid"));
                Timestamp timestamp = rs.getTimestamp("orderdate");
                if (timestamp != null) {
                    order.setOrderDate(timestamp.toLocalDateTime()); // Directly set LocalDateTime
                }
                order.setAccountUsername(rs.getString("accountusername"));
                order.setOrderStatus(rs.getString("orderstatus"));
                order.setPaymentStatus(rs.getString("paymentstatus"));
                order.setSumOrderTotalPrice(rs.getDouble("sum"));
                ordersList.add(order);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        
        return ordersList;
    }

    public boolean updateOrderStatus(int orderId, String orderStatus, String paymentStatus) throws SQLException {
        boolean rowUpdated = false;
        Connection con = null;
        PreparedStatement psOrder = null;
        PreparedStatement psPayment = null;
        PreparedStatement psInventory = null;

        try {
            con = ConnectionManager.getConnection();
            con.setAutoCommit(false);  // Start transaction

            // Update order status if it's not null
            if (orderStatus != null && !orderStatus.isEmpty()) {
                psOrder = con.prepareStatement(UPDATE_ORDER_STATUS);
                psOrder.setString(1, orderStatus);
                psOrder.setInt(2, orderId);
                int orderRowsAffected = psOrder.executeUpdate();
                
                if (orderRowsAffected > 0) {
                    rowUpdated = true;
                }
            } else {
                // Set orderStatus to 'CANCELLED' if null
                orderStatus = "CANCELLED";
                psOrder = con.prepareStatement(UPDATE_ORDER_STATUS);
                psOrder.setString(1, orderStatus);
                psOrder.setInt(2, orderId);
                int orderRowsAffected = psOrder.executeUpdate();
                
                if (orderRowsAffected > 0) {
                    rowUpdated = true;
                }
            }

            // Update payment status only if it's not null
            if (paymentStatus != null && !paymentStatus.isEmpty()) {
                psPayment = con.prepareStatement(UPDATE_PAYMENT_STATUS);
                psPayment.setString(1, paymentStatus);
                psPayment.setInt(2, orderId);
                int paymentRowsAffected = psPayment.executeUpdate();
                
                if (paymentRowsAffected > 0) {
                    rowUpdated = true;
                }

                // If payment status is set to "APPROVED", update inventory
                if ("APPROVED".equalsIgnoreCase(paymentStatus) && "PACKAGING".equalsIgnoreCase(orderStatus)) {
                    // Fetch order details
                    String selectOrderSQL = "SELECT inventoryid, orderquantity FROM orders WHERE orderid = ?";
                    PreparedStatement psSelectOrder = con.prepareStatement(selectOrderSQL);
                    psSelectOrder.setInt(1, orderId);
                    ResultSet rs = psSelectOrder.executeQuery();

                    while (rs.next()) {
                        int inventoryId = rs.getInt("inventoryid");
                        int orderQuantity = rs.getInt("orderquantity");

                        // Update inventory quantity
                        String updateInventorySQL = "UPDATE inventory SET inventoryquantityexisting = inventoryquantityexisting - ? WHERE inventoryid = ?";
                        psInventory = con.prepareStatement(updateInventorySQL);
                        psInventory.setInt(1, orderQuantity);
                        psInventory.setInt(2, inventoryId);
                        psInventory.executeUpdate();
                    }
                }
            } else {
                // Set paymentStatus to 'NOTAPPROVED' if null
                paymentStatus = "NOTAPPROVED";
                psPayment = con.prepareStatement(UPDATE_PAYMENT_STATUS);
                psPayment.setString(1, paymentStatus);
                psPayment.setInt(2, orderId);
                int paymentRowsAffected = psPayment.executeUpdate();
                
                if (paymentRowsAffected > 0) {
                    rowUpdated = true;
                }
            }

            // Commit transaction if updates were successful
            if (rowUpdated) {
                con.commit();
            } else {
                con.rollback();
            }

            System.out.println("order status: " + orderStatus);
            System.out.println("payment status: " + paymentStatus);

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
            printSQLException(e);
        } finally {
            if (psOrder != null) psOrder.close();
            if (psPayment != null) psPayment.close();
            if (psInventory != null) psInventory.close();
            if (con != null) {
                con.setAutoCommit(true);  // Reset to default
                con.close();
            }
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

    	public static int insertOrderIntoDatabase(Connection con, int accountID, int inventoryID, Timestamp orderDateTime, String orderStatus,
            double orderTotalPrice, int orderQuantity) throws SQLException {
        PreparedStatement stmt = null;
        PreparedStatement selectStmt = null;
        ResultSet rs = null;
        int orderId = 0;
        
        // Truncate the orderDateTime to seconds
        Timestamp truncatedOrderDateTime = truncateToSeconds(orderDateTime);
        
        try {
            // Check the maximum order id for orders with the same accountID and orderDateTime
            String maxOrderIdQuery = "SELECT MAX(orderid) AS max_orderid FROM orders WHERE accountID = ? AND orderDate = ?";
            selectStmt = con.prepareStatement(maxOrderIdQuery);
            selectStmt.setInt(1, accountID);
            selectStmt.setTimestamp(2, truncatedOrderDateTime);
            rs = selectStmt.executeQuery();
            
            if (rs.next()) {
                orderId = rs.getInt("max_orderid");
            }
            
            if (orderId == 0) {
                // If no existing order for this account and datetime, get the overall max orderid
                String overallMaxOrderIdQuery = "SELECT MAX(orderid) AS max_orderid FROM orders";
                selectStmt = con.prepareStatement(overallMaxOrderIdQuery);
                rs = selectStmt.executeQuery();
                
                if (rs.next()) {
                    orderId = rs.getInt("max_orderid");
                }
            }
            
            // Increment the order id for the new order
            orderId++;
            
            // Insert order into the database
            stmt = con.prepareStatement("INSERT INTO orders (orderid, accountID, inventoryID, orderDate, orderStatus, orderTotalPrice, orderQuantity) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, orderId);
            stmt.setInt(2, accountID);
            stmt.setInt(3, inventoryID);
            stmt.setTimestamp(4, truncatedOrderDateTime);
            stmt.setString(5, orderStatus);
            stmt.setDouble(6, orderTotalPrice);
            stmt.setInt(7, orderQuantity);
            int affectedRows = stmt.executeUpdate();
            
            stmt = con.prepareStatement("SELECT MIN(orderid) AS min_orderid FROM orders WHERE accountID = ? AND orderDate = ?");
            stmt.setInt(1, accountID);
            stmt.setTimestamp(2, truncatedOrderDateTime); 
            rs = stmt.executeQuery(); 
            
            if (rs.next()) {
                orderId = rs.getInt("min_orderid");
            }
            
            
            stmt = con.prepareStatement("UPDATE orders SET orderid = ? WHERE accountID = ? AND orderDate = ?");
            stmt.setInt(1, orderId);
            stmt.setInt(2, accountID);
            stmt.setTimestamp(3, truncatedOrderDateTime);
            
            stmt.executeUpdate();
            con.commit();
            
            // Check if insertion was successful
            if (affectedRows > 0) {
                System.out.println("Inserted Order ID: " + orderId);
            } else {
                throw new SQLException("Failed to insert order");
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
    	
    	private static Timestamp truncateToSeconds(Timestamp timestamp) {
    	    long milliseconds = timestamp.getTime();
    	    return new Timestamp((milliseconds / 1000) * 1000);
    	}

}