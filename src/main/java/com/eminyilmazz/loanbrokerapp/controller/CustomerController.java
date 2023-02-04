package com.eminyilmazz.loanbrokerapp.controller;

import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.service.ICustomerService;
import com.eminyilmazz.loanbrokerapp.utility.TcknValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
