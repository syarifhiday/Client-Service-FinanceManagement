package com.syarif.financemanagement.financemanagement.controller;

import com.syarif.financemanagement.financemanagement.dto.request.TransactionRequestDto;
import com.syarif.financemanagement.financemanagement.dto.response.BaseResponseDto;
import com.syarif.financemanagement.financemanagement.dto.response.TransactionResponseDto;
import com.syarif.financemanagement.financemanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<BaseResponseDto> saveTransaction(@RequestBody TransactionRequestDto transactionRequestDto) {
        BaseResponseDto response = transactionService.handleTransaction(transactionRequestDto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/cc/{creditCard}")
    public ResponseEntity<BaseResponseDto> getAllTransactions(@PathVariable String creditCard){
        String url = "http://localhost:8081/v1/transactions/cc/" + creditCard;

        ResponseEntity<BaseResponseDto> response = restTemplate.getForEntity(url, BaseResponseDto.class);
        return response;
    }

    @GetMapping("/export/{creditCard}")
    public ResponseEntity<BaseResponseDto> exportTransaction(@PathVariable String creditCard){

        String url = "http://localhost:8081/v1/transactions/export/" + creditCard;
        ResponseEntity<BaseResponseDto> response = restTemplate.getForEntity(url, BaseResponseDto.class);
        return response;
    }

}