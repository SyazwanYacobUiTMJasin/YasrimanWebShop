package com.masbro.yasriman.emailapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.masbro.yasriman.emailapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
