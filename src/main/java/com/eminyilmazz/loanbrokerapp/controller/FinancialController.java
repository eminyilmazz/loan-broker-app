package com.eminyilmazz.loanbrokerapp.controller;

import com.eminyilmazz.loanbrokerapp.model.Loan;
import com.eminyilmazz.loanbrokerapp.service.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class FinancialController {
    @Autowired
    ILoanService loanService;

    @GetMapping("/get/all")
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.status(HttpStatus.OK).body(loanService.getAll());
    }
}
