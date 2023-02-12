package com.eminyilmazz.loanbrokerapp.controller;


import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.Loan;
import com.eminyilmazz.loanbrokerapp.model.dto.GetLoansRequestDto;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanApplicationDto;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanPaymentApplication;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource(locations = "classpath:test-application.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FinancialControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllLoans() throws Exception {
        List<Loan> expectedLoans = getLoans();
        mockMvc.perform(get("/loan/get/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedLoans.size())));
    }

    @Test
    void testGetLoansByCustomerSuccess() throws Exception {
        Long tckn = 10000000010L;
        GetLoansRequestDto request = new GetLoansRequestDto();
        request.setApproved("false");
        request.setTckn(tckn);
        request.setBirthDate("1999-10-30");

        mockMvc.perform(get("/loan/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].loan_amount").value(10000.0))
                .andExpect(jsonPath("$[0].approval_date").value("2021-10-13T10:44:42.163"))
                .andExpect(jsonPath("$[0].approval_status").value(true))
                .andExpect(jsonPath("$[0].due_status").value(true))
                .andExpect(jsonPath("$[1].loan_amount").value(0.0))
                .andExpect(jsonPath("$[1].approval_date").value("2022-11-03T15:26:32.166"))
                .andExpect(jsonPath("$[1].approval_status").value(false))
                .andExpect(jsonPath("$[1].due_status").value(false));
    }
    @Test
    void applyLoan_when850CreditScore() throws Exception {
        long tckn = 10000000850L;
        LoanApplicationDto loanApplicationDto = new LoanApplicationDto();
        loanApplicationDto.setTckn(tckn);
        loanApplicationDto.setBirthDate("1997-06-20");
        loanApplicationDto.setAssurance(10000.0);
        LoanResponseDto expected = new LoanResponseDto(true, 22000.0);

        mockMvc.perform(post("/loan/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.approval_status").value(expected.isApproved()))
                .andExpect(jsonPath("$.credit_amount").value(expected.getAmount()));
    }

    @Test
    void applyLoan_when1000CreditScore() throws Exception {
        long tckn = 10000000950L;
        LoanApplicationDto loanApplicationDto = new LoanApplicationDto();
        loanApplicationDto.setTckn(tckn);
        loanApplicationDto.setBirthDate("1993-04-14");
        loanApplicationDto.setAssurance(10000.0);
        LoanResponseDto expected = new LoanResponseDto(true, 39748.0);

        mockMvc.perform(post("/loan/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.approval_status").value(expected.isApproved()))
                .andExpect(jsonPath("$.credit_amount").value(expected.getAmount()));
    }

    @Test
    void applyLoan_when50CreditScore() throws Exception {
        long tckn = 10000000050L;
        LoanApplicationDto loanApplicationDto = new LoanApplicationDto();
        loanApplicationDto.setTckn(tckn);
        loanApplicationDto.setBirthDate("1993-10-19");
        loanApplicationDto.setAssurance(10000.0);
        LoanResponseDto expected = new LoanResponseDto(false, 0.0);

        mockMvc.perform(post("/loan/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.approval_status").value(expected.isApproved()))
                .andExpect(jsonPath("$.credit_amount").value(expected.getAmount()));
    }

    @Test
    void applyLoan_when850CreditScore_withoutAssurance() throws Exception {
        long tckn = 10000000850L;
        LoanApplicationDto loanApplicationDto = new LoanApplicationDto();
        loanApplicationDto.setTckn(tckn);
        loanApplicationDto.setBirthDate("1997-06-20");
        LoanResponseDto expected = new LoanResponseDto(true, 20000.0);

        mockMvc.perform(post("/loan/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.approval_status").value(expected.isApproved()))
                .andExpect(jsonPath("$.credit_amount").value(expected.getAmount()));
    }

    @Test
    void applyLoan_whenTcknDoesNotExist_shouldReturnError() throws Exception {
        LoanApplicationDto loanApplicationDto = new LoanApplicationDto();
        loanApplicationDto.setBirthDate("1993-10-19");
        loanApplicationDto.setAssurance(10000.0);

        mockMvc.perform(post("/loan/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.tckn").value("TCKN cannot be empty."));
    }

    @Test
    void applyLoan_whenBirthDateDoesNotExist_shouldReturnError() throws Exception {
        long tckn = 10000000050L;
        LoanApplicationDto loanApplicationDto = new LoanApplicationDto();
        loanApplicationDto.setTckn(tckn);
        loanApplicationDto.setAssurance(10000.0);

        mockMvc.perform(post("/loan/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.birthDate").value("must not be blank"));
    }

    @Test
    void payLoan_validInput_returnsSuccessful() throws Exception {
        LoanPaymentApplication application = new LoanPaymentApplication(9L, 10000000810L, "1995-06-22");


        mockMvc.perform(put("/loan/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(application)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Payment successful"));
    }

    @Test
    void payLoan_invalidInput_returnsAlreadyPaid() throws Exception {
        LoanPaymentApplication application = new LoanPaymentApplication(5L, 10000000950L, "1993-04-14");


        mockMvc.perform(put("/loan/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(application)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Already paid"));
    }

    private static List<Customer> getCustomers() {
        return Arrays.asList(
                new Customer(10000000850L, LocalDate.of(1997, 6, 20), "Emi", "Bradford", "4683423427", "b-emi@outlook.net", 6974),
                new Customer(10000000950L, LocalDate.of(1993, 4, 14), "Allen", "Carlson", "4051433878", "carlson_allen3730@yahoo.com", 8687),
                new Customer(10000000010L, LocalDate.of(1999, 10, 30), "Colby", "Rose", "2623458683", "colbyrose4723@icloud.net", 5904),
                new Customer(10000000810L, LocalDate.of(1995, 6, 22), "Vivien", "Munoz", "4145187362", "vmunoz1016@outlook.net", 4335),
                new Customer(10000000050L, LocalDate.of(1993, 10, 19), "Eaton", "Phillips", "2462487285", "e-phillips@outlook.net", 5956)
        );
    }

    private static List<Loan> getLoans() {
        return Arrays.asList(
                new Loan(1L, 20000.0, getCustomer(10000000850L), true, true),
                new Loan(2L, 0.0, getCustomer(10000000850L), false, false),
                new Loan(3L, 10000.0, getCustomer(10000000850L), true, false),
                new Loan(4L, 0.0, getCustomer(10000000950L), false, false),
                new Loan(5L, 27000.0, getCustomer(10000000950L), true, false),
                new Loan(6L, 32500.0, getCustomer(10000000950L), true, true),
                new Loan(7L, 0.0, getCustomer(10000000050L), false, false),
                new Loan(8L, 0.0, getCustomer(10000000050L), false, false),
                new Loan(9L, 10000.0, getCustomer(10000000810L), true, true),
                new Loan(10L, 10000.0, getCustomer(10000000010L), true, true),
                new Loan(11L, 0.0, getCustomer(10000000010L), false, false)
        );
    }

    private static Customer getCustomer(Long tckn) {
        List<Customer> customers = getCustomers();
        return customers.stream().filter(c -> c.getTckn().equals(tckn)).findFirst().orElse(null);
    }
}
