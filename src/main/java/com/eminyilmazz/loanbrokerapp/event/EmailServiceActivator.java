package com.eminyilmazz.loanbrokerapp.event;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceActivator extends AbstractNotificationServiceActivator{

    protected EmailServiceActivator(@Qualifier(value = "emailServiceInputChannel") MessageChannel requestChannel) {
        super(requestChannel);
    }

    @EventListener
    public void handleLoanEvent(LoanEvent loanEvent) {
        sendMessage(MessageBuilder.withPayload(loanEvent).build());
    }
}
