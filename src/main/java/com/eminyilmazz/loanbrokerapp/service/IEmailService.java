package com.eminyilmazz.loanbrokerapp.service;

import com.eminyilmazz.loanbrokerapp.event.LoanEvent;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import javax.mail.MessagingException;

public interface IEmailService {
    @ServiceActivator(inputChannel = "emailServiceInputChannel")
    void sendEmail(Message<LoanEvent> msg) throws MessagingException;
}
