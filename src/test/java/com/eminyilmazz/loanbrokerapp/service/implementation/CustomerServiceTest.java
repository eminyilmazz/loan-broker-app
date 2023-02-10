package com.eminyilmazz.loanbrokerapp.service.implementation;

import com.eminyilmazz.loanbrokerapp.exception.CustomerNotFoundException;
import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;

    @Test
    void testGetAllCustomers() {
        // Given
        List<Customer> expectedCustomers = Arrays.asList(new Customer(), new Customer(), new Customer());

        // When
        when(customerRepository.findAll()).thenReturn(expectedCustomers);
        List<Customer> result = customerService.getAll();

        // Then
        verify(customerRepository, times(1)).findAll();
        assertEquals(expectedCustomers, result);
    }

    @Test
    void testGetByTckn_whenCustomerFound_shouldReturnCustomer() {
        //given
        Long tckn = 12345678910L;
        Customer expectedCustomer = new Customer();
        expectedCustomer.setTckn(tckn);
        //when
        when(customerRepository.findById(tckn)).thenReturn(Optional.of(expectedCustomer));
        Customer actualCustomer = customerService.getByTckn(tckn);
        //then
        assertEquals(expectedCustomer, actualCustomer);
        verify(customerRepository, times(1)).findById(tckn);
    }

    @Test
    void testGetByTckn_whenCustomerNotFound_shouldThrowCustomerNotFoundException() {
        //given
        Long tckn = 12345678910L;
        //when
        Mockito.when(customerRepository.findById(tckn)).thenReturn(Optional.empty());
        //then
        Exception exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getByTckn(tckn);
        });
        assertEquals("Customer tckn: " + tckn + " not found!", exception.getMessage());
        verify(customerRepository, times(1)).findById(tckn);
    }

    @Test
    void getByTcknAndBirthDate_WhenCustomerFound_ShouldReturnCustomer() {
        //given
        LocalDate birthDate = LocalDate.of(1999, 1, 1);
        Customer customer = new Customer(12345678910L, birthDate, "Foo", "Bar", "1234567890", "dummy.test@loanbroker.com", 3000.0);
        //when
        when(customerRepository.findByTcknAndBirthDate(12345678910L, birthDate))
                .thenReturn(Optional.of(customer));
        //then
        Customer result = customerService.getByTcknAndBirthDate(12345678910L, birthDate);
        assertEquals(customer, result);
        verify(customerRepository, times(1)).findByTcknAndBirthDate(anyLong(), any());
    }

    @Test
    void getByTcknAndBirthDate_WhenCustomerNotFound_ShouldThrowException() {
        //given
        LocalDate birthDate = LocalDate.of(1999, 1, 1);
        //when
        when(customerRepository.findByTcknAndBirthDate(anyLong(), any(LocalDate.class)))
                .thenReturn(Optional.empty());
        //then
        Exception exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getByTcknAndBirthDate(12345678910L, birthDate);
        });
        String expectedMessage = "Customer tckn: " + 12345678910L + " and birth date" + birthDate + " not found!";
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(exception.getClass(), CustomerNotFoundException.class);
        verify(customerRepository, times(1)).findByTcknAndBirthDate(anyLong(), any());
    }

    @Test
    void addCustomer() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomer() {
    }
}