package com.eminyilmazz.loanbrokerapp.controller;

import com.eminyilmazz.loanbrokerapp.exception.CustomerNotFoundException;
import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.Loan;
import com.eminyilmazz.loanbrokerapp.model.dto.CustomerDto;
import com.eminyilmazz.loanbrokerapp.repository.CustomerRepository;
import com.eminyilmazz.loanbrokerapp.service.implementation.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource(locations = "classpath:test-application.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CustomerControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

    @Test
    void testGetAllCustomers() {
        ResponseEntity<List<Customer>> response = restTemplate.exchange(
                "/customer/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Customer>>() {
                });

        List<Customer> customers = response.getBody();
        assertThat(customers).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getByTckn_returnsExpectedCustomer() throws Exception {
        Long tckn = 10000000850L;
        Customer expected = getCustomer(tckn);

        mockMvc.perform(get("/customer/get?tckn=" + tckn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tckn").value(expected.getTckn()))
                .andExpect(jsonPath("$.birth_date").value(String.valueOf(expected.getBirthDate())))
                .andExpect(jsonPath("$.first_name").value(expected.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(expected.getLastName()))
                .andExpect(jsonPath("$.phone_number").value(expected.getPhoneNumber()))
                .andExpect(jsonPath("$.email_address").value(expected.getEmailAddress()))
                .andExpect(jsonPath("$.monthly_salary").value(expected.getMonthlySalary()));
    }

    @Test
    void getByTckn_notFound() throws Exception {
        long tckn = 12345674200L;
        mockMvc.perform(get("/customer/get?tckn=" + tckn))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("CustomerNotFoundException: Customer tckn: " + tckn + " not found!"));
    }

    @Test
    void givenCustomerDto_whenAddCustomer_thenReturnCreatedCustomer() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setTckn(98765432100L);
        customerDto.setBirthDate("1999-01-01");
        customerDto.setFirstName("Foo");
        customerDto.setLastName("Bar");
        customerDto.setPhoneNumber("1234567890");
        customerDto.setEmailAddress("foo.bar@loanbroker.com");
        customerDto.setMonthlySalary(3000.0);

        Customer expected = new Customer();
        expected.setTckn(customerDto.getTckn());
        expected.setBirthDate(LocalDate.of(1999, 1, 1));
        expected.setFirstName(customerDto.getFirstName());
        expected.setLastName(customerDto.getLastName());
        expected.setPhoneNumber(customerDto.getPhoneNumber());
        expected.setEmailAddress(customerDto.getEmailAddress());
        expected.setMonthlySalary(customerDto.getMonthlySalary());


        mockMvc.perform(post("/customer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tckn").value(expected.getTckn()))
                .andExpect(jsonPath("$.birth_date").value(String.valueOf(expected.getBirthDate())))
                .andExpect(jsonPath("$.first_name").value(expected.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(expected.getLastName()))
                .andExpect(jsonPath("$.phone_number").value(expected.getPhoneNumber()))
                .andExpect(jsonPath("$.email_address").value(expected.getEmailAddress()))
                .andExpect(jsonPath("$.monthly_salary").value(expected.getMonthlySalary()));
        customerRepository.deleteById(expected.getTckn());
    }

    @Test
    void addCustomer_whenTcknAlreadyExists_thenReturnConflict() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setTckn(10000000850L);
        customerDto.setBirthDate("1999-01-01");
        customerDto.setFirstName("Foo");
        customerDto.setLastName("Bar");
        customerDto.setPhoneNumber("1234567890");
        customerDto.setEmailAddress("foo.bar@loanbroker.com");
        customerDto.setMonthlySalary(3000.0);

        mockMvc.perform(post("/customer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("DuplicateTcknException: Provided TCKN already exists.\nCannot accept duplicate TCKN.\n"))
                .andReturn();
    }

    @Test
    void updateCustomer_validInput_customerUpdatedSuccessfully() throws Exception {
        CustomerDto customerDto = new CustomerDto(10000000850L, "1997-06-20", "Emi", "Bradford", "4683423428", "b-emi@gmail.com", 5000.0);
        Customer expected = new Customer(10000000850L, LocalDate.of(1997, 6, 20), "Emi", "Bradford", "4683423428", "b-emi@gmail.com", 5000.0);

        mockMvc.perform(put("/customer/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tckn").value(expected.getTckn()))
                .andExpect(jsonPath("$.birth_date").value(String.valueOf(expected.getBirthDate())))
                .andExpect(jsonPath("$.first_name").value(expected.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(expected.getLastName()))
                .andExpect(jsonPath("$.phone_number").value(expected.getPhoneNumber()))
                .andExpect(jsonPath("$.email_address").value(expected.getEmailAddress()))
                .andExpect(jsonPath("$.monthly_salary").value(expected.getMonthlySalary()));
    }

    @Test
    void updateCustomer_whenTcknDoesNotExist_thenReturnNotFound() throws Exception {
        CustomerDto customerDto = new CustomerDto(12345678910L, "1997-01-01", "Foo", "Bar", "1234567890", "foo.bar@gmail.com", 3000.0);

        mockMvc.perform(put("/customer/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("CustomerNotFoundException: Customer tckn: 12345678910 not found!"));
    }


    @Test
    void deleteCustomer_whenExistingTcknIsProvided_shouldDeleteTheCustomer() throws Exception {
        long tckn = 12345678910L;
        LocalDate birthDate = LocalDate.of(1999, 1, 1);
        Customer customer = new Customer(tckn, birthDate, "Foo", "Bar", "1234567890", "foo.bar@loanbroker.com", 3000.0);

        customerRepository.save(customer);

        mockMvc.perform(delete("/customer/delete?tckn=" + tckn))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully deleted."));

        assertThrows(CustomerNotFoundException.class, () -> customerService.getByTckn(tckn));
    }

    @Test
    void deleteCustomer_whenNonExistingTcknIsProvided_shouldReturnNotFound() throws Exception {
        long tckn = 99999999999L;

        mockMvc.perform(delete("/customer/delete?tckn=" + tckn))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("CustomerNotFoundException: Delete operation is not successful. The customer does not exist."));
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
