package com.masbro.yasriman.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Controller
public class BusinessController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/coopbusinesslist")
    public String getBusinesses(Model model) {
        String url = "https://coop-management-2024-f4cb6cd5cd97.herokuapp.com/api/businesses";
        BusinessResponse response = restTemplate.getForObject(url, BusinessResponse.class);

        if (response != null && response.getBusinesses() != null) {
            model.addAttribute("businesses", response.getBusinesses());
        }
        
        return "coopbusinesslist"; // Nama template Thymeleaf
    }

    // DTO untuk mapping response dari API
    public static class BusinessResponse {
        private List<Business> businesses;

        public List<Business> getBusinesses() {
            return businesses;
        }

        public void setBusinesses(List<Business> businesses) {
            this.businesses = businesses;
        }
    }

    public static class Business {
        private String businessID;
        private String ownerName;
        private String ownerEmail;
        private String businessType;

        // Getters dan Setters
        public String getBusinessID() {
            return businessID;
        }

        public void setBusinessID(String businessID) {
            this.businessID = businessID;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getOwnerEmail() {
            return ownerEmail;
        }

        public void setOwnerEmail(String ownerEmail) {
            this.ownerEmail = ownerEmail;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }
    }
}
