package com.eminyilmazz.loanbrokerapp.utility;

import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanApplicationDto;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoanUtilityTest {

    @Test
    void whenCreditScoreIs1000_withSalary16000_withoutAssurance_thenLoanIsApprovedAndAmount48000() {
        //given
        Customer customer = new Customer(12345678910L, LocalDate.of(1999, 1, 1), "Foo", "Bar", "1234567890", "foo.bar@loanbroker.com", 16000.0);
        customer.setCreditScore(1000);
        LoanApplicationDto application = new LoanApplicationDto();
        application.setTckn(12345678910L);
        application.setBirthDate("1999-01-01");
        application.setAssurance(0.0);

        //when
        LoanResponseDto loanResponse = LoanUtility.processApplication(customer, application);

        //then
        assertTrue(loanResponse.isApproved());
        assertEquals(64000.0, loanResponse.getAmount());
    }

    @Test
    void whenCreditScoreIs1000_withSalary16000_withAssurance10000_thenLoanIsApprovedAndAmount69000() {
        //given
        Customer customer = new Customer(12345678910L, LocalDate.of(1999, 1, 1), "Foo", "Bar", "1234567890", "foo.bar@loanbroker.com", 16000.0);
        customer.setCreditScore(1000);
        LoanApplicationDto application = new LoanApplicationDto();
        application.setTckn(12345678910L);
        application.setBirthDate("1999-01-01");
        application.setAssurance(10000.0);
        //when
        LoanResponseDto loanResponse = LoanUtility.processApplication(customer, application);
        //then
        assertTrue(loanResponse.isApproved());
        assertEquals(69000.0, loanResponse.getAmount());
    }

    @Test
    void whenCreditScoreIsBetween500And1000AndSalaryIsLessThan5000_withAssurance10000_thenLoanIsApprovedAndAmount11000() {
        //given
        Customer customer = new Customer(12345678910L, LocalDate.of(1999, 1, 1), "Foo", "Bar", "1234567890", "foo.bar@loanbroker.com", 420.0);
        customer.setCreditScore(700);
        LoanApplicationDto application = new LoanApplicationDto();
        application.setTckn(12345678910L);
        application.setBirthDate("1999-01-01");
        application.setAssurance(10000.0);
        //when
        LoanResponseDto loanResponse = LoanUtility.processApplication(customer, application);
        //then
        assertTrue(loanResponse.isApproved());
        assertEquals(11000.0, loanResponse.getAmount());
    }

    @Test
    void whenCreditScoreIsBetween500And1000AndSalaryIsBetween5000And10000_withAssurance10000_thenLoanIsApprovedAndAmount22000() {
        //given
        Customer customer = new Customer(12345678910L, LocalDate.of(1999, 1, 1), "Foo", "Bar", "1234567890", "foo.bar@loanbroker.com", 8000.0);
        customer.setCreditScore(700);
        LoanApplicationDto application = new LoanApplicationDto();
        application.setTckn(12345678910L);
        application.setBirthDate("1999-01-01");
        application.setAssurance(10000.0);
        //when
        LoanResponseDto loanResponse = LoanUtility.processApplication(customer, application);
        //then
        assertTrue(loanResponse.isApproved());
        assertEquals(22000.0, loanResponse.getAmount());
    }

    @Test
    void whenCreditScoreIsBetween500And1000AndSalaryIsLargerThan10000_withAssurance10000_thenLoanIsApprovedAndAmount32500() {
        // Given
        Customer customer = new Customer(12345678910L, LocalDate.of(1999, 1, 1), "Foo", "Bar", "1234567890", "foo.bar@loanbroker.com", 15000.0);
        customer.setCreditScore(700);
        LoanApplicationDto application = new LoanApplicationDto();
        application.setTckn(12345678910L);
        application.setBirthDate("1999-01-01");
        application.setAssurance(10000.0);
        // When
        LoanResponseDto loanResponse = LoanUtility.processApplication(customer, application);
        // Then
        assertTrue(loanResponse.isApproved());
        assertEquals(32500.0, loanResponse.getAmount());
    }

    @Test
    void whenCreditScoreIsLowerThan500_thenLoanIsNotApprovedAndAmount0() {
        //given
        Customer customer = new Customer(12345678910L, LocalDate.of(1999, 1, 1), "Foo", "Bar", "1234567890", "foo.bar@loanbroker.com", 15000.0);
        customer.setCreditScore(420);
        LoanApplicationDto application = new LoanApplicationDto();
        application.setTckn(12345678910L);
        application.setBirthDate("1999-01-01");
        application.setAssurance(0.0);
        //when
        LoanResponseDto loanResponse = LoanUtility.processApplication(customer, application);
        //then
        assertFalse(loanResponse.isApproved());
        assertEquals(0.0, loanResponse.getAmount());
    }

    @Test
    void formatCurrency_WithValidInput_ReturnsFormattedCurrency() {
        double input = 12345.67;
        String expectedResult = "$12,345.67";
        String actualResult = LoanUtility.formatCurrency(input);
        assertEquals(expectedResult, actualResult);
    }
}