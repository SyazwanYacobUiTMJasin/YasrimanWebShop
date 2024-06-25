package com.masbro.yasriman.api.controller;

import com.masbro.yasriman.api.model.AccountAPI;
import com.masbro.yasriman.api.service.AccountAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountControllerAPI {

    @Autowired
    private AccountAPIService accountAPIService;

    @GetMapping
    public ResponseEntity<List<AccountAPI>> getAllAccounts() {
        List<AccountAPI> accounts = accountAPIService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountAPI> getAccountById(@PathVariable int id) {
        Optional<AccountAPI> account = accountAPIService.getAccountById(id);
        return account.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountAPI account) {
        try {
            AccountAPI createdAccount = accountAPIService.createAccount(account);
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // or use a logger
            return new ResponseEntity<>("Error creating account: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountAPI> updateAccount(@PathVariable int id, @RequestBody AccountAPI account) {
        if (!accountAPIService.getAccountById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        account.setId(id);
        AccountAPI updatedAccount = accountAPIService.updateAccount(account);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int id) {
        if (!accountAPIService.getAccountById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountAPIService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}