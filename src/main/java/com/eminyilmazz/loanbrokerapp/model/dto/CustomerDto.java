package com.eminyilmazz.loanbrokerapp.model.dto;

import jakarta.validation.constraints.*;
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
    @Digits(fraction = 0, integer = 11)
    @NotNull(message = "TCKN cannot be empty.")
    private Long tckn;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Birth date needs be in this format: \"yyyy-MM-dd\"")
    private String birthDate;
    private String firstName;
    private String lastName;
    @Pattern(regexp = "^[0-9]{10}", message = "Phone number needs to be 10 digits and can only contain only numbers.")
    private String phoneNumber;
    @Email
    private String emailAddress;
    @Min(0)
    private Double monthlySalary;
}
