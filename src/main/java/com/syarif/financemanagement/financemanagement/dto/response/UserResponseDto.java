package com.syarif.financemanagement.financemanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syarif.financemanagement.financemanagement.entity.Budget;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {
    private String id;
    private String name;
    private String username;
    private double balance;
    private String creditCard;
    private List<Budget> budgets;

    @JsonIgnore
    private String message;

}
