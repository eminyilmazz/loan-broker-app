package com.eminyilmazz.loanbrokerapp.messaging;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreditScoreProducerTest {
    @Autowired
    private CreditScoreProducer creditScoreProducer;

    @Test
    void testSendMessage_shouldReturn1000() {
        Long tckn = 12345678910L;
        Integer returnedValue = creditScoreProducer.send(tckn);
        assertEquals(1000, returnedValue);
    }
    @Test
    void testSendMessage_shouldReturn750() {
        Long tckn = 12345678750L;
        Integer returnedValue = creditScoreProducer.send(tckn);
        assertEquals(750, returnedValue);
    }
    @Test
    void testSendMessage_shouldReturn0() {
        Long tckn = 12345678000L;
        Integer returnedValue = creditScoreProducer.send(tckn);
        assertEquals(0, returnedValue);
    }
}