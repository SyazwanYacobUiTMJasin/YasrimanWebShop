package com.masbro.yasriman.controller; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.masbro.yasriman.dao.DashboardDAO;
import com.masbro.yasriman.dao.InventoryDAO;
import com.masbro.yasriman.dao.OrderDAO;
import com.masbro.yasriman.dao.PaymentDAO;
import com.masbro.yasriman.model.orders;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderDAO orderDAO;
    private final PaymentDAO PaymentDAO;
    private final InventoryDAO InventoryDAO;

    @Autowired
    public OrderController(OrderDAO orderDAO, PaymentDAO PaymentDAO, InventoryDAO InventoryDAO) {
        this.orderDAO = orderDAO;
        this.PaymentDAO = PaymentDAO;
        this.InventoryDAO = InventoryDAO;
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
            List<orders> inventoryItems = InventoryDAO.getInventoryItemsByOrderId(orderId, order);
            request.setAttribute("inventoryItems", inventoryItems);

            byte[] pictureBytes = order.getPaymentProof();
            String base64EncodedImage = (pictureBytes != null && pictureBytes.length > 0) ?
                    Base64.getEncoder().encodeToString(pictureBytes) : "a";
            request.setAttribute("base64EncodedImage", base64EncodedImage);

            return "orderdetails";
        } else if ("update".equals(from)) {
            List<orders> inventoryItems = InventoryDAO.getInventoryItemsByOrderId(orderId, order);
            request.setAttribute("inventoryItems", inventoryItems);

            byte[] pictureBytes = order.getPaymentProof();
            String base64EncodedImage = (pictureBytes != null && pictureBytes.length > 0) ?
                    Base64.getEncoder().encodeToString(pictureBytes) : "a";
            request.setAttribute("base64EncodedImage", base64EncodedImage);

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
        String paymentStatus = request.getParameter("paymentStatus");
        try {
            orderDAO.updateOrderStatus(orderId, orderStatus, paymentStatus);
            orders order = orderDAO.selectOrderById(orderId);
            model.addAttribute("order", order);

            List<orders> inventoryItems = InventoryDAO.getInventoryItemsByOrderId(orderId, order);
            request.setAttribute("inventoryItems", inventoryItems);

            byte[] pictureBytes = order.getPaymentProof();
		    String base64EncodedImage = null;

            if (pictureBytes != null && pictureBytes.length > 0) {
                base64EncodedImage = Base64.getEncoder().encodeToString(pictureBytes);
            } else {
                System.out.println("Picture byte array is null or empty");
                base64EncodedImage = "a"; // or some default placeholder value
            }
    
            request.setAttribute("base64EncodedImage", base64EncodedImage);
            System.out.println("Order Status: " + order.getOrderStatus());
            System.out.println("Payment Status: " + order.getPaymentStatus());
            System.out.println("updateOrderStatus()");
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
        String accountStreet = (String) session.getAttribute("accountstreet");
        String accountCity = (String) session.getAttribute("accountcity");
        String accountState = (String) session.getAttribute("accountstate");
        int accountPoscode = (int) session.getAttribute("accountpostalcode");
        System.out.println(accountId + " FROM ORDER CONTROLLER CHECKSIGNINSTATUS");
        
        if (accountId == null || accountId.isEmpty() ) {
            session.setAttribute("signinerror", "Please sign in first");
            return "redirect:/signin";
        } else {

            // Check if any address field is empty
            if (accountStreet == null || accountStreet.isEmpty() ||
                accountCity == null || accountCity.isEmpty() ||
                accountState == null || accountState.isEmpty() ||
                accountPoscode <=0) {
                session.setAttribute("errorMessage", "Please check you address make sure it is correct");
                return "redirect:/editcustomeraccount?uid="+accountId; // Redirect to an error page or handle appropriately
            }  else
            {
                if (session.getAttribute("orders") == null) {
                    String cartDataJson = request.getParameter("cartData");
                    System.out.println("Received cart data: " + cartDataJson);
                    
                    if (cartDataJson == null || cartDataJson.isEmpty()) {
                        session.setAttribute("errorMessage", "Cart data is missing or invalid");
                        return "redirect:/error";
                    }
                    
                    try {
                        // URL-decode the cart data
                        String decodedCartData = URLDecoder.decode(cartDataJson, StandardCharsets.UTF_8.toString());
                        System.out.println("Decoded cart data: " + decodedCartData);
                        
                        // Process the decoded cart data
                        processCartData(request, response, decodedCartData, accountId);
                        return "redirect:/payment?action=viewform&uid=" + accountId;
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        session.setAttribute("errorMessage", "Error processing cart data: " + ex.getMessage());
                        return "redirect:/error";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        session.setAttribute("errorMessage", "Unexpected error: " + ex.getMessage());
                        return "redirect:/error";
                    }
                }
                return "redirect:/payment?action=viewform&uid=" + accountId;
            }
        }
    }

    private void processCartData(HttpServletRequest request, HttpServletResponse response,
                             String cartDataJson, String accountId) throws SQLException, ServletException, IOException {
        int orderAccountId = Integer.parseInt(accountId);
        HttpSession session = request.getSession();

        try {
            // URL-decode the cart data
            String decodedCartData = URLDecoder.decode(cartDataJson, StandardCharsets.UTF_8.toString());
            System.out.println("Decoded cart data: " + decodedCartData);

            // Use Gson to parse the JSON
            Gson gson = new Gson();
            JsonArray cartArray = gson.fromJson(decodedCartData, JsonArray.class);

            List<orders> orders = (List<orders>) session.getAttribute("orders");
            if (orders == null) {
                orders = new ArrayList<>();
            }

            for (int i = 0; i < cartArray.size(); i++) {
                JsonObject cartItem = cartArray.get(i).getAsJsonObject();

                String title = cartItem.get("title").getAsString();
                double price = cartItem.get("price").getAsDouble();
                int inventoryID = Integer.parseInt(cartItem.get("inventoryID").getAsString());
                int quantity = cartItem.get("quantity").getAsInt();
                int accountID = orderAccountId;
                LocalDateTime orderDate = LocalDateTime.now();
                String orderStatus = "PROCESS";
                double orderTotalPrice = price * quantity;

                // Add order to the list
                orders.add(new orders(accountID, inventoryID, orderDate, orderStatus, orderTotalPrice, quantity));
            }

            // Update session attribute
            session.setAttribute("orders", orders);
            System.out.println("DEBUG: Orders in session after update: " + session.getAttribute("orders"));

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error processing cart data", e);
        }
    }

    @GetMapping(params = "action=history")
    public String listOrderHistory(HttpServletRequest request, Model model) {
        String uidParam = request.getParameter("uid");
        HttpSession session = request.getSession();

        if (uidParam != null && !uidParam.isEmpty()) {
            try {
                int accountId = Integer.parseInt(uidParam);
                List<orders> ordersList = orderDAO.selectAllOrdersGroupedByOrderId(accountId);
                model.addAttribute("orders", ordersList);
                return "orderhistory";
            } catch (NumberFormatException | SQLException e) {
                session.setAttribute("errorMessage", "Invalid account ID or database error");
                return "error";
            }
        } else {
            session.setAttribute("errorMessage", "Please create an account first.");
            return "error";
        }
    }

    @GetMapping(params = "action=summary")
    public String showOrderSummary(HttpServletRequest request, Model model) {
        int orderId = Integer.parseInt(request.getParameter("orderid"));

        try {
            orders order = orderDAO.getOrderDetailsById(orderId);

            if (order == null) {
                model.addAttribute("errorMessage", "Order not found.");
                return "error";
            }

            List<orders> orderItems = orderDAO.selectOrderItemsByOrderId(orderId);
            double totalPrice = orderItems.stream().mapToDouble(orders::getOrderTotalPrice).sum();

            model.addAttribute("order", order);
            model.addAttribute("orderItems", orderItems);
            model.addAttribute("totalPrice", totalPrice);
            
            return "ordersummary";
        } catch (SQLException ex) {
            // Handle exception
            return "error";
        }
    }

    @GetMapping(params = "action=delete")
    public String deleteOrder(HttpServletRequest request, Model model) {
        String orderIdParam = request.getParameter("orderid");
        String paymentStatus = request.getParameter("paymentStatus");

        if (orderIdParam == null || orderIdParam.isEmpty()) {
            model.addAttribute("errorMessage", "Invalid order ID");
            return "error";
        }

        try {
            int orderId = Integer.parseInt(orderIdParam);

            if ("NOTAPPROVED".equalsIgnoreCase(paymentStatus)) {
                boolean deleted = orderDAO.deleteOrder(orderId);
                if (deleted) {
                    return "redirect:/order?action=view";
                } else {
                    model.addAttribute("errorMessage", "Failed to delete order");
                    return "error";
                }
            } else {
                return "redirect:/order?action=view&error=cannotDelete";
            }
        } catch (NumberFormatException | SQLException e) {
            model.addAttribute("errorMessage", "Invalid order ID format or database error");
            return "error";
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

    @PostMapping("/clearOrderSession")
    @ResponseBody
    public ResponseEntity<String> clearOrderSession(HttpSession session) {
        session.removeAttribute("orders");
        return ResponseEntity.ok("Order session cleared");
    }
}
