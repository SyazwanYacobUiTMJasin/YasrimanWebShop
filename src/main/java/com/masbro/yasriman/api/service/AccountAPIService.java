package com.masbro.yasriman.api.service;

import com.masbro.yasriman.api.model.AccountAPI;
import com.masbro.yasriman.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountAPIService {

    @Autowired
    private AccountRepository AccountRepository;

    public List<AccountAPI> getAllAccounts() {
        return AccountRepository.findAll();
    }

    public Optional<AccountAPI> getAccountById(int id) {
        return AccountRepository.findById(id);
    }

    public AccountAPI createAccount(AccountAPI account) {
        return AccountRepository.save(account);
    }

    public AccountAPI updateAccount(AccountAPI account) {
        return AccountRepository.save(account);
    }

    public void deleteAccount(int id) {
        AccountRepository.deleteById(id);
    }
}