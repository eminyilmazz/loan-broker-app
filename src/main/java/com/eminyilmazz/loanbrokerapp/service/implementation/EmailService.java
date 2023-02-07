package com.eminyilmazz.loanbrokerapp.service.implementation;

import com.eminyilmazz.loanbrokerapp.service.IEmailService;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {
    @Override
    @ServiceActivator(inputChannel = "smsServiceInputChannel")
    public void sendEmail() {

    }
}
