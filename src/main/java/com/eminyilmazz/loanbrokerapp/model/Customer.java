package com.eminyilmazz.loanbrokerapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {
    @Id
    @Column(name = "tckn", nullable = false)
    private Long tckn;

    @Transient
    private Integer creditScore;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "monthly_salary")
    private double monthlySalary;
}
