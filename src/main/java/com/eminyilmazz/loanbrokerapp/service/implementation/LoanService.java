package com.eminyilmazz.loanbrokerapp.service.implementation;

import com.eminyilmazz.loanbrokerapp.event.LoanApplicationPublisher;
import com.eminyilmazz.loanbrokerapp.exception.CustomerNotFoundException;
import com.eminyilmazz.loanbrokerapp.exception.LoanNotFoundException;
import com.eminyilmazz.loanbrokerapp.messaging.CreditScoreProducer;
import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.Loan;
import com.eminyilmazz.loanbrokerapp.model.dto.GetLoansRequestDto;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanApplicationDto;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanPaymentApplication;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanResponseDto;
import com.eminyilmazz.loanbrokerapp.repository.LoanRepository;
import com.eminyilmazz.loanbrokerapp.service.ICustomerService;
import com.eminyilmazz.loanbrokerapp.service.ILoanService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    LoanApplicationPublisher loanApplicationPublisher;
    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
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
        logger.info("Apply loan started");
        Customer customer;
        try {
            customer = customerService.getByTcknAndBirthDate(application.getTckn(), LocalDate.from(DateTimeFormatter.ofPattern(DATE_FORMAT).parse(application.getBirthDate())));
        } catch (Exception e) {
            throw new CustomerNotFoundException(e.getMessage());
        }
        customer.setCreditScore(creditScoreProducer.send(application.getTckn()));
        LoanResponseDto loanRsp = processApplication(customer, application);
        try {
            logger.debug("Loan application is processed: {}", objectMapper.writeValueAsString(loanRsp));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Loan loan = Loan.builder()
                .loanAmount(loanRsp.getAmount())
                .approvalStatus(loanRsp.isApproved())
                .customer(customer)
                .dueStatus(true)
                .approvalDate(LocalDateTime.now())
                .build();
        loanRepository.save(loan);
        logger.info("Apply loan completed.");
        loanApplicationPublisher.onLoanApplication(loan);
        return loanRsp;
    }

    @Override
    public String payLoan(LoanPaymentApplication application) {
        LocalDate date = LocalDate.from(DateTimeFormatter.ofPattern(DATE_FORMAT).parse(application.getBirthDate()));
        if(!loanRepository.existsByIdAndCustomer_TcknAndCustomer_BirthDate(application.getId(), application.getTckn(), date))
            throw new LoanNotFoundException("Loan with id: " + application.getId() + " tckn: " + application.getTckn() + " birth date: " + application.getBirthDate() + " not found!");
        Loan loan = loanRepository.findByIdAndCustomer_TcknAndCustomer_BirthDate(application.getId(), application.getTckn(), date);
        if (!loan.isDueStatus()) return "Already paid";
        loan.setDueStatus(false);
        loanRepository.save(loan);
        return "Payment successful";
    }
}
