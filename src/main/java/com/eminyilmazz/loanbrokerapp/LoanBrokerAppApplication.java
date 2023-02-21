package com.eminyilmazz.loanbrokerapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class LoanBrokerAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanBrokerAppApplication.class, args);
    }

}
