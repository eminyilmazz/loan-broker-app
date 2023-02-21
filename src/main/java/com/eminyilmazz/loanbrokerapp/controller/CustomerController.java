package com.eminyilmazz.loanbrokerapp.controller;

import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.dto.CustomerDto;
import com.eminyilmazz.loanbrokerapp.service.ICustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.eminyilmazz.loanbrokerapp.utility.TcknValidator.validate;

@RestController
@RequestMapping("/customer")
@Api(value = "Customer Controller")
public class CustomerController {
    @Autowired
    ICustomerService customerService;

    @ApiOperation(value = "Get all customers.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetched all customers successfully", response = List.class, responseContainer = "ResponseEntity")})
    @GetMapping(value = "/all")
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @ApiOperation(value = "Get customer for given TCKN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetched customer successfully", response = Customer.class, responseContainer = "ResponseEntity"),
            @ApiResponse(code = 404, message = "Customer not found.")})
    @GetMapping("/get")
    public ResponseEntity<Customer> getByTckn(@RequestParam(name = "tckn") Long tckn) {
        validate(tckn);
        return ResponseEntity.ok(customerService.getByTckn(tckn));
    }

    @ApiOperation(value = "Add a customer", code = 202)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Customer added successfully", response = Customer.class, responseContainer = "ResponseEntity"),
            @ApiResponse(code = 400, message = "Bad request. Duplicate TCKN.")})
    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody CustomerDto customerDto) {
        validate(customerDto.getTckn());
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.addCustomer(customerDto));
    }

    @ApiOperation(value = "Update an existing customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer updated successfully", response = Customer.class, responseContainer = "ResponseEntity"),
            @ApiResponse(code = 400, message = "Bad Request. Invalid attributes provided."),
            @ApiResponse(code = 404, message = "Not found. Customer does not exist.")})
    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(customerDto));
    }

    @ApiOperation(value = "Delete a customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer deleted successfully"),
            @ApiResponse(code = 404, message = "Not found. TCKN does not exist.")})
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@RequestParam(name = "tckn") Long tckn) {
        validate(tckn);
        customerService.deleteCustomer(tckn);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted.");
    }
}
