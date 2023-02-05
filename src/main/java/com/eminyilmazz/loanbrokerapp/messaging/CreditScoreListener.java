package com.eminyilmazz.loanbrokerapp.messaging;

import com.eminyilmazz.loanbrokerapp.config.RabbitConfig;
import com.eminyilmazz.loanbrokerapp.utility.CreditScoreService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CreditScoreListener {

    @RabbitListener(queues = RabbitConfig.CREDIT_QUEUE)
    private Integer creditScoreListener(Long tckn){
        return CreditScoreService.calculateCreditScore(tckn);
    }
}
