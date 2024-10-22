package com.syarif.financemanagement.financemanagement.dto.response;

import lombok.Data;

@Data
public class BudgetResponseDto {
    private String id;
    private String name;
    private double amount;
    private String user_id; // Menyimpan ID pengguna yang terkait
}