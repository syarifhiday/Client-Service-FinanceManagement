package com.syarif.financemanagement.financemanagement.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponseDto> register(@RequestBody RegisterRequestDto userRegister) {
        BaseResponseDto responseDto = userDetailsService.register(userRegister);
        return new ResponseEntity<>(responseDto, responseDto.getStatus());
    }


    @PostMapping("/login")
    public ResponseEntity<BaseResponseDto> createToken(
            @RequestBody LoginRequestDto authRequest, HttpServletResponse res) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(BaseResponseDto.builder()
                            .status(HttpStatus.UNAUTHORIZED)
                            .description("Invalid credentials")
                            .build());
        }

        // Load user details dan generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());

        // Buat cookie untuk menyimpan token
        Cookie cookie = new Cookie("jwtToken", token);
        cookie.setHttpOnly(true); // Agar cookie tidak bisa diakses dari JavaScript
        cookie.setMaxAge(86400); // Set cookie untuk satu hari (86400 detik)
        res.addCookie(cookie); // Menambahkan cookie ke response

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("token", token);

        BaseResponseDto responseDto = BaseResponseDto.builder()
                .status(HttpStatus.OK)
                .description("Login successful")
                .data(responseData)
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
