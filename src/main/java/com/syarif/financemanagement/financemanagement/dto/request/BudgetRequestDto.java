package com.syarif.financemanagement.financemanagement.dto.request;

import lombok.Data;

@Data
public class BudgetRequestDto {
    private String name;
    private double amount;
    private String user_id;
}
