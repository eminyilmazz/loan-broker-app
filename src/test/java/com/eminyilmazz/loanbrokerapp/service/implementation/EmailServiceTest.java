package com.eminyilmazz.loanbrokerapp.service.implementation;

import com.eminyilmazz.loanbrokerapp.event.LoanEvent;
import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.Loan;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private MimeMessage mimeMessage;
    @Mock
    private MimeMessageHelper mimeMessageHelper;
    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    public void setUp() throws MessagingException {
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    void sendEmailTest() throws MessagingException {
        //given
        Loan loan = new Loan();
        Customer customer = new Customer(12345678910L, LocalDate.of(1999, 1, 1), "Foo", "Bar", "1234567890", "dummy.test@loanbroker.com", 3000.0);
        customer.setEmailAddress("test@example.com");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        loan.setCustomer(customer);
        loan.setApprovalStatus(true);
        loan.setLoanAmount(1000.0);
        loan.setCustomer(customer);
        LoanEvent loanEvent = new LoanEvent(this, loan);
        emailService._from = "test@gmail.com";
        //then
        emailService.sendEmail(MessageBuilder.withPayload(loanEvent).build());
        verify(javaMailSender).send(mimeMessage);
    }
}