package com.eminyilmazz.loanbrokerapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customer implements Serializable {
    @ApiModelProperty(name = "Customer TCKN", required = true, notes = "MUST be 11 digits number", example = "12345678910")
    @Id
    @Column(name = "tckn", nullable = false)
    @Digits(fraction = 0, integer = 11)
    private Long tckn;
    @ApiModelProperty(name = "Customer birth date", required = true, notes = "Must be a past date", example = "1997-06-20")
    @Column(name = "birth_date", nullable = false)
    @Past
    @JsonProperty(value = "birth_date")
    private LocalDate birthDate;
    @Transient
    @JsonIgnore
    private Integer creditScore;
    @ApiModelProperty(name = "Customer first name", notes = "String", example = "'Emin'")
    @Column(name = "first_name")
    @JsonProperty(value = "first_name")
    private String firstName;
    @ApiModelProperty(name = "Customer last name", notes = "String", example = "'Yilmaz'")
    @Column(name = "last_name")
    @JsonProperty(value = "last_name")
    private String lastName;
    @ApiModelProperty(name = "Customer phone number", notes = "String", example = "'5301234567'")
    @Column(name = "phone_number")
    @Pattern(regexp = "^[0-9]{10}", message = "Phone number needs to be 10 digits and can only contain only numbers.")
    @JsonProperty(value = "phone_number")
    private String phoneNumber;
    @ApiModelProperty(name = "Customer email address", notes = "String", example = "'example@gmail.com'")
    @Column(name = "email_address")
    @Email(message = "Not valid email address!")
    @JsonProperty(value = "email_address")
    private String emailAddress;
    @ApiModelProperty(name = "Customer monthly salary", notes = "double", example = "8000.0")
    @Column(name = "monthly_salary")
    @JsonProperty(value = "monthly_salary")
    private double monthlySalary;
    @Transient
    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JsonProperty(value = "loans")
    private List<Loan> loanList;

    public Customer(Long tckn, LocalDate birthDate, String firstName, String lastName, String phoneNumber, String emailAddress, double monthlySalary) {
        this.tckn = tckn;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.monthlySalary = monthlySalary;
    }
}
