package com.eminyilmazz.loanbrokerapp.service.implementation;

import com.eminyilmazz.loanbrokerapp.exception.CustomerNotFoundException;
import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.Loan;
import com.eminyilmazz.loanbrokerapp.model.dto.GetLoansRequestDto;
import com.eminyilmazz.loanbrokerapp.repository.LoanRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private LoanService loanService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getByCustomer_WhenRequestHasApprovedStatusFalse() {
        //given
        LocalDate birthDate = LocalDate.of(2000, 1, 1);
        Customer customer = new Customer(12345678910L, birthDate, "Dummy", "Test", "1234567890", "dummy.test@loanbroker.com", 9000D);
        List<Loan> expectedLoanList = Arrays.asList(new Loan(1L, 20000D, customer, true, true),
                new Loan(2L, 10000D, customer, true, true),
                new Loan(3L, 10000D, customer, true, false),
                new Loan(4L, 0D, customer, false, true),
                new Loan(5L, 0D, customer, false, false));
        GetLoansRequestDto getLoansRequestDto = new GetLoansRequestDto();
        getLoansRequestDto.setApproved("false");
        getLoansRequestDto.setTckn(12345678910L);
        getLoansRequestDto.setBirthDate(DateTimeFormatter.ofPattern(DATE_FORMAT).format(birthDate));
        //when
        when(customerService.getByTcknAndBirthDate(getLoansRequestDto.getTckn(), birthDate)).thenReturn(customer);
        when(loanRepository.findByCustomer(customer)).thenReturn(expectedLoanList);
        List<Loan> actualLoanList = loanService.getByCustomer(getLoansRequestDto);
        //then
        assertEquals(expectedLoanList.size(), actualLoanList.size());
        for (Loan expectedLoan : expectedLoanList) {
            Optional<Loan> actualLoan = actualLoanList.stream()
                    .filter((actual) -> Objects.equals(actual.getId(), expectedLoan.getId()))
                    .findFirst();
            assertEquals(expectedLoan.isApprovalStatus(), actualLoan.get().isApprovalStatus());
            assertEquals(expectedLoan.getLoanAmount(), actualLoan.get().getLoanAmount());
            assertEquals(expectedLoan.getCustomer(), actualLoan.get().getCustomer());
            assertEquals(expectedLoan.isDueStatus(), actualLoan.get().isDueStatus());
        }
    }

    @Test
    void getByCustomer_WhenRequestDoesNotHaveApprovedStatus() {
        //given
        LocalDate birthDate = LocalDate.of(2000, 1, 1);
        Customer customer = new Customer(12345678910L, birthDate, "Dummy", "Test", "1234567890", "dummy.test@loanbroker.com", 9000D);
        List<Loan> expectedLoanList = Arrays.asList(new Loan(1L, 20000D, customer, true, true),
                new Loan(2L, 10000D, customer, true, true),
                new Loan(3L, 10000D, customer, true, false),
                new Loan(4L, 0D, customer, false, true),
                new Loan(5L, 0D, customer, false, false));
        GetLoansRequestDto getLoansRequestDto = new GetLoansRequestDto();
        getLoansRequestDto.setTckn(12345678910L);
        getLoansRequestDto.setBirthDate(DateTimeFormatter.ofPattern(DATE_FORMAT).format(birthDate));

        //when
        when(customerService.getByTcknAndBirthDate(getLoansRequestDto.getTckn(), birthDate)).thenReturn(customer);
        when(loanRepository.findByCustomer(customer)).thenReturn(expectedLoanList);
        List<Loan> actualLoanList = loanService.getByCustomer(getLoansRequestDto);

        //then
        assertEquals(expectedLoanList.size(), actualLoanList.size());
        for (Loan expectedLoan : expectedLoanList) {
            Optional<Loan> actualLoan = actualLoanList.stream()
                    .filter((actual) -> Objects.equals(actual.getId(), expectedLoan.getId()))
                    .findFirst();
            assertEquals(expectedLoan.isApprovalStatus(), actualLoan.get().isApprovalStatus());
            assertEquals(expectedLoan.getLoanAmount(), actualLoan.get().getLoanAmount());
            assertEquals(expectedLoan.getCustomer(), actualLoan.get().getCustomer());
            assertEquals(expectedLoan.isDueStatus(), actualLoan.get().isDueStatus());
        }
    }

    @Test
    void getByCustomer_WhenRequestHasApprovedStatusTrue() {
        //given
        LocalDate birthDate = LocalDate.of(2000, 1, 1);
        Customer customer = new Customer(12345678910L, birthDate, "Dummy", "Test", "1234567890", "dummy.test@loanbroker.com", 9000D);
        List<Loan> expectedLoanList = Arrays.asList(new Loan(1L, 20000D, customer, true, true),
                new Loan(2L, 10000D, customer, true, true),
                new Loan(3L, 10000D, customer, true, false));

        GetLoansRequestDto getLoansRequestDto = new GetLoansRequestDto();
        getLoansRequestDto.setTckn(12345678910L);
        getLoansRequestDto.setApproved("true");
        getLoansRequestDto.setBirthDate(DateTimeFormatter.ofPattern(DATE_FORMAT).format(birthDate));

        //when
        when(customerService.getByTcknAndBirthDate(getLoansRequestDto.getTckn(), birthDate)).thenReturn(customer);
        when(loanRepository.findByCustomerAndApprovalStatus(customer, Boolean.parseBoolean(getLoansRequestDto.getApproved()))).thenReturn(expectedLoanList);
        List<Loan> actualLoanList = loanService.getByCustomer(getLoansRequestDto);

        //then
        assertEquals(expectedLoanList.size(), actualLoanList.size());
        for (Loan expectedLoan : expectedLoanList) {
            Optional<Loan> actualLoan = actualLoanList.stream()
                    .filter((actual) -> Objects.equals(actual.getId(), expectedLoan.getId()))
                    .findFirst();
            assertEquals(expectedLoan.isApprovalStatus(), actualLoan.get().isApprovalStatus());
            assertEquals(expectedLoan.getLoanAmount(), actualLoan.get().getLoanAmount());
            assertEquals(expectedLoan.getCustomer(), actualLoan.get().getCustomer());
            assertEquals(expectedLoan.isDueStatus(), actualLoan.get().isDueStatus());
        }
    }

    @Test
    void getByCustomer_WhenCustomerDoesNotExist_ThrowNotFoundException() {
        //given
        LocalDate birthDate = LocalDate.of(2000, 1, 1);
        Customer customer = new Customer(12345678910L, birthDate, "Dummy", "Test", "1234567890", "dummy.test@loanbroker.com", 9000D);
        GetLoansRequestDto getLoansRequestDto = new GetLoansRequestDto();
        getLoansRequestDto.setTckn(12345678910L);
        getLoansRequestDto.setApproved("true");
        getLoansRequestDto.setBirthDate(DateTimeFormatter.ofPattern(DATE_FORMAT).format(birthDate));
        //when
        when(customerService.getByTcknAndBirthDate(customer.getTckn(), birthDate)).thenThrow(new CustomerNotFoundException("Customer tckn: " + getLoansRequestDto.getTckn() + " and birth date" + birthDate + " not found!"));
        Exception exception = assertThrows(CustomerNotFoundException.class, () -> loanService.getByCustomer(getLoansRequestDto));
        //then
        assertEquals("Customer tckn: " + getLoansRequestDto.getTckn() + " and birth date" + birthDate + " not found!", exception.getMessage());
        assertEquals(exception.getClass(), CustomerNotFoundException.class);
    }

    @Test
    void applyLoan() {
    }

    @Test
    void payLoan() {
    }
}