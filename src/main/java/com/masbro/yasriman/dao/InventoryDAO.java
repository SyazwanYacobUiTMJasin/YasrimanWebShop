package com.masbro.yasriman.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.masbro.yasriman.connection.ConnectionManager;
import com.masbro.yasriman.model.Inventory;
import com.masbro.yasriman.model.Plant;
import com.masbro.yasriman.model.Tool;
import com.masbro.yasriman.model.accounts;
import com.masbro.yasriman.model.orders;

@Repository
public class InventoryDAO {

    private static final String INSERT_INVENTORY_SQL = "INSERT INTO INVENTORY (INVENTORYNAME, INVENTORYPRICEPERITEM, INVENTORYQUANTITYEXISTING, INVENTORYDESC, INVENTORYSTATUS, INVENTORYIMAGE, INVENTORYROLE, INVENTORYQUANTITYIN) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_INVENTORY_MANAGE_SQL = "INSERT INTO INVENTORYMANAGE (ACCOUNTID, INVENTORYID, INVMANAGEDATECHANGED) VALUES (?, ?, ?)";
    private static final String INSERT_PLANT_SQL = "INSERT INTO PLANT (PLANTMANUAL, INVENTORYID) VALUES (?, ?)";
    private static final String INSERT_TOOL_SQL = "INSERT INTO TOOL (TOOLCATEGORY, INVENTORYID) VALUES (?, ?)";
    private static final String UPDATE_INVENTORY_SQL = "UPDATE INVENTORY SET INVENTORYNAME = ?, INVENTORYPRICEPERITEM = ?, INVENTORYQUANTITYEXISTING = ?, INVENTORYDESC = ?, INVENTORYSTATUS = ?, INVENTORYIMAGE = ?, INVENTORYROLE = ?, INVENTORYQUANTITYIN = ? WHERE INVENTORYID = ?";
//    private static final String UPDATE_PLANT_SQL = "UPDATE PLANT SET PLANTMANUAL = ? WHERE PLANTID = ?";
//    private static final String UPDATE_TOOL_SQL = "UPDATE TOOL SET TOOLCATEGORY = ? WHERE TOOLID = ?";
    private static final String UPDATE_PLANT_SQL = "UPDATE PLANT SET PLANTMANUAL = ? WHERE INVENTORYID = ?";
    private static final String UPDATE_TOOL_SQL = "UPDATE TOOL SET TOOLCATEGORY = ? WHERE INVENTORYID = ?";
    private static final String UPDATE_INVENTORY_MANAGE_SQL = "UPDATE INVENTORYMANAGE SET ACCOUNTID = ?, INVMANAGEDATECHANGED = ? WHERE INVENTORYID = ?";
    private static final String SELECT_PLANT_BY_ID = "SELECT * FROM PLANT WHERE PLANTID = ?";
    private static final String SELECT_TOOL_BY_ID = "SELECT * FROM TOOL WHERE TOOLID = ?";
    private static final String SELECT_ALL_INVENTORY = "SELECT * FROM INVENTORY JOIN INVENTORYMANAGE USING (INVENTORYID)";
//    private static final String SELECT_INVENTORY_BY_ID = "SELECT * FROM INVENTORY LEFT JOIN INVENTORYMANAGE ON INVENTORY.INVENTORYID = INVENTORYMANAGE.INVENTORYID LEFT JOIN PLANT ON INVENTORY.INVENTORYID = PLANT.INVENTORYID LEFT JOIN TOOL ON INVENTORY.INVENTORYID = TOOL.INVENTORYID WHERE INVENTORY.INVENTORYID = ?";
    private static final String SELECT_INVENTORY_BY_ID = 
    	    "SELECT I.*, IM.ACCOUNTID, IM.INVMANAGEDATECHANGED, A.ACCOUNTUSERNAME, P.PLANTMANUAL, T.TOOLCATEGORY " +
    	    "FROM INVENTORY I " +
    	    "LEFT JOIN INVENTORYMANAGE IM ON I.INVENTORYID = IM.INVENTORYID " +
    	    "LEFT JOIN ACCOUNTS A ON IM.ACCOUNTID = A.ACCOUNTID " +
    	    "LEFT JOIN PLANT P ON I.INVENTORYID = P.INVENTORYID " +
    	    "LEFT JOIN TOOL T ON I.INVENTORYID = T.INVENTORYID " +
    	    "WHERE I.INVENTORYID = ?";
    private static final String SELECT_ALL_INVENTORY_BY_ROLE = "SELECT * FROM INVENTORY WHERE INVENTORYROLE = ?"; 

    public void insertInventory(Inventory inventory) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        accounts accounts = new accounts();
        int inventoryID = 0;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);  // Start transaction

            // Insert into INVENTORY table
//            preparedStatement = connection.prepareStatement(INSERT_INVENTORY_SQL, new String[]{"INVENTORYID"});
            preparedStatement = connection.prepareStatement(INSERT_INVENTORY_SQL);
            preparedStatement.setString(1, inventory.getInventoryName());
            preparedStatement.setDouble(2, inventory.getInventoryPricePerItem());
            preparedStatement.setInt(3, inventory.getInventoryQuantityExisting());
            preparedStatement.setString(4, inventory.getInventoryDesc());
            preparedStatement.setString(5, inventory.getInventoryStatus());
            preparedStatement.setBytes(6, inventory.getInventoryImage());
            preparedStatement.setString(7, inventory.getInventoryRole());
            preparedStatement.setInt(8, inventory.getInventoryQuantityIn());
            preparedStatement.executeUpdate();

            ps = connection.prepareStatement("SELECT INVENTORYID FROM INVENTORY WHERE INVENTORYNAME = ?"); 
            ps.setString(1, inventory.getInventoryName());
	            
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
            	inventoryID = resultSet.getInt("INVENTORYID");
            	
            	 // Insert into subclass table based on inventoryRole
                if (inventory instanceof Plant) {
                    Plant plant = (Plant) inventory;
                    preparedStatement = connection.prepareStatement(INSERT_PLANT_SQL);
                    preparedStatement.setString(1, plant.getPlantManual());
                    preparedStatement.setInt(2, inventoryID);
                } else if (inventory instanceof Tool) {
                    Tool tool = (Tool) inventory;
                    preparedStatement = connection.prepareStatement(INSERT_TOOL_SQL); 
                    preparedStatement.setString(1, tool.getToolCategory());
                    preparedStatement.setInt(2, inventoryID);
                }
                preparedStatement.executeUpdate();

                // Insert into INVENTORYMANAGE table
                preparedStatement = connection.prepareStatement(INSERT_INVENTORY_MANAGE_SQL);
                preparedStatement.setInt(1, inventory.getAccountID());
                preparedStatement.setInt(2, inventoryID);
                preparedStatement.setDate(3, new java.sql.Date(inventory.getInvmanageDateChanged().getTime())); 
                
                preparedStatement.executeUpdate();

            connection.commit();  // Commit transaction
            }
               
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();  // Rollback transaction on error
            }
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void updateInventory(Inventory inventory) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);  // Start transaction

            // Update INVENTORY table (excluding image)
            String updateInventorySQL = "UPDATE INVENTORY SET INVENTORYNAME = ?, INVENTORYPRICEPERITEM = ?, INVENTORYQUANTITYEXISTING = ?, INVENTORYDESC = ?, INVENTORYSTATUS = ?, INVENTORYROLE = ?, INVENTORYQUANTITYIN = ? WHERE INVENTORYID = ?";
            preparedStatement = connection.prepareStatement(updateInventorySQL);
            preparedStatement.setString(1, inventory.getInventoryName());
            preparedStatement.setDouble(2, inventory.getInventoryPricePerItem());
            preparedStatement.setInt(3, inventory.getInventoryQuantityExisting());
            preparedStatement.setString(4, inventory.getInventoryDesc());
            preparedStatement.setString(5, inventory.getInventoryStatus());
            preparedStatement.setString(6, inventory.getInventoryRole());
            preparedStatement.setInt(7, inventory.getInventoryQuantityIn());
            preparedStatement.setInt(8, inventory.getInventoryID());
            preparedStatement.executeUpdate();

            // Update the image if a new one is provided
            if (inventory.getInventoryImage() != null) {
                String updateImageSQL = "UPDATE INVENTORY SET INVENTORYIMAGE = ? WHERE INVENTORYID = ?";
                preparedStatement = connection.prepareStatement(updateImageSQL);
                preparedStatement.setBytes(1, inventory.getInventoryImage());
                preparedStatement.setInt(2, inventory.getInventoryID());
                preparedStatement.executeUpdate();
            }

            // Update subclass table based on inventoryRole
            if (inventory instanceof Plant) {
                Plant plant = (Plant) inventory;
                preparedStatement = connection.prepareStatement(UPDATE_PLANT_SQL);
                preparedStatement.setString(1, plant.getPlantManual());
                preparedStatement.setInt(2, plant.getInventoryID());
                preparedStatement.executeUpdate();
            } else if (inventory instanceof Tool) {
                Tool tool = (Tool) inventory;
                preparedStatement = connection.prepareStatement(UPDATE_TOOL_SQL);
                preparedStatement.setString(1, tool.getToolCategory());
                preparedStatement.setInt(2, tool.getInventoryID());
                preparedStatement.executeUpdate();
            }

            // Update INVENTORYMANAGE table
            preparedStatement = connection.prepareStatement(UPDATE_INVENTORY_MANAGE_SQL);
            preparedStatement.setInt(1, inventory.getAccountID());
            preparedStatement.setDate(2, new java.sql.Date(inventory.getInvmanageDateChanged().getTime()));
            preparedStatement.setInt(3, inventory.getInventoryID());
            preparedStatement.executeUpdate();

            connection.commit();  // Commit transaction
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();  // Rollback transaction on error
            }
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public Inventory selectInventory(int inventoryID) throws SQLException {
        Inventory inventory = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_INVENTORY_BY_ID);
            preparedStatement.setInt(1, inventoryID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String role = resultSet.getString("INVENTORYROLE");
                if ("plant".equals(role)) {
                    inventory = new Plant();
                    ((Plant) inventory).setPlantManual(resultSet.getString("PLANTMANUAL"));
                } else if ("tool".equals(role)) {
                    inventory = new Tool();
                    ((Tool) inventory).setToolCategory(resultSet.getString("TOOLCATEGORY"));
                } else {
                    inventory = new Inventory();
                }

                inventory.setInventoryID(resultSet.getInt("INVENTORYID"));
                inventory.setInventoryName(resultSet.getString("INVENTORYNAME"));
                inventory.setInventoryPricePerItem(resultSet.getDouble("INVENTORYPRICEPERITEM"));
                inventory.setInventoryQuantityExisting(resultSet.getInt("INVENTORYQUANTITYEXISTING"));
                inventory.setInventoryDesc(resultSet.getString("INVENTORYDESC"));
                inventory.setInventoryStatus(resultSet.getString("INVENTORYSTATUS"));
                inventory.setInventoryImage(resultSet.getBytes("INVENTORYIMAGE"));
                inventory.setInventoryRole(resultSet.getString("INVENTORYROLE"));
                inventory.setInventoryQuantityIn(resultSet.getInt("INVENTORYQUANTITYIN"));
                inventory.setAccountID(resultSet.getInt("ACCOUNTID"));
                inventory.setInvmanageDateChanged(resultSet.getDate("INVMANAGEDATECHANGED"));
                inventory.setAccountUsername(resultSet.getString("ACCOUNTUSERNAME"));
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return inventory;
    }



    private String getPlantManual(int plantID) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String plantManual = null;

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_PLANT_BY_ID);
            preparedStatement.setInt(1, plantID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                plantManual = resultSet.getString("PLANTMANUAL");
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return plantManual;
    }

    private String getToolCategory(int toolID) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String toolCategory = null;

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_TOOL_BY_ID);
            preparedStatement.setInt(1, toolID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                toolCategory = resultSet.getString("TOOLCATEGORY");
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return toolCategory;
    }

    public List<Inventory> selectAllInventory() throws SQLException {
        List<Inventory> inventories = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_INVENTORY);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String role = resultSet.getString("INVENTORYROLE");
                Inventory inventory;
                if ("plant".equals(role)) {
                    inventory = new Plant();
                    ((Plant) inventory).setPlantManual(getPlantManual(resultSet.getInt("INVENTORYID")));
                } else if ("tool".equals(role)) {
                    inventory = new Tool();
                    ((Tool) inventory).setToolCategory(getToolCategory(resultSet.getInt("INVENTORYID")));
                } else {
                    inventory = new Inventory();
                }

                inventory.setInventoryID(resultSet.getInt("INVENTORYID"));
                inventory.setInventoryName(resultSet.getString("INVENTORYNAME"));
                inventory.setInventoryPricePerItem(resultSet.getDouble("INVENTORYPRICEPERITEM"));
                inventory.setInventoryQuantityExisting(resultSet.getInt("INVENTORYQUANTITYEXISTING"));
                inventory.setInventoryDesc(resultSet.getString("INVENTORYDESC"));
                inventory.setInventoryStatus(resultSet.getString("INVENTORYSTATUS"));
                inventory.setInventoryImage(resultSet.getBytes("INVENTORYIMAGE"));
                inventory.setInventoryRole(resultSet.getString("INVENTORYROLE"));
                inventory.setInventoryQuantityIn(resultSet.getInt("INVENTORYQUANTITYIN"));
                inventory.setAccountID(resultSet.getInt("ACCOUNTID"));
                inventory.setInvmanageDateChanged(resultSet.getDate("INVMANAGEDATECHANGED"));
                
                inventories.add(inventory);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return inventories;
    }
    
    public List<Inventory> selectAllInventoryByPlant() throws SQLException {
        List<Inventory> inventories = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_INVENTORY_BY_ROLE);
            preparedStatement.setString(1, "plant");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String role = resultSet.getString("INVENTORYROLE");
                Inventory inventory;
                if ("plant".equals(role)) {
                    inventory = new Plant();
                    ((Plant) inventory).setPlantManual(getPlantManual(resultSet.getInt("INVENTORYID")));
                } else if ("tool".equals(role)) {
                    inventory = new Tool();
                    ((Tool) inventory).setToolCategory(getToolCategory(resultSet.getInt("INVENTORYID")));
                } else {
                    inventory = new Inventory();
                }

                inventory.setInventoryID(resultSet.getInt("INVENTORYID"));
                inventory.setInventoryName(resultSet.getString("INVENTORYNAME"));
                inventory.setInventoryPricePerItem(resultSet.getDouble("INVENTORYPRICEPERITEM"));
                inventory.setInventoryQuantityExisting(resultSet.getInt("INVENTORYQUANTITYEXISTING"));
                inventory.setInventoryDesc(resultSet.getString("INVENTORYDESC"));
                inventory.setInventoryStatus(resultSet.getString("INVENTORYSTATUS"));
                inventory.setInventoryImage(resultSet.getBytes("INVENTORYIMAGE"));
                inventory.setInventoryRole(resultSet.getString("INVENTORYROLE"));
                inventory.setInventoryQuantityIn(resultSet.getInt("INVENTORYQUANTITYIN"));
                
                inventories.add(inventory);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return inventories;
    }
    
    public List<Inventory> selectAllInventoryByTool() throws SQLException {
        List<Inventory> inventories = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_INVENTORY_BY_ROLE);
            preparedStatement.setString(1, "tool");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String role = resultSet.getString("INVENTORYROLE");
                Inventory inventory;
                if ("plant".equals(role)) {
                    inventory = new Plant();
                    ((Plant) inventory).setPlantManual(getPlantManual(resultSet.getInt("INVENTORYID")));
                } else if ("tool".equals(role)) {
                    inventory = new Tool();
                    ((Tool) inventory).setToolCategory(getToolCategory(resultSet.getInt("INVENTORYID")));
                } else {
                    inventory = new Inventory();
                }

                inventory.setInventoryID(resultSet.getInt("INVENTORYID"));
                inventory.setInventoryName(resultSet.getString("INVENTORYNAME"));
                inventory.setInventoryPricePerItem(resultSet.getDouble("INVENTORYPRICEPERITEM"));
                inventory.setInventoryQuantityExisting(resultSet.getInt("INVENTORYQUANTITYEXISTING"));
                inventory.setInventoryDesc(resultSet.getString("INVENTORYDESC"));
                inventory.setInventoryStatus(resultSet.getString("INVENTORYSTATUS"));
                inventory.setInventoryImage(resultSet.getBytes("INVENTORYIMAGE"));
                inventory.setInventoryRole(resultSet.getString("INVENTORYROLE"));
                inventory.setInventoryQuantityIn(resultSet.getInt("INVENTORYQUANTITYIN"));
                
                inventories.add(inventory);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return inventories;
    }

	public static List<orders> getInventoryItemsByOrderId(int orderId, orders order) throws SQLException{
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
		List<orders> orderarr = new ArrayList<>();
		double sum=0.0;
		int paymentid=0;
		String paymentstatus = null;
		String sql = "SELECT * FROM orders " +
                "JOIN accounts USING (accountid) " +
                "JOIN inventory USING (inventoryid) " +
                "JOIN payment USING (orderid) " +
                "WHERE orderid = ?";
   
		try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderId);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
            	orders orderrs = new orders();
            	orderrs.setInventoryId(rs.getInt("inventoryid"));
            	orderrs.setInventoryName(rs.getString("inventoryname"));
            	orderrs.setOrderQuantity(rs.getInt("orderquantity"));
            	orderrs.setOrderTotalPrice(rs.getInt("ordertotalprice"));
            	paymentid = rs.getInt("paymentid");
            	paymentstatus = rs.getString("paymentstatus");
            	
            	byte[] blobData  = rs.getBytes("paymentproof");
	            byte[] paymentproof = null;
	            if (blobData  != null) {
	            	paymentproof = blobData;
	            }
	            
	            orderrs.setPaymentProof(paymentproof);
            	
                double orderTotalPrice = rs.getDouble("ordertotalprice");
                sum += orderTotalPrice;
                
                orderarr.add(orderrs);
            }
            order.setPaymentStatus(paymentstatus);
            order.setPaymentID(paymentid);
            order.setSumOrderTotalPrice(sum);
            System.out.println(order.getSumOrderTotalPrice());
           
            
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
 
	   return orderarr;

	}
}
