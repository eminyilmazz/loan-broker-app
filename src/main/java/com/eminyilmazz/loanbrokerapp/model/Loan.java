package com.eminyilmazz.loanbrokerapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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
    @ApiModelProperty(name = "Loan amount", required = true, example = "1234.0")
    @Column(name = "loan_amount")
    @JsonProperty(value = "loan_amount")
    private Double loanAmount;
    @ApiModelProperty(name = "Customer TCKN", required = true, notes = "MUST be 11 digits number, Refers to owner of the loan", example = "12345678910")
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_tckn", referencedColumnName = "tckn")
    private Customer customer;
    @ApiModelProperty(name = "Approval date", notes = "The date for loan approval", example = "2023-01-30 23:31:11.167000")
    @Column(name = "approval_date")
    @CreationTimestamp
    @JsonProperty(value = "approval_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime approvalDate;
    @ApiModelProperty(name = "Approval status", notes = "Boolean value of approval status of loan application", example = "true")
    @Column(name = "approval_status", nullable = false)
    @JsonProperty(value = "approval_status")
    private boolean approvalStatus;
    @ApiModelProperty(name = "Due status", notes = "Boolean value of due status of an approved loan", example = "true")
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
