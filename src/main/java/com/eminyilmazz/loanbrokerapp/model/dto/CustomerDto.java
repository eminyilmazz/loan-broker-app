package com.eminyilmazz.loanbrokerapp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto implements Serializable {
    @ApiModelProperty(name = "Customer TCKN", required = true, notes = "MUST be 11 digits number", example = "12345678910")
    @Digits(fraction = 0, integer = 11)
    @NotNull(message = "TCKN cannot be empty.")
    private Long tckn;
    @ApiModelProperty(name = "Customer birth date", required = true, notes = "Must be a past date", example = "1997-06-20")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Birth date needs be in this format: \"yyyy-MM-dd\"")
    @JsonProperty(value = "birth_date")
    private String birthDate;
    @ApiModelProperty(name = "Customer first name", required = true, notes = "String", example = "'Emin'")
    @JsonProperty(value = "first_name")
    private String firstName;
    @ApiModelProperty(name = "Customer last name", required = true, notes = "String", example = "'Yilmaz'")
    @JsonProperty(value = "last_name")
    private String lastName;
    @ApiModelProperty(name = "Customer phone number", required = true, notes = "String", example = "'5301234567'")
    @Pattern(regexp = "^[0-9]{10}", message = "Phone number needs to be 10 digits and can only contain only numbers.")
    @JsonProperty(value = "phone_number")
    private String phoneNumber;
    @ApiModelProperty(name = "Customer email address", required = true, notes = "String", example = "'example@gmail.com'")
    @Email(message = "Not valid email address!")
    @JsonProperty(value = "email_address")
    private String emailAddress;
    @ApiModelProperty(name = "Customer monthly salary", required = true, notes = "double", example = "8000.0")
    @Min(0)
    @JsonProperty(value = "monthly_salary")
    private Double monthlySalary;
}
