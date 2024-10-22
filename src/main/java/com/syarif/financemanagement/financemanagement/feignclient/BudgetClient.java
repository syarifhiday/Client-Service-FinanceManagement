package com.syarif.financemanagement.financemanagement.feignclient;

import com.syarif.financemanagement.financemanagement.configuration.FeignClientConfig;
import com.syarif.financemanagement.financemanagement.dto.response.BaseResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "budget-service", url = "http://localhost:8080/v1/budget", configuration = FeignClientConfig.class)
public interface BudgetClient {

    @GetMapping("/user/{userId}")
    BaseResponseDto getBudgetsByUserId(@PathVariable("userId") String userId);
}