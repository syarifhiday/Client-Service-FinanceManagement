package com.syarif.financemanagement.financemanagement.controller;

import com.syarif.financemanagement.financemanagement.dto.request.TransactionRequestDto;
import com.syarif.financemanagement.financemanagement.dto.response.BaseResponseDto;
import com.syarif.financemanagement.financemanagement.dto.response.TransactionResponseDto;
import com.syarif.financemanagement.financemanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<BaseResponseDto> saveTransaction(@RequestBody TransactionRequestDto transactionRequestDto) {
        BaseResponseDto response = transactionService.handleTransaction(transactionRequestDto);

        if (response.getStatus() == HttpStatus.CREATED) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}