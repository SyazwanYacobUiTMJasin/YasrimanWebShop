package com.masbro.yasriman.api.repository;

import com.masbro.yasriman.api.model.AccountAPI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountAPI, Integer> {
    // No need to define any methods here as JpaRepository provides basic CRUD operations
}