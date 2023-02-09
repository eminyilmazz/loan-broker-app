package com.eminyilmazz.loanbrokerapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    @JsonProperty(value = "loan_amount")
    private Double loanAmount;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_tckn", referencedColumnName = "tckn")
    private Customer customer;
    @Column(name = "approval_date")
    @CreationTimestamp
    @JsonProperty(value = "approval_date")
    private LocalDateTime approvalDate;
    @Column(name = "approval_status", nullable = false)
    @JsonProperty(value = "approval_status")
    private boolean approvalStatus;
    @Column(name = "due_status", nullable = false)
    @JsonProperty(value = "due_status")
    private boolean dueStatus;

    public Loan(Long id, Double loanAmount, Customer customer, boolean approvalStatus, boolean dueStatus) {
        this.id = id;
        this.loanAmount = loanAmount;
        this.customer = customer;
        this.approvalStatus = approvalStatus;
        this.dueStatus = dueStatus;
    }
}
