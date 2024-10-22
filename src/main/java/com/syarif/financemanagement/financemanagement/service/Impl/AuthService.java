package com.syarif.financemanagement.financemanagement.service.Impl;

import com.syarif.financemanagement.financemanagement.dto.request.LoginRequestDto;
import com.syarif.financemanagement.financemanagement.dto.request.RegisterRequestDto;
import com.syarif.financemanagement.financemanagement.dto.response.BaseResponseDto;
import com.syarif.financemanagement.financemanagement.service.Impl.UserDetailServiceImpl;
import com.syarif.financemanagement.financemanagement.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    public BaseResponseDto login(LoginRequestDto loginRequest, HttpServletResponse response) {
        try {
            // Autentikasi pengguna
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // Load user details dan generate token
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            String token = jwtUtil.generateToken(userDetails.getUsername());

            // Buat cookie untuk menyimpan token
            Cookie cookie = new Cookie("jwtToken", token);
            cookie.setHttpOnly(true); // Agar cookie tidak bisa diakses dari JavaScript
            cookie.setMaxAge(86400); // Set cookie untuk satu hari (86400 detik)
            response.addCookie(cookie); // Menambahkan cookie ke response

            return BaseResponseDto.builder()
                    .status(HttpStatus.OK)
                    .description("Login successful")
                    .build();
        } catch (Exception e) {
            return BaseResponseDto.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .description("Invalid credentials")
                    .build();
        }
    }

    public BaseResponseDto register(RegisterRequestDto userRegister) {
        return userDetailsService.register(userRegister);
    }
}
