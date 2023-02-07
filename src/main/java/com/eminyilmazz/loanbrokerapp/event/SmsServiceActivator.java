package com.eminyilmazz.loanbrokerapp.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceActivator extends AbstractNotificationServiceActivator {

    @Autowired
    public SmsServiceActivator(@Qualifier(value = "smsServiceInputChannel") MessageChannel requestChannel) {
        super(requestChannel);
    }

    @EventListener
    public void handleLoanEvent(LoanEvent loanEvent) {
        sendMessage(MessageBuilder.withPayload(loanEvent).build());
    }
}
