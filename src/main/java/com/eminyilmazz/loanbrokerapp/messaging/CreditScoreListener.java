package com.eminyilmazz.loanbrokerapp.messaging;

import com.eminyilmazz.loanbrokerapp.config.RabbitConfig;
import com.eminyilmazz.loanbrokerapp.utility.CreditScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CreditScoreListener {
    private static final Logger logger = LoggerFactory.getLogger(CreditScoreListener.class);

    @RabbitListener(queues = RabbitConfig.CREDIT_QUEUE)
    private Integer creditScoreListener(Long tckn) {
        logger.trace("Credit score request received for: {}", tckn);
        return CreditScoreService.calculateCreditScore(tckn);
    }
}
