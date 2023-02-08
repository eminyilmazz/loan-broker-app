package com.eminyilmazz.loanbrokerapp.controller;

import com.eminyilmazz.loanbrokerapp.model.Loan;
import com.eminyilmazz.loanbrokerapp.model.dto.GetLoansRequestDto;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanApplicationDto;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanPaymentApplication;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanResponseDto;
import com.eminyilmazz.loanbrokerapp.service.ILoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.eminyilmazz.loanbrokerapp.utility.TcknValidator.validate;

@RestController
@RequestMapping("/loan")
public class FinancialController {
    @Autowired
    ILoanService loanService;

    @GetMapping("/get/all")
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.status(HttpStatus.OK).body(loanService.getAll());
    }
    @GetMapping("/history")
    public ResponseEntity<List<Loan>> getLoans(@Valid @RequestBody GetLoansRequestDto request) {
        validate(request.getTckn());
        return ResponseEntity.status(HttpStatus.OK).body(loanService.getByCustomer(request));
    }

    @PostMapping("/apply")
    public ResponseEntity<LoanResponseDto> applyLoan(@Valid @RequestBody LoanApplicationDto application) {
        validate(application.getTckn());
        return ResponseEntity.status(HttpStatus.OK).body(loanService.applyLoan(application));
    }

    @PutMapping("/pay")
    public ResponseEntity<String> payLoan(@Valid @RequestBody LoanPaymentApplication application) {
        validate(application.getTckn());
        return ResponseEntity.status(HttpStatus.OK).body(loanService.payLoan(application));
    }
}
