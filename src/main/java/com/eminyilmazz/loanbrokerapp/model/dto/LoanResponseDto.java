package com.eminyilmazz.loanbrokerapp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponseDto implements Serializable {
    @ApiModelProperty(name = "Approval status", notes = "Boolean value of approval status of the loan application", example = "true")
    @JsonProperty(value = "approval_status")
    boolean approved;
    @ApiModelProperty(name = "Credit amount", notes = "Loan amount credited.", example = "1234.0")
    @JsonProperty(value = "credit_amount")
    double amount;
}
