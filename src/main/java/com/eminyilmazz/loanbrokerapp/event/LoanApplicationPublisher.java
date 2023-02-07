package com.eminyilmazz.loanbrokerapp.event;

import com.eminyilmazz.loanbrokerapp.model.Loan;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component(value = "loanApplicationPublisher")
public class LoanApplicationPublisher implements ApplicationEventPublisher {
    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationPublisher.class);
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public LoanApplicationPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void onLoanApplication(Loan loan) {
        LoanEvent loanEvent = new LoanEvent(this, loan);
        logger.debug("Sending loan event");
        eventPublisher.publishEvent(loanEvent);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        ApplicationEventPublisher.super.publishEvent(event);
    }

    @Override
    public void publishEvent(Object event) {
    }
}
