package com.eminyilmazz.loanbrokerapp.messaging;

import com.eminyilmazz.loanbrokerapp.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditScoreProducer {
    private static final Logger logger = LoggerFactory.getLogger(CreditScoreProducer.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Integer send(Long value) {
        logger.trace("Sending credit score request for: {}", value);
        return (Integer) rabbitTemplate.convertSendAndReceive(RabbitConfig.EXCHANGE, RabbitConfig.CREDIT_QUEUE, value);
    }
}
