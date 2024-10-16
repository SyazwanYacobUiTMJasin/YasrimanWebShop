package com.masbro.yasriman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.masbro.yasriman.emailapi.service.EmailSenderService;

@SpringBootApplication
@Controller
public class YasrimanApplication {

	public static void main(String[] args) {
		SpringApplication.run(YasrimanApplication.class, args);
	}

	@GetMapping("/")
    public String index() {
        return "index";
    }

	@GetMapping("/products")
    public String product() {
        return "product";
    }

	@GetMapping("/productsplant")
    public String plant() {
        return "plant";
    }

	@GetMapping("/productstool")
    public String tool() {
        return "tool";
    }

	@GetMapping("/products/productdetails")
    public String productdetails() {
        return "productdetails";
    }

	@GetMapping("/cart")
    public String cart() {
        return "viewcart";
    }

	@GetMapping("/error")
    public String showError() {
        return "error";
    }
    
	@GetMapping("/paymentsuccess")
    public String showPaymentsuccess() {
        return "paymentsuccess";
    }


    // @Autowired
    // private EmailSenderService senderService;
    
    // @EventListener(ApplicationReadyEvent.class)
    // public void sendMail()
    // {
    //     senderService.sendEmail("yasrimanonlineshop@gmail.com", "Welcome to Our Plant Shop", "Thank you for signing up!");

    // }
}
//testcommit