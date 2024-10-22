package com.syarif.financemanagement.financemanagement.controller;

import com.syarif.financemanagement.financemanagement.dto.request.LoginRequestDto;
import com.syarif.financemanagement.financemanagement.dto.request.RegisterRequestDto;
import com.syarif.financemanagement.financemanagement.dto.response.BaseResponseDto;
import com.syarif.financemanagement.financemanagement.service.Impl.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class AuthUIController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RestTemplate restTemplate; // Autowire RestTemplate

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Mengembalikan halaman login
    }

    @PostMapping("/login") // Pastikan ini untuk POST
    public String login(@ModelAttribute LoginRequestDto loginRequest, HttpServletResponse response) {
        // Memanggil endpoint login
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequestDto> entity = new HttpEntity<>(loginRequest, headers);

        // Memanggil endpoint login
        ResponseEntity<BaseResponseDto> res = restTemplate.exchange(
                "http://localhost:8080/v1/auth/login", // Ganti dengan URL endpoint yang sesuai
                HttpMethod.POST,
                entity,
                BaseResponseDto.class
        );

        if (res.getStatusCode().is2xxSuccessful()) {
            // Redirect ke halaman budget jika login berhasil
            return "redirect:/user/budgets";
        } else {
            // Redirect ke halaman login jika login gagal
            return "redirect:/login?error=true";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // Mengembalikan halaman register
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequestDto registerRequest) {
        // Implementasi register seperti sebelumnya
        ResponseEntity<BaseResponseDto> res = restTemplate.postForEntity(
                "http://localhost:8080/v1/auth/register", // Ganti dengan URL endpoint yang sesuai
                registerRequest,
                BaseResponseDto.class
        );

        if (res.getStatusCode().is2xxSuccessful()) {
            // Redirect ke halaman login jika registrasi berhasil
            return "redirect:/login?success=true";
        } else {
            // Redirect ke halaman register jika registrasi gagal
            return "redirect:/register?error=true";
        }
    }
}