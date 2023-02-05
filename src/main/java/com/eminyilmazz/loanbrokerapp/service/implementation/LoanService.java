package com.eminyilmazz.loanbrokerapp.service.implementation;

import com.eminyilmazz.loanbrokerapp.model.Loan;
import com.eminyilmazz.loanbrokerapp.repository.LoanRepository;
import com.eminyilmazz.loanbrokerapp.service.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService implements ILoanService {
    @Autowired
    LoanRepository loanRepository;

    @Override
    public List<Loan> getAll() {
        return loanRepository.findAll();
    }
}
