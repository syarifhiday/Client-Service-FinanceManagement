package com.syarif.financemanagement.financemanagement.controller;

import com.syarif.financemanagement.financemanagement.dto.request.BudgetRequestDto;
import com.syarif.financemanagement.financemanagement.dto.response.BaseResponseDto;
import com.syarif.financemanagement.financemanagement.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    // API untuk mengambil data budget berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getBudget(@PathVariable String id) {
        BaseResponseDto responseDto = budgetService.getOne(id);
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }

    // API untuk mengambil seluruh data budget
    @PostMapping("/user/{id}")
    public ResponseEntity<BaseResponseDto> getAllBudgets(@PathVariable String user_id) {
        BaseResponseDto responseDto = budgetService.getAll(user_id);
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }

    // API untuk membuat budget baru
    @PostMapping
    public ResponseEntity<BaseResponseDto> createBudget(@RequestBody BudgetRequestDto budgetRequestDto) {
        BaseResponseDto responseDto = budgetService.save(budgetRequestDto);
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }

    // API untuk memperbarui budget berdasarkan ID
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDto> updateBudget(@RequestBody BudgetRequestDto budgetRequestDto, @PathVariable String id) {
        BaseResponseDto responseDto = budgetService.update(budgetRequestDto, id);
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }
}
