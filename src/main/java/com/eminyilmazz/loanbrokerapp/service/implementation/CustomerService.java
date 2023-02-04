package com.eminyilmazz.loanbrokerapp.service.implementation;

import com.eminyilmazz.loanbrokerapp.exception.CustomerNotFoundException;
import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.repository.CustomerRepository;
import com.eminyilmazz.loanbrokerapp.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getByTckn(Long tckn) {
        Customer customer = customerRepository.findById(tckn).orElseThrow(() -> new CustomerNotFoundException(String.format("Customer was not found with tckn %d", tckn)));
        return customer;
    }

}
