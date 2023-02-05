package com.eminyilmazz.loanbrokerapp.repository;

import com.eminyilmazz.loanbrokerapp.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
