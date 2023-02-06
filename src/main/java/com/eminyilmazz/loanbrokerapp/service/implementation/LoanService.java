package com.eminyilmazz.loanbrokerapp.service.implementation;

import com.eminyilmazz.loanbrokerapp.exception.CustomerNotFoundException;
import com.eminyilmazz.loanbrokerapp.messaging.CreditScoreProducer;
import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.Loan;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanApplicationDto;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanResponseDto;
import com.eminyilmazz.loanbrokerapp.repository.LoanRepository;
import com.eminyilmazz.loanbrokerapp.service.ICustomerService;
import com.eminyilmazz.loanbrokerapp.service.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.eminyilmazz.loanbrokerapp.utility.LoanUtility.processApplication;

@Service
public class LoanService implements ILoanService {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ICustomerService customerService;
    @Autowired
    CreditScoreProducer creditScoreProducer;
    @Value("${com.eminyilmazz.duedate.days:30}")
    int dueDateDays;
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public List<Loan> getAll() {
        return loanRepository.findAll();
    }

    @Override
    public LoanResponseDto applyLoan(LoanApplicationDto application) {
        Customer customer;
        try {
            customer = customerService.getByTckn(application.getTckn());
        } catch (Exception e) {
            throw new CustomerNotFoundException(e.getMessage());
        }
        customer.setCreditScore(creditScoreProducer.send(application.getTckn()));
        LoanResponseDto loanRsp = processApplication(customer, application);
        if (loanRsp.isApproved()) {
            loanRepository.save(Loan.builder()
                    .loanAmount(loanRsp.getAmount())
                    .approvalStatus(loanRsp.isApproved())
                    .customer(customer)
                    .dueStatus(true)
                    .approvalDate(LocalDate.now())
                    .build());
        }
        return loanRsp;
    }
}
