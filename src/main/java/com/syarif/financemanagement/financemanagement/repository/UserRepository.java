package com.syarif.financemanagement.financemanagement.repository;

import com.syarif.financemanagement.financemanagement.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {
    Optional<Users> findByUsername(String username);
    Users findByCreditCard(String creditCard);
}