package com.syarif.financemanagement.financemanagement.service;

import com.syarif.financemanagement.financemanagement.dto.request.TransactionRequestDto;
import com.syarif.financemanagement.financemanagement.dto.response.BaseResponseDto;

public interface TransactionService {
    BaseResponseDto handleTransaction(TransactionRequestDto transactionRequestDto);
}