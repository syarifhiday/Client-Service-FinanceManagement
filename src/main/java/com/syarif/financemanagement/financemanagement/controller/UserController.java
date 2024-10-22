package com.syarif.financemanagement.financemanagement.controller;

import com.syarif.financemanagement.financemanagement.dto.request.RegisterRequestDto;
import com.syarif.financemanagement.financemanagement.dto.response.BaseResponseDto;
import com.syarif.financemanagement.financemanagement.service.Impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    // Endpoint to get a user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponseDto> getUserById(@PathVariable("userId") String userId) {
        BaseResponseDto response = userDetailService.getOne(userId);
        return new ResponseEntity<>(response, response.getStatus());
    }

}
