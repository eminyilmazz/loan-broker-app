package com.eminyilmazz.loanbrokerapp.service;

import com.eminyilmazz.loanbrokerapp.exception.DuplicateTcknException;
import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.dto.CustomerDto;

import java.time.LocalDate;
import java.util.List;

public interface ICustomerService {
    List<Customer> getAll();

    Customer getByTckn(Long tckn);

    Customer getByTcknAndBirthDate(Long tckn, LocalDate birthDate);

    Customer addCustomer(CustomerDto customerDto) throws DuplicateTcknException;

    Customer updateCustomer(CustomerDto customerDto);

    boolean deleteCustomer(Long tckn);

}
