package com.syarif.financemanagement.financemanagement.service.Impl;

import com.syarif.financemanagement.financemanagement.dto.request.TransactionRequestDto;
import com.syarif.financemanagement.financemanagement.dto.response.BaseResponseDto;
import com.syarif.financemanagement.financemanagement.dto.response.TransactionResponseDto;
import com.syarif.financemanagement.financemanagement.entity.Users;
import com.syarif.financemanagement.financemanagement.repository.UserRepository;
import com.syarif.financemanagement.financemanagement.service.TransactionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public BaseResponseDto handleTransaction(TransactionRequestDto transactionRequestDto) {
        String url = "http://localhost:8081/v1/transactions";

        ResponseEntity<TransactionResponseDto> response = restTemplate.postForEntity(url, transactionRequestDto, TransactionResponseDto.class);

        if (response.getStatusCodeValue() == 201) {
            TransactionResponseDto transactionResponse = response.getBody();

            Optional<Users> optionalUser = Optional.ofNullable(userRepository.findByCreditCard(transactionRequestDto.getCreditCard()));
            if (optionalUser.isPresent()) {
                Users user = optionalUser.get();

                if (transactionRequestDto.getIsIncome()) {
                    log.info("Menambahkan {} ke saldo user.", transactionRequestDto.getAmount());
                    user.setBalance(user.getBalance() + transactionRequestDto.getAmount());
                } else {
                    if (!transactionRequestDto.getRecipientCreditCard().isBlank()){
                        Optional<Users> optionalRecepientUser = Optional.ofNullable(userRepository.findByCreditCard(transactionRequestDto.getRecipientCreditCard()));
                        if (optionalRecepientUser.isPresent()) {
                            Users recepientUser = optionalRecepientUser.get();
                            recepientUser.setBalance(recepientUser.getBalance() + transactionRequestDto.getAmount());
                            log.info("Menambahkan {} ke saldo recepient user.", transactionRequestDto.getAmount());
                        }
                    }
                    log.info("Mengurangi {} dari saldo user.", transactionRequestDto.getAmount());
                    user.setBalance(user.getBalance() - transactionRequestDto.getAmount());
                }

                userRepository.save(user);

                log.info("Saldo user setelah transaksi: {}", user.getBalance());
            } else {
                log.warn("User tidak ditemukan dengan creditCard: {}", transactionRequestDto.getCreditCard());
            }

            return BaseResponseDto.builder()
                    .status(HttpStatus.CREATED)
                    .description("Transaction successful")
                    .data(Collections.singletonMap("transaction", transactionResponse))
                    .build();
        }

        log.error("Transaksi gagal dengan status: {}", response.getStatusCodeValue());
        return BaseResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .description("Transaction failed")
                .build();
    }
}
