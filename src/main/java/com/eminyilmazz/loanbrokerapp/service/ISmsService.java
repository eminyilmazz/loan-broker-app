package com.eminyilmazz.loanbrokerapp.service;

import org.springframework.messaging.Message;

public interface ISmsService {
    void sendSms(Message message);
}
