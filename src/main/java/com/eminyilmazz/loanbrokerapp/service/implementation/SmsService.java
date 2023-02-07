package com.eminyilmazz.loanbrokerapp.service.implementation;

import com.eminyilmazz.loanbrokerapp.event.LoanEvent;
import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.Loan;
import com.eminyilmazz.loanbrokerapp.service.ISmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import static com.eminyilmazz.loanbrokerapp.utility.LoanUtility.formatCurrency;

@Service
public class SmsService implements ISmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    @Override
    @ServiceActivator(inputChannel = "smsServiceInputChannel")
    public void sendSms(Message<LoanEvent> message) {
        Loan loan = message.getPayload().getLoan();
        Customer customer = loan.getCustomer();
        String approved = loan.isApprovalStatus() ? "approved" : "not approved";
        logger.info("Dear {} {}, your loan application is {}. Amount: {}", customer.getFirstName(), customer.getLastName(), approved, formatCurrency(loan.getLoanAmount()));
    }
}
