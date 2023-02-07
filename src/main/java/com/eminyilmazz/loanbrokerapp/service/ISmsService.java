package com.eminyilmazz.loanbrokerapp.service;

import com.eminyilmazz.loanbrokerapp.event.LoanEvent;
import org.springframework.messaging.Message;

public interface ISmsService {
    void sendSms(Message<LoanEvent> message);
}
