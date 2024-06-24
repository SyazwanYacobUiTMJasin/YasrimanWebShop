package com.masbro.yasriman.controller;

import com.masbro.yasriman.dao.PaymentDAO;
import com.masbro.yasriman.model.accounts;
import com.masbro.yasriman.model.orders;

import jakarta.servlet.http.HttpSessionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentDAO paymentDAO;

    @Autowired
    public PaymentController(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    @GetMapping
    public String handleGetRequest(@RequestParam("action") String action,
                                   @RequestParam("uid") int accountId,
                                   Model model) {
        if ("viewform".equals(action)) {
            return viewForm(accountId, model);
        } else {
            return "error"; // Handle unknown action
        }
    }

    @PostMapping
    public String handlePostRequest(@RequestParam("action") String action,
                                    @RequestParam("uid") int accountId,
                                    @RequestParam("paymentproof") MultipartFile paymentProof,
                                    HttpSession session,
                                    Model model) {
        if ("submitform".equals(action)) {
            return submitForm(accountId, paymentProof, session, model);
        } else {
            return "error"; // Handle unknown action
        }
    }

    private String viewForm(int accountId, Model model) {
         
            accounts paymentAccounts = paymentDAO.viewCustomerAddress(accountId);
            byte[] pictureBytes = paymentAccounts.getPicture();
            String base64EncodedImage = (pictureBytes != null && pictureBytes.length > 0) ?
                    Base64.getEncoder().encodeToString(pictureBytes) : "a"; // Default placeholder value
            model.addAttribute("base64EncodedImage", base64EncodedImage);
            model.addAttribute("accounts", paymentAccounts);
            return "payment";
        
    }

    private String submitForm(int accountId,
                              MultipartFile paymentProof,
                              HttpSession session,
                              Model model) {
        byte[] paymentproof = null;
        try {
            if (!paymentProof.isEmpty()) {
                paymentproof = paymentProof.getBytes();
            }

            List<orders> orders = (List<orders>) session.getAttribute("orders");
            if (orders == null || orders.isEmpty()) {
                model.addAttribute("errorMessage", "No orders found to process.");
                return "error";
            }

            for (orders order : orders) {
                com.masbro.yasriman.model.Payment payment = new com.masbro.yasriman.model.Payment();
                payment.setOrderid(order.getOrderId());
                payment.setPaymentproof(paymentproof);

                // Convert LocalDate to java.sql.Date
                Date sqlOrderDate = Date.valueOf(order.getOrderDate());

                // Call insertOrderAndPayment with converted date
                paymentDAO.insertOrderAndPayment(order.getAccountId(), order.getInventoryId(), sqlOrderDate,
                        order.getOrderStatus(), order.getOrderTotalPrice(), order.getOrderQuantity(), payment);
            }

            // Clear the orders from the session after successful commit
            session.removeAttribute("orders");
            return "redirect:/";
        } catch (SQLException | IOException ex) {
            // Handle exception
            ex.printStackTrace();
            model.addAttribute("errorMessage", "Error processing payment.");
            return "error";
        }
    }
}
