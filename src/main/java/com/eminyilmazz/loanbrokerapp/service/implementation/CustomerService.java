package com.eminyilmazz.loanbrokerapp.service.implementation;

import com.eminyilmazz.loanbrokerapp.exception.CustomerNotFoundException;
import com.eminyilmazz.loanbrokerapp.exception.DuplicateTcknException;
import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.dto.CustomerDto;
import com.eminyilmazz.loanbrokerapp.repository.CustomerRepository;
import com.eminyilmazz.loanbrokerapp.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.eminyilmazz.loanbrokerapp.model.mapper.CustomerMapper.toEntity;

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
        return customerRepository.findById(tckn)
                .orElseThrow(() -> new CustomerNotFoundException("Customer tckn: " + tckn + " not found!"));

    }

    @Override
    public Customer getByTcknAndBirthDate(Long tckn, LocalDate birthDate) {
        return customerRepository.findByTcknAndBirthDate(tckn, birthDate)

                .orElseThrow(() -> new CustomerNotFoundException("Customer tckn: " + tckn + " and birth date" + birthDate + " not found!"));
    }

    @Override
    public Customer addCustomer(CustomerDto customerDto) throws DuplicateTcknException {
        if (customerRepository.existsById(customerDto.getTckn())) {
            throw new DuplicateTcknException();
        }
        return customerRepository.save(toEntity(customerDto));
    }

    @Override
    public Customer updateCustomer(CustomerDto customerDto) throws CustomerNotFoundException {
        if (!customerRepository.existsById(customerDto.getTckn())) {
            throw new CustomerNotFoundException("Customer tckn: " + customerDto.getTckn() + " not found!");
        }
        return customerRepository.save(toEntity(customerDto));
    }

    @Override
    public boolean deleteCustomer(Long tckn) {
        if (!customerRepository.existsById(tckn)) {
            throw new CustomerNotFoundException("Delete operation is not successful. The customer does not exist.");
        }
        customerRepository.deleteById(tckn);
        return true;
    }
}
