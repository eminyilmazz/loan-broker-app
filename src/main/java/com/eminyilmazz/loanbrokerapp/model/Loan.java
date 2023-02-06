package com.eminyilmazz.loanbrokerapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Loan implements Serializable {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "loan_amount")
    private Double loanAmount;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_tckn", referencedColumnName = "tckn")
    private Customer customer;
    @Column(name = "approval_date")
    @CreationTimestamp
    private LocalDate approvalDate;
    @Column(name = "approval_status", nullable = false)
    private boolean approvalStatus;
    @Column(name = "due_status", nullable = false)
    private boolean dueStatus;
}
