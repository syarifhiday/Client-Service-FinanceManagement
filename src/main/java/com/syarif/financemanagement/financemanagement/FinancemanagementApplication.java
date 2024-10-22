package com.syarif.financemanagement.financemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FinancemanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancemanagementApplication.class, args);
	}

}