package com.syarif.financemanagement.financemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syarif.financemanagement.financemanagement.dto.AuditingDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Users extends AuditingDto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String username;
    private String password;
    private double balance;
    private String creditCard;
    private String role;
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Budget> budgets;
}