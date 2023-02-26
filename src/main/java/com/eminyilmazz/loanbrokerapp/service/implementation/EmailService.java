package com.eminyilmazz.loanbrokerapp.service.implementation;

import com.eminyilmazz.loanbrokerapp.event.LoanEvent;
import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.Loan;
import com.eminyilmazz.loanbrokerapp.service.IEmailService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static com.eminyilmazz.loanbrokerapp.utility.LoanUtility.formatCurrency;

@Service
public class EmailService implements IEmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    JavaMailSender javaMailSender;
    @Value("${spring.mail.username:loan-broker-app@outlook.com}")
    String _from;

    @Override
    @ServiceActivator(inputChannel = "emailServiceInputChannel")
    public void sendEmail(Message<LoanEvent> message) throws MessagingException {
        Loan loan = message.getPayload().getLoan();
        Customer customer = loan.getCustomer();
        logger.debug("Sending email to customer: {}, email address: {}", customer.getTckn(), customer.getEmailAddress());
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, "UTF-8");
        String approved = loan.isApprovalStatus() ? "approved" : "not approved";
        String content = String.format("Dear %s %s, your loan application is %s. Amount: %s", customer.getFirstName(), customer.getLastName(), approved, formatCurrency(loan.getLoanAmount()));
        try {
            helper.setSubject("Loan application - " + customer.getTckn());
            helper.setTo(customer.getEmailAddress());
            helper.setFrom(_from);
            helper.setText(content);
            javaMailSender.send(mail);

        } catch (MessagingException e) {
            logger.error("Error sending mail");
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }
}
