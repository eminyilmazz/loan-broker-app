package com.eminyilmazz.loanbrokerapp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class LoanPaymentApplication {
    @ApiModelProperty(name = "Loan id", required = true, notes = "Loan id that is to be paid.", example = "1")
    @NotNull
    private Long id;
    @ApiModelProperty(name = "Customer TCKN", required = true, notes = "MUST be 11 digits number", example = "12345678910")
    @Digits(fraction = 0, integer = 11)
    @NotNull(message = "TCKN cannot be empty.")
    private Long tckn;
    @ApiModelProperty(name = "Customer birth date", required = true, notes = "Must be a past date", example = "1997-06-20")
    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Birth date needs be in this format: \"yyyy-MM-dd\"")
    @JsonProperty(value = "birth_date")
    private String birthDate;
}
