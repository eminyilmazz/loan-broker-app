package com.eminyilmazz.loanbrokerapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDto implements Serializable {
    private Long tckn;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private Double monthlySalary;
}
