package com.eminyilmazz.loanbrokerapp.model.mapper;

import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.dto.CustomerDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerMapper {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private CustomerMapper() {
    }

    public static Customer toEntity(CustomerDto customerDto) {
        return Customer.builder()
                .tckn(customerDto.getTckn())
                .birthDate(LocalDate.from(DateTimeFormatter.ofPattern(DATE_FORMAT).parse(customerDto.getBirthDate())))
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .phoneNumber(customerDto.getPhoneNumber())
                .emailAddress(customerDto.getEmailAddress())
                .monthlySalary(customerDto.getMonthlySalary())
                .build();
    }
}
