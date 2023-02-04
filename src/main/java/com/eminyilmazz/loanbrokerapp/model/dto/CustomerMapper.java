package com.eminyilmazz.loanbrokerapp.model.dto;

import com.eminyilmazz.loanbrokerapp.model.Customer;

public class CustomerMapper {
    private CustomerMapper() {
    }

    public static Customer toEntity(CustomerDto customerDto) {
        return Customer.builder()
                .tckn(customerDto.getTckn())
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .phoneNumber(customerDto.getPhoneNumber())
                .emailAddress(customerDto.getEmailAddress())
                .monthlySalary(customerDto.getMonthlySalary())
                .build();
    }
}
