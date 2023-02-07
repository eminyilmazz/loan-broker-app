package com.eminyilmazz.loanbrokerapp.event;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

public abstract class AbstractNotificationServiceActivator {
    private final MessageChannel requestChannel;

    protected AbstractNotificationServiceActivator(MessageChannel requestChannel) {
        this.requestChannel = requestChannel;
    }

    protected void sendMessage(Message<?> message) {
        requestChannel.send(message);
    }
}
