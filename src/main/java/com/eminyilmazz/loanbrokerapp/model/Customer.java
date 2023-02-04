package com.eminyilmazz.loanbrokerapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private LocalDate birthDate;
    @Transient
    @JsonIgnore
    private Integer creditScore;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    @Pattern(regexp = "^[0-9]{10}", message = "Phone number needs to be 10 digits and can only contain only numbers.")
    private String phoneNumber;
    @Column(name = "email_address")
    @Email
    private String emailAddress;
    @Column(name = "monthly_salary")
    private double monthlySalary;
    @Transient
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Loan> loanList;
}
