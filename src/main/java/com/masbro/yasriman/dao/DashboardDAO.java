package com.masbro.yasriman.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.masbro.yasriman.model.orders;
import com.masbro.yasriman.connection.SpringConnectionManager;

@Repository
public class DashboardDAO {
    
	
    private final SpringConnectionManager ConnectionManager;
    
    @Autowired
    public DashboardDAO(SpringConnectionManager connectionManager) {
        this.ConnectionManager = connectionManager;
    }

	public List<orders> getOrderData() {
        List<orders> orderList = new ArrayList<>();

        String sql = "SELECT orderDate, SUM(orderTotalPrice) AS sumOrderTotalPrice FROM orders GROUP BY orderDate ORDER BY orderDate";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                orders order = new orders();
                order.setOrderDate(rs.getTimestamp("orderDate").toLocalDateTime());
                order.setSumOrderTotalPrice(rs.getDouble("sumOrderTotalPrice"));
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }
}
