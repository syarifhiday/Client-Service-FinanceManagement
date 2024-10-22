package com.syarif.financemanagement.financemanagement.service.Impl;

import com.syarif.financemanagement.financemanagement.dto.request.RegisterRequestDto;
import com.syarif.financemanagement.financemanagement.dto.response.BaseResponseDto;
import com.syarif.financemanagement.financemanagement.dto.response.UserResponseDto;
import com.syarif.financemanagement.financemanagement.entity.Users;
import com.syarif.financemanagement.financemanagement.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
    }

    public BaseResponseDto register(RegisterRequestDto userRegister) {
        try {
            Users user = new Users();
            BeanUtils.copyProperties(userRegister, user);
            user.setPassword(passwordEncoder().encode(userRegister.getPassword()));
            userRepository.save(user);
            return BaseResponseDto.builder()
                    .status(HttpStatus.CREATED)
                    .description("User created")
                    .build();
        } catch (Exception e) {
            return BaseResponseDto.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .description("Failed to register user")
                    .build();
        }
    }

    public BaseResponseDto getOne(String userId) {
        try {
            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));

            // Buat objek UserResponseDto dan masukkan data user dan budget
            UserResponseDto userResponse = new UserResponseDto();
            userResponse.setId(user.getId());
            userResponse.setName(user.getName());
            userResponse.setUsername(user.getUsername());
            userResponse.setBalance(user.getBalance());
            userResponse.setCreditCard(user.getCreditCard());
            userResponse.setBudgets(user.getBudgets()); // Masukkan list budget ke dalam response

            return BaseResponseDto.builder()
                    .status(HttpStatus.OK)
                    .description("User found")
                    .data(Collections.singletonMap("user", userResponse)) // Gunakan UserResponseDto sebagai data
                    .build();
        } catch (NoSuchElementException e) {
            return BaseResponseDto.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .description(e.getMessage())
                    .build();
        } catch (Exception e) {
            return BaseResponseDto.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .description("An error occurred while retrieving the user")
                    .build();
        }
    }

}

