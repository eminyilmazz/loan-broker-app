package com.eminyilmazz.loanbrokerapp.service.implementation;

import com.eminyilmazz.loanbrokerapp.event.LoanEvent;
import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.Loan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SmsServiceTest {
    @Mock
    Message<LoanEvent> message;
    @Mock
    LoanEvent loanEvent;
    @Mock
    Loan loan;
    @Mock
    Customer customer;
    @InjectMocks
    SmsService smsService;

    @Test
    void testSendSms() {
        //when
        when(message.getPayload()).thenReturn(loanEvent);
        when(loanEvent.getLoan()).thenReturn(loan);
        when(loan.getCustomer()).thenReturn(customer);
        when(customer.getFirstName()).thenReturn("John");
        when(customer.getLastName()).thenReturn("Doe");
        when(loan.isApprovalStatus()).thenReturn(true);
        when(loan.getLoanAmount()).thenReturn(100.0);
        //then
        smsService.sendSms(message);
        verify(message).getPayload();
        verify(loanEvent).getLoan();
        verify(loan).getCustomer();
        verify(customer).getFirstName();
        verify(customer).getLastName();
        verify(loan).isApprovalStatus();
        verify(loan).getLoanAmount();
    }
}