package com.eminyilmazz.loanbrokerapp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponseDto {
    @JsonProperty(value = "approval_status")
    boolean approved;
    @JsonProperty(value = "credit_amount")
    double amount;
}
