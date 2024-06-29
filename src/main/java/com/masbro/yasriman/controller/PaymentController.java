package com.masbro.yasriman.controller;

import com.masbro.yasriman.dao.InventoryDAO;
import com.masbro.yasriman.dao.PaymentDAO;
import com.masbro.yasriman.model.accounts;
import com.masbro.yasriman.model.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
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
                                    HttpSession session, HttpServletRequest  request,
                                    Model model) {
                                        session = request.getSession();
        if ("submitform".equals(action)) {
            return submitForm(accountId, paymentProof, session, request, model);
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

    @PostMapping("/submitForm")
    private String submitForm(@RequestParam("accountId") int accountId,
                            @RequestParam("paymentProof") MultipartFile paymentProof,
                            HttpSession session, HttpServletRequest  request,
                            Model model) {
                                session = request.getSession();
        try {
            byte[] paymentProofBytes = null;
            if (paymentProof != null && !paymentProof.isEmpty()) {
                String contentType = paymentProof.getContentType();
                if (contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/jpg") || contentType.equals("image/png"))) {
                    paymentProofBytes = paymentProof.getBytes();
                } else {
                    model.addAttribute("errorMessage", "Invalid file type. Please upload a JPEG or PNG image.");
                    return "error";
                }
            }

            List<orders> orders = (List<orders>) session.getAttribute("orders");
            if (orders == null || orders.isEmpty()) {
                model.addAttribute("errorMessage", "No orders found to process.");
                return "error";
            }

            int count = 0;
            for (orders order : orders) {
                com.masbro.yasriman.model.Payment payment = new com.masbro.yasriman.model.Payment();
                payment.setOrderid(order.getOrderId());
	            payment.setAccountId(order.getAccountId());
	            payment.setInventoryId(order.getInventoryId());
	            payment.setOrderDate(order.getOrderDate());
                payment.setPaymentproof(paymentProofBytes);

                LocalDateTime orderDateTime = order.getOrderDate();
                
                PaymentDAO.insertOrderAndPayment(order.getAccountId(), order.getInventoryId(), orderDateTime,
                        order.getOrderStatus(), order.getOrderTotalPrice(), order.getOrderQuantity(), payment, count);
                count++;

                List<orders> inventoryItems = InventoryDAO.getInventoryItemsByOrderId(payment.getOrderid(), order);
	            request.setAttribute("inventoryItems", inventoryItems);
	            
	            session.setAttribute("ordertotalprice", order.getSumOrderTotalPrice());
	            session.setAttribute("orderid", payment.getOrderid());
            }

            // Clear the orders from the session after successful commit
            session.removeAttribute("orders");
            return "paymentsuccess";
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("errorMessage", "Error processing payment: " + ex.getMessage());
            return "error";
        }
    }
}
