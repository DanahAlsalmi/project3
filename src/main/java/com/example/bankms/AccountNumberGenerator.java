package com.example.bankms;

import org.springframework.context.annotation.Bean;

import java.util.Random;

public class AccountNumberGenerator {

    public static String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            if (i > 0) {
                accountNumber.append("-");
            }
            for (int j = 0; j < 4; j++) {
                accountNumber.append(random.nextInt(10));
            }
        }

        return accountNumber.toString();
    }
}
