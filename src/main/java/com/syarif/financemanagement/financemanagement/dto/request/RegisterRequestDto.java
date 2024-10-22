package com.syarif.financemanagement.financemanagement.dto.request;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String name;
    private String username;
    private String password;
    private double balance;
    private String creditCard;
    private String role;
}
