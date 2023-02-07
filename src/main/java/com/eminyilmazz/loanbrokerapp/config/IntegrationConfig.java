package com.eminyilmazz.loanbrokerapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class IntegrationConfig {

    @Bean(value = "smsServiceInputChannel")
    public MessageChannel smsServiceInputChannel() {
        return new DirectChannel();
    }

    @Bean(value = "emailServiceInputChannel")
    public MessageChannel emailServiceInputChannel() {
        return new DirectChannel();
    }
}
