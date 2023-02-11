package com.eminyilmazz.loanbrokerapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;

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
    @Id
    @Column(name = "tckn", nullable = false)
    @Digits(fraction = 0, integer = 11)
    private Long tckn;
    @Column(name = "birth_date", nullable = false)
    @Past
    @JsonProperty(value = "birth_date")
    private LocalDate birthDate;
    @Transient
    @JsonIgnore
    private Integer creditScore;
    @Column(name = "first_name")
    @JsonProperty(value = "first_name")
    private String firstName;
    @Column(name = "last_name")
    @JsonProperty(value = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    @Pattern(regexp = "^[0-9]{10}", message = "Phone number needs to be 10 digits and can only contain only numbers.")
    @JsonProperty(value = "phone_number")
    private String phoneNumber;
    @Column(name = "email_address")
    @Email
    @JsonProperty(value = "email_address")
    private String emailAddress;
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
