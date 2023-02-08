package com.eminyilmazz.loanbrokerapp.service;

import com.eminyilmazz.loanbrokerapp.event.LoanEvent;
import jakarta.mail.MessagingException;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

public interface IEmailService {
    @ServiceActivator(inputChannel = "emailServiceInputChannel")
    void sendEmail(Message<LoanEvent> msg) throws MessagingException;
}
