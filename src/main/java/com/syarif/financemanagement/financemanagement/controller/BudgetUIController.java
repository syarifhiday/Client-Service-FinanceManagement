package com.syarif.financemanagement.financemanagement.controller;

import com.syarif.financemanagement.financemanagement.feignclient.BudgetClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BudgetUIController {

    @Autowired
    private BudgetClient budgetClient;

    @GetMapping("/user/budgets")
    public String showBudgetsPage() {
        return "budgets";
    }
}