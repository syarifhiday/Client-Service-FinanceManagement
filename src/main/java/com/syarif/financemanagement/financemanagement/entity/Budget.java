package com.syarif.financemanagement.financemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syarif.financemanagement.financemanagement.dto.AuditingDto;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Budget extends AuditingDto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Users user;

}

