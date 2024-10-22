package com.syarif.financemanagement.financemanagement.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Ambil token dari SecurityContextHolder
            String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();

            // Sertakan token dalam header Authorization
            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }
}