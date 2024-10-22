package com.syarif.financemanagement.financemanagement.service;

import com.syarif.financemanagement.financemanagement.dto.request.BudgetRequestDto;
import com.syarif.financemanagement.financemanagement.dto.response.BaseResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface BudgetService {
    BaseResponseDto getOne(String id);
    BaseResponseDto getAll(String user_id);
    BaseResponseDto save(BudgetRequestDto budget);
    BaseResponseDto update(BudgetRequestDto budgetRequestDto, String id);
}
