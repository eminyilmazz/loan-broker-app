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
    void getAll() {
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

    }

    @Test
    void getByTcknAndBirthDate() {
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