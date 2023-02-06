package com.eminyilmazz.loanbrokerapp.service.implementation;

import com.eminyilmazz.loanbrokerapp.exception.CustomerNotFoundException;
import com.eminyilmazz.loanbrokerapp.messaging.CreditScoreProducer;
import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.Loan;
import com.eminyilmazz.loanbrokerapp.model.dto.GetLoansRequestDto;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanApplicationDto;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanResponseDto;
import com.eminyilmazz.loanbrokerapp.repository.LoanRepository;
import com.eminyilmazz.loanbrokerapp.service.ICustomerService;
import com.eminyilmazz.loanbrokerapp.service.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.eminyilmazz.loanbrokerapp.model.mapper.CustomerMapper.DATE_FORMAT;
import static com.eminyilmazz.loanbrokerapp.utility.LoanUtility.processApplication;

@Service
public class LoanService implements ILoanService {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ICustomerService customerService;
    @Autowired
    CreditScoreProducer creditScoreProducer;

    @Override
    public List<Loan> getAll() {
        return loanRepository.findAll();
    }

    @Override
    public List<Loan> getByCustomer(GetLoansRequestDto request) {
        Customer customer = customerService.getByTcknAndBirthDate(request.getTckn(),
                                                                  LocalDate.from(DateTimeFormatter.ofPattern(DATE_FORMAT).parse(request.getBirthDate())));
        if (Boolean.parseBoolean(request.getApproved())) return loanRepository.findByCustomerAndApprovalStatus(customer, true);
        else return loanRepository.findByCustomer(customer);
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
                    .approvalDate(LocalDateTime.now())
                    .build());
        }
        return loanRsp;
    }
}
