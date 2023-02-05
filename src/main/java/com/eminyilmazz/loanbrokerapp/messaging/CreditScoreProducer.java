package com.eminyilmazz.loanbrokerapp.messaging;

import com.eminyilmazz.loanbrokerapp.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditScoreProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Integer send(Long value) {
        return (Integer) rabbitTemplate.convertSendAndReceive(RabbitConfig.EXCHANGE, RabbitConfig.CREDIT_QUEUE, value);
    }
}
