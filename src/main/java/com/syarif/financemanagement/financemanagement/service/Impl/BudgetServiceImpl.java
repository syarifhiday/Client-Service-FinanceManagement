package com.syarif.financemanagement.financemanagement.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syarif.financemanagement.financemanagement.dto.request.BudgetRequestDto;
import com.syarif.financemanagement.financemanagement.dto.response.BaseResponseDto;
import com.syarif.financemanagement.financemanagement.dto.response.BudgetResponseDto;
import com.syarif.financemanagement.financemanagement.entity.Budget;
import com.syarif.financemanagement.financemanagement.entity.Users;
import com.syarif.financemanagement.financemanagement.repository.BudgetRepository;
import com.syarif.financemanagement.financemanagement.repository.UserRepository;
import com.syarif.financemanagement.financemanagement.service.BudgetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public BaseResponseDto getOne(String id) {
        try {
            Optional<Budget> optionalBudget = budgetRepository.findById(id);
            if (optionalBudget.isPresent()) {
                Budget budget = optionalBudget.get();

                // Ubah Budget menjadi BudgetResponseDto
                BudgetResponseDto budgetResponseDto = new BudgetResponseDto();
                budgetResponseDto.setId(budget.getId());
                budgetResponseDto.setName(budget.getName());
                budgetResponseDto.setAmount(budget.getAmount());
                budgetResponseDto.setUser_id(budget.getUser().getId());

                // Konversi BudgetResponseDto menjadi Map
                Map<String, Object> responseData = objectMapper.convertValue(budgetResponseDto, Map.class);

                return BaseResponseDto.builder()
                        .status(HttpStatus.OK)
                        .description("Budget found")
                        .data(responseData) // Kirim Map
                        .build();
            } else {
                return BaseResponseDto.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .description("Budget not found")
                        .build();
            }
        } catch (Exception e) {
            return BaseResponseDto.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .description("Failed to fetch budget: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public BaseResponseDto getAll(String user_id) {
        try {
            List<Budget> budgets = budgetRepository.findAllByUserId(user_id);

            // Buat List untuk menampung hasil konversi dari Budget ke Map
            List<Map<String, Object>> responseDataList = new ArrayList<>();

            for (Budget budget : budgets) {
                // Konversi setiap Budget menjadi BudgetResponseDto
                BudgetResponseDto dto = new BudgetResponseDto();
                dto.setId(budget.getId());
                dto.setName(budget.getName());
                dto.setAmount(budget.getAmount());
                dto.setUser_id(budget.getUser().getId());

                // Konversi BudgetResponseDto menjadi Map<String, Object>
                Map<String, Object> budgetMap = objectMapper.convertValue(dto, Map.class);

                // Tambahkan Map ke List
                responseDataList.add(budgetMap);
            }

            // Bungkus List<Map<String, Object>> ke dalam Map<String, Object>
            Map<String, Object> responseData = Map.of("budgets", responseDataList);

            return BaseResponseDto.builder()
                    .status(HttpStatus.OK)
                    .description("Budgets retrieved successfully")
                    .data(responseData) // Kirim Map yang berisi List<Map<String, Object>>
                    .build();
        } catch (Exception e) {
            return BaseResponseDto.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .description("Failed to fetch budgets: " + e.getMessage())
                    .build();
        }
    }



    @Override
    public BaseResponseDto save(BudgetRequestDto budgetRequestDto) {
        try {
            Budget budget = new Budget();
            BeanUtils.copyProperties(budgetRequestDto, budget);

            // Ambil user dari repository berdasarkan userId
            Users user = userRepository.findById(budgetRequestDto.getUser_id())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + budgetRequestDto.getUser_id()));
            budget.setUser(user); // Set user pada budget

            budgetRepository.save(budget);

            // Ubah Budget menjadi BudgetResponseDto
            BudgetResponseDto budgetResponseDto = new BudgetResponseDto();
            budgetResponseDto.setId(budget.getId());
            budgetResponseDto.setName(budget.getName());
            budgetResponseDto.setAmount(budget.getAmount());
            budgetResponseDto.setUser_id(user.getId());

            // Konversi BudgetResponseDto menjadi Map
            Map<String, Object> responseData = objectMapper.convertValue(budgetResponseDto, Map.class);

            return BaseResponseDto.builder()
                    .status(HttpStatus.CREATED)
                    .description("Budget created successfully")
                    .data(responseData) // Kirim Map
                    .build();
        } catch (Exception e) {
            return BaseResponseDto.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .description("Failed to save budget: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public BaseResponseDto update(BudgetRequestDto budgetRequestDto, String id) {
        try {
            Optional<Budget> optionalBudget = budgetRepository.findById(id);
            if (optionalBudget.isPresent()) {
                Budget existingBudget = optionalBudget.get();
                BeanUtils.copyProperties(budgetRequestDto, existingBudget, "id"); // Exclude id from being overwritten
                budgetRepository.save(existingBudget);

                // Ubah Budget menjadi BudgetResponseDto
                BudgetResponseDto budgetResponseDto = new BudgetResponseDto();
                budgetResponseDto.setId(existingBudget.getId());
                budgetResponseDto.setName(existingBudget.getName());
                budgetResponseDto.setAmount(existingBudget.getAmount());
                budgetResponseDto.setUser_id(existingBudget.getUser().getId());

                // Konversi BudgetResponseDto menjadi Map
                Map<String, Object> responseData = objectMapper.convertValue(budgetResponseDto, Map.class);

                return BaseResponseDto.builder()
                        .status(HttpStatus.OK)
                        .description("Budget updated successfully")
                        .data(responseData) // Kirim Map
                        .build();
            } else {
                return BaseResponseDto.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .description("Budget not found")
                        .build();
            }
        } catch (Exception e) {
            return BaseResponseDto.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .description("Failed to update budget: " + e.getMessage())
                    .build();
        }
    }

}
