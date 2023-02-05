package com.eminyilmazz.loanbrokerapp.controller;

import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.dto.CustomerDto;
import com.eminyilmazz.loanbrokerapp.service.ICustomerService;
import com.eminyilmazz.loanbrokerapp.utility.TcknValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    ICustomerService customerService;

    @GetMapping(value = "/all")
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/get")
    public ResponseEntity<Customer> getByTckn(@RequestParam(name = "tckn") Long tckn) {
        TcknValidator.validate(tckn);
        return ResponseEntity.ok(customerService.getByTckn(tckn));
    }

    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody CustomerDto customerDto) {
        TcknValidator.validate(customerDto.getTckn());
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.addCustomer(customerDto));
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(customerDto));
    }
}
