package com.eminyilmazz.loanbrokerapp.event;

import com.eminyilmazz.loanbrokerapp.model.Loan;
import org.springframework.context.ApplicationEvent;

public class LoanEvent extends ApplicationEvent {
    private final Loan loan;

    public LoanEvent(Object source, Loan loan) {
        super(source);
        this.loan = loan;
    }

    public Loan getLoan() {
        return loan;
    }
}
