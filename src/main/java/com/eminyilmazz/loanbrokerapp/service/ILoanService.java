package com.eminyilmazz.loanbrokerapp.service;

import com.eminyilmazz.loanbrokerapp.model.Loan;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanApplicationDto;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanResponseDto;

import java.util.List;

public interface ILoanService {
    List<Loan> getAll();

    LoanResponseDto applyLoan(LoanApplicationDto application);
}
