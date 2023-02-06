package com.eminyilmazz.loanbrokerapp.model.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoanApplicationDto {
    @Digits(fraction = 0, integer = 11)
    @NotNull(message = "TCKN cannot be empty.")
    private Long tckn;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Birth date needs be in this format: \"yyyy-MM-dd\"")
    private String birthDate;
    private double assurance;
}
