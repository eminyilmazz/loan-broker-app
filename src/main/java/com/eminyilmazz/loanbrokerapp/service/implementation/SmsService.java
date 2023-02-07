package com.eminyilmazz.loanbrokerapp.service.implementation;

import com.eminyilmazz.loanbrokerapp.service.ISmsService;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class SmsService implements ISmsService {

    @Override
    @ServiceActivator(inputChannel = "smsServiceInputChannel")
    public void sendSms(Message message) {
    }
}
