package com.masbro.yasriman.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.masbro.yasriman.connection.SpringConnectionManager;
import com.masbro.yasriman.model.accounts;

/**
 * accountDAO.java This DAO class provides CRUD database operations for the
 * table accounts in the database.
 *
 * @author
 * MUHAMMAD SYAZWAN BIN YACOB
 * 2022601834
 * CDCS2664B
 * 
 */
@Repository
public class AccountDAO {

    @Autowired
    private SpringConnectionManager ConnectionManager;

    @Autowired
    public AccountDAO(SpringConnectionManager connectionManager) {
        this.ConnectionManager = connectionManager;
    }

    private static final String CHECK_DISTINCT_EMAIL_SQL = "SELECT accountemail FROM accounts WHERE accountemail = ?";
    private static final String INSERT_ACCOUNT_SQL = "INSERT INTO accounts(accountfirstname, accountlastname, accountusername, accountemail, accountpassword, accountphonenum,accountrole,accountstatus) VALUES (?, ?, ?, ?, ?, ?,?,?)";
    private static final String CHECK_ACCOUNT_AUTH = "SELECT accountid, accountrole, accountusername, accountstatus FROM accounts WHERE accountemail=? AND accountpassword=?";
    private static final String FETCH_ACCOUNT_BY_ID = "SELECT accountid, accountrole, accountusername FROM accounts WHERE accountid=?";
    private static final String LIST_ALL_ACCOUNT = "SELECT * FROM accounts ORDER BY accountid";
    private static final String VIEW_ONE_ACCOUNT = "SELECT * FROM accounts WHERE accountid=?";
    private static final String UPDATE_CUSTOMER_ACCOUNT = "UPDATE accounts SET accountrole=?, accountusername=?, accountfirstname=?, accountlastname=?, accountpassword=?, accountemail=?, accountphonenum=?, accountstreet=?, accountstate=?, accountcity=?, accountpostalcode=?, supervisorid=?, accountstatus=? WHERE accountid=?";
    private static final String COMMIT_CHANGES = "COMMIT";
    public AccountDAO() {}

    public boolean isEmailExists(String email) throws SQLException {
        boolean emailExists = false;
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(CHECK_DISTINCT_EMAIL_SQL)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emailExists = true;
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return emailExists;
    }

    public void insertAccount(accounts newAccount) throws SQLException {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_ACCOUNT_SQL)) {
            ps.setString(1, newAccount.getFirstname());
            ps.setString(2, newAccount.getLastname());
            ps.setString(3, newAccount.getUsername());
            ps.setString(4, newAccount.getEmail());
            ps.setString(5, newAccount.getPassword());
            ps.setString(6, newAccount.getPhonenum());
            ps.setString(7, newAccount.getRole());
            ps.setString(8, newAccount.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    
    public accounts authenticateAccount(String email, String password) throws SQLException {
        accounts account = null;
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(CHECK_ACCOUNT_AUTH)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    account = new accounts();
                    account.setId(rs.getInt("accountid"));
                    account.setRole(rs.getString("accountrole"));
                    account.setUsername(rs.getString("accountusername"));
                    account.setStatus(rs.getString("accountstatus"));
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return account;
    }

    public accounts getAccountById(int accountId) throws SQLException {
        accounts account = null;
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(FETCH_ACCOUNT_BY_ID)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    account = new accounts();
                    account.setId(rs.getInt("accountid"));
                    account.setRole(rs.getString("accountrole"));
                    account.setUsername(rs.getString("accountusername"));
                    account.setFirstname(rs.getString("accountfirstname"));
                    account.setLastname(rs.getString("accountlastname"));
                    account.setPhonenum(rs.getString("accountphonenum"));
                    account.setStreet(rs.getString("accountstreet"));
                    account.setState(rs.getString("accountstate"));
                    account.setCity(rs.getString("accountcity"));
                    account.setPostalcode(rs.getInt("accountpostalcode"));
                    account.setPicture(rs.getBytes("accountpicture"));
                }
            }
             
        } catch (SQLException e) {
            printSQLException(e);
        }
        return account;
    }


    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
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

	public List<accounts> selectAllUsers() {
		// TODO Auto-generated method stub
		List<accounts> accounts = new ArrayList<accounts>();
		
		try {
			//call getConnection() method
			Connection con = ConnectionManager.getConnection();

			//3. create statement 
			PreparedStatement stmt = con.prepareStatement(LIST_ALL_ACCOUNT); 

			//4. execute query
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {		//process result
				accounts account = new accounts();
				account.setId(rs.getInt("accountid"));
				account.setRole(rs.getString("accountrole"));
				account.setUsername(rs.getString("accountusername"));
				account.setPassword(rs.getString("accountpassword"));
				account.setEmail(rs.getString("accountemail"));
				account.setFirstname(rs.getString("accountfirstname"));
				account.setLastname(rs.getString("accountlastname"));
				account.setPhonenum(rs.getString("accountphonenum"));
				account.setStreet(rs.getString("accountstreet"));
				account.setState(rs.getString("accountstate"));
				account.setCity(rs.getString("accountcity"));
				account.setPostalcode(rs.getInt("accountpostalcode"));
				account.setPicture(rs.getBytes("accountpicture"));
				account.setStatus(rs.getString("accountstatus"));
				accounts.add(account);
			}
			
			
			//5. close connection
			con.close();

		}catch(Exception e) {
			e.printStackTrace();
		}

		return accounts;
	}

	public accounts viewCustomerAccount(int accountid) {
		// TODO Auto-generated method stub
		accounts accounts = null;
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
                int  accountpostalcode = rs.getInt("accountpostalcode");
                byte[] accountpicture = rs.getBytes("accountpicture");
                
                String accountstatus = rs.getString("accountstatus");
                int supervisorId = rs.getInt("supervisorid");
                String supervisor = supervisorId > 0 ? getSupervisorNameById(supervisorId) : "N/A";
				accounts = new accounts(accountidsql, accountrole, accountusername, accountpassword, accountemail, accountfirstname, accountlastname, accountphonenum, accountstreet, accountstate, accountcity, accountpostalcode, accountpicture, supervisor, accountstatus);
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return accounts;
	}

	public accounts editCustomerAccount(int accountid, String firstName, String lastName, String username, String phone, String street, String state, String city, int postalCode, byte[] picture) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        accounts account = null;

        try {
            con = ConnectionManager.getConnection();
            String sql;

            if (picture != null) {
                sql = "UPDATE accounts SET accountfirstname=?, accountlastname=?, accountusername=?, accountphonenum=?, accountstreet=?, accountstate=?, accountcity=?, accountpostalcode=?, accountpicture=? WHERE accountid=?";
                ps = con.prepareStatement(sql);
                ps.setBytes(9, picture);
                ps.setInt(10, accountid);
            } else {
                sql = "UPDATE accounts SET accountfirstname=?, accountlastname=?, accountusername=?, accountphonenum=?, accountstreet=?, accountstate=?, accountcity=?, accountpostalcode=? WHERE accountid=?";
                ps = con.prepareStatement(sql);
                ps.setInt(9, accountid);
            }

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, username);
            ps.setString(4, phone);
            ps.setString(5, street);
            ps.setString(6, state);
            ps.setString(7, city);
            ps.setInt(8, postalCode);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                account = new accounts();
                account.setId(accountid);
                account.setFirstname(firstName);
                account.setLastname(lastName);
                account.setUsername(username);
                account.setPhonenum(phone);
                account.setStreet(street);
                account.setState(state);
                account.setCity(city);
                account.setPostalcode(postalCode);
                account.setPicture(picture);
            }

        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return account;

	} 
	
	public accounts updateCustomerAccount(int accountid, String role, String username, String firstName, String lastName, String email,
	        String password, String phone, String street, String state, String city, int postalCode, int supervisorId, String status) throws SQLException {
		// TODO Auto-generated method stub
		accounts accounts = null;
		Connection con = null;
        PreparedStatement ps = null;
		try {
			con = ConnectionManager.getConnection();
            ps = con.prepareStatement(UPDATE_CUSTOMER_ACCOUNT);

        	ps.setObject(1, role, Types.OTHER); // Use setObject to set ENUM value
	        ps.setString(2, username);   
	        ps.setString(3, firstName);  
	        ps.setString(4, lastName);    
	        ps.setString(5, password);    
	        ps.setString(6, email);       
	        ps.setString(7, phone);    
	        ps.setString(8, street);     
	        ps.setString(9, state);       
	        ps.setString(10, city);      
	        ps.setInt(11, postalCode); 
            ps.setInt(12, supervisorId); // Set the supervisor ID     
	        ps.setString(13, status);           
	        ps.setInt(14, accountid);           
                
	        int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                accounts = new accounts();
                accounts.setId(accountid);
                accounts.setRole(role);
                accounts.setFirstname(firstName);
                accounts.setLastname(lastName);
    	        accounts.setPassword(password);
    	        accounts.setEmail(email);
                accounts.setUsername(username);
                accounts.setPhonenum(phone);
                accounts.setStreet(street);
                accounts.setState(state);
                accounts.setCity(city);
                accounts.setPostalcode(postalCode); 
                accounts.setStatus(status); 
            }

        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return accounts;
	}

    public String getSupervisorNameById(int supervisorId) {
	    String supervisorName = "N/A";
	    try {
	        Connection con = ConnectionManager.getConnection();
	        PreparedStatement ps = con.prepareStatement("SELECT accountfirstname, accountlastname FROM accounts WHERE accountid = ?");
	        ps.setInt(1, supervisorId);

	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            supervisorName = rs.getString("accountfirstname") + " " + rs.getString("accountlastname");
	        }

	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return supervisorName;
	}

	public void commit(Connection con)
	{
		 try {
			try (PreparedStatement commitStatement = con.prepareStatement(COMMIT_CHANGES)) {
			    commitStatement.execute();
			}
		 } catch (SQLException ex) {
             ex.printStackTrace();
         }
	}
	
    public boolean deleteAccount(int accountid) throws SQLException {
        String DELETE_ORDERS_SQL = "DELETE FROM ORDERS WHERE ACCOUNTID = ?";
        String DELETE_PAYMENTS_SQL = "DELETE FROM PAYMENT WHERE ACCOUNTID = ?";
        String DELETE_INVENTORYMANAGE_SQL = "DELETE FROM INVENTORYMANAGE WHERE ACCOUNTID = ?";
        String DELETE_ACCOUNT_SQL = "DELETE FROM ACCOUNTS WHERE ACCOUNTID = ?";

        Connection connection = null;
        PreparedStatement psDeleteOrders = null;
        PreparedStatement psDeletePayments = null;
        PreparedStatement psDeleteInventoryManage = null;
        PreparedStatement psDeleteAccount = null;
        PreparedStatement psUpdatePicture = null;

        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false); // Begin transaction

            // Delete related records in orders
            psDeleteOrders = connection.prepareStatement(DELETE_ORDERS_SQL);
            psDeleteOrders.setInt(1, accountid);
            psDeleteOrders.executeUpdate();

            // Delete related records in payments
            psDeletePayments = connection.prepareStatement(DELETE_PAYMENTS_SQL);
            psDeletePayments.setInt(1, accountid);
            psDeletePayments.executeUpdate();

            // Delete related records in inventorymanage
            psDeleteInventoryManage = connection.prepareStatement(DELETE_INVENTORYMANAGE_SQL);
            psDeleteInventoryManage.setInt(1, accountid);
            psDeleteInventoryManage.executeUpdate();

            // Delete the account
            psDeleteAccount = connection.prepareStatement(DELETE_ACCOUNT_SQL);
            psDeleteAccount.setInt(1, accountid);
            int rowDeleted = psDeleteAccount.executeUpdate();

            

            connection.commit(); // Commit transaction
            return rowDeleted > 0;

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback transaction on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (psDeleteOrders != null) psDeleteOrders.close();
            if (psDeletePayments != null) psDeletePayments.close();
            if (psDeleteInventoryManage != null) psDeleteInventoryManage.close();
            if (psDeleteAccount != null) psDeleteAccount.close();
            if (connection != null) connection.close();
        }
    }

    public List<String> getAllRoles() {
        List<String> roles = new ArrayList<>();
        try {
            Connection con = ConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT DISTINCT accountrole FROM accounts");
            ResultSet rs = ps.executeQuery();
    
            while (rs.next()) {
                roles.add(rs.getString("accountrole"));
            }
    
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }

    // Update account status
public void updateAccountStatus(int accountId, String newStatus) throws SQLException {
    String updateStatusSQL = "UPDATE accounts SET accountstatus = ? WHERE accountid = ?";
    try (Connection con = ConnectionManager.getConnection();
         PreparedStatement ps = con.prepareStatement(updateStatusSQL)) {
        ps.setString(1, newStatus);
        ps.setInt(2, accountId);
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}



}
