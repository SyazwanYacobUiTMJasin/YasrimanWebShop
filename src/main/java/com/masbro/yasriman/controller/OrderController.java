package com.masbro.yasriman.controller; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.masbro.yasriman.dao.OrderDAO;
import com.masbro.yasriman.model.orders;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderDAO orderDAO;

    @Autowired
    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @GetMapping(params = "action=view")
    public String listOrders(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {
        try {
            List<orders> ordersList = orderDAO.selectAllOrders();
            model.addAttribute("orders", ordersList);
            // request.setAttribute("orders", ordersList);
            // request.getRequestDispatcher("order.jsp").forward(request, response);
            return "order";
        } catch (SQLException ex) {
            // Handle exception
            return "error";
        }
    }

    @GetMapping(params = "action=show")
    public String showOrderDetails(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderid"));
        String from = request.getParameter("from");
        try {
            orders order = orderDAO.selectOrderById(orderId);
            model.addAttribute("order", order);
            request.setAttribute("order", order);
            if ("view".equals(from)) {
                // request.getRequestDispatcher("orderdetails.jsp").forward(request, response);
                return "orderdetails";
            } else if ("update".equals(from)) {
                // request.getRequestDispatcher("updateorderdetails.jsp").forward(request, response);
                return "updateorderdetails";
            } else {
                return "error";
            }
        } catch (SQLException ex) {
            // Handle exception
            return "error";
        }
    }

    @PostMapping(params = "action=update")
    public String updateOrderStatus(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderid"));
        String orderStatus = request.getParameter("orderStatus");
        try {
            orderDAO.updateOrderStatus(orderId, orderStatus);
            orders order = orderDAO.selectOrderById(orderId);
            model.addAttribute("order", order);
            // request.setAttribute("order", order);
            // request.getRequestDispatcher("orderdetails.jsp").forward(request, response);
            return "orderdetails";
        } catch (SQLException ex) {
            // Handle exception
            return "error";
        }
    }

    @PostMapping(params = "action=checksigninstatus")
    public String checkSignInStatus(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws ServletException, IOException {
        String accountId = request.getParameter("uid");
        System.out.println(accountId + "FROM ORDER CONTROLLER CHECKSIGNINSTATUS");
        if ("".equals(accountId)) {
            return "redirect:/signin";
        } else {
            String cartDataJson = request.getParameter("cartData");
            try {
                processCartData(request, response, cartDataJson, accountId);
                // response.sendRedirect("./payment?action=viewform&uid=" + accountId);
                return "redirect:/payment?action=viewform&uid=" + accountId;
            } catch (SQLException ex) {
                // Handle exception
                return "error";
            }
        }
    }

    private void processCartData(HttpServletRequest request, HttpServletResponse response,
                             String cartDataJson, String accountId) throws SQLException, ServletException, IOException {
    int orderAccountId = Integer.parseInt(accountId);
    HttpSession session = request.getSession();

    try {
        JsonArray cartArray = JsonParser.parseString(cartDataJson).getAsJsonArray();

        List<orders> orders = (List<orders>) session.getAttribute("orders");
        if (orders == null) {
            orders = new ArrayList<>();
        }

        for (int i = 0; i < cartArray.size(); i++) {
            JsonObject cartItem = cartArray.get(i).getAsJsonObject();

            String title = cartItem.get("title").getAsString();
            double price = cartItem.get("price").getAsDouble();
            String imgSrc = cartItem.get("imgSrc").getAsString();
            int inventoryID = cartItem.get("inventoryID").getAsInt();
            int quantity = cartItem.get("quantity").getAsInt();
            int accountID = orderAccountId; // Using the accountId passed to this method
            LocalDate orderDate = LocalDate.now(); // Assuming current date as order date
            String orderStatus = "PROCESS"; // Initial order status
            double orderTotalPrice = price * quantity;

            // Add order to the list
            orders.add(new orders(accountID, inventoryID, orderDate, orderStatus, orderTotalPrice, quantity));
        }

        // Update session attribute
        session.setAttribute("orders", orders);
        System.out.println("DEBUG: Orders in session after update: " + session.getAttribute("orders"));

    } catch (Exception e) {
        e.printStackTrace();
        // Handle exception as needed, e.g., logging or throwing ServletException
        throw new ServletException("Error processing cart data", e);
    }
}


    @ExceptionHandler(SQLException.class)
    public String handleSQLException(SQLException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler({IOException.class, ServletException.class})
    public String handleIOException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
