package com.eminyilmazz.loanbrokerapp.controller;

import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.service.implementation.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomerControllerTest {
    @Mock
    CustomerService customerService;
    @InjectMocks
    CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllCustomers_ReturnsListOfCustomers() {
        //given
        List<Customer> customers = Arrays.asList(
                new Customer(),
                new Customer()
        );
        //when
        when(customerService.getAll()).thenReturn(customers);
        //then
        List<Customer> response = customerController.getAll();
        assertEquals(customers, response);
    }

    @Test
    void getByTckn() {
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