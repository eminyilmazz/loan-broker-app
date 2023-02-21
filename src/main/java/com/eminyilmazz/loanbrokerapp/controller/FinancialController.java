package com.eminyilmazz.loanbrokerapp.controller;

import com.eminyilmazz.loanbrokerapp.model.Loan;
import com.eminyilmazz.loanbrokerapp.model.dto.GetLoansRequestDto;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanApplicationDto;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanPaymentApplication;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanResponseDto;
import com.eminyilmazz.loanbrokerapp.service.ILoanService;
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
@RequestMapping("/loan")
@Api(value = "Loan Controller")
public class FinancialController {
    @Autowired
    ILoanService loanService;

    /**
     * @return List<Loan>
     * @apiNote Endpoint to get all loans.
     */
    @ApiOperation(value = "Get all loans.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetched all loans successfully", response = List.class, responseContainer = "ResponseEntity")})
    @GetMapping("/get/all")
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.status(HttpStatus.OK).body(loanService.getAll());
    }

    /**
     * @param request fields: tckn, birth_date, approved (optional)
     * @return if approved field in the request is true, returns only approved loans as a ResponseEntity of a Loan list. Else, all of them.
     * @apiNote Endpoint to get the history of loan applications of a customer.
     */
    @ApiOperation(value = "Get all loans of a customer.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetched all loans of a customer successfully", response = List.class, responseContainer = "ResponseEntity"),
            @ApiResponse(code = 404, message = "Not found. Customer by  provided TCKN & Birth date does not exist.")})
    @GetMapping("/history")
    public ResponseEntity<List<Loan>> getLoans(@Valid @RequestBody GetLoansRequestDto request) {
        validate(request.getTckn());
        return ResponseEntity.status(HttpStatus.OK).body(loanService.getByCustomer(request));
    }

    /**
     * @param application as a request body. Fields: tckn, birth_date, assurance.
     * @return ResponseEntity<LoanResponseDto> - Fields: approved, amount. Amount is 0.0 if declined.
     * Values of the returned map is th Boolean value of the approval status of the application.
     * @apiNote Endpoint to apply for loan.
     */
    @ApiOperation(value = "Apply loan with LoanApplicationDto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Loan application is processed successfully", response = LoanResponseDto.class, responseContainer = "ResponseEntity"),
            @ApiResponse(code = 400, message = "Bad request. Application is not valid."),
            @ApiResponse(code = 404, message = "Customer not found.")})
    @PostMapping("/apply")
    public ResponseEntity<LoanResponseDto> applyLoan(@Valid @RequestBody LoanApplicationDto application) {
        validate(application.getTckn());
        return ResponseEntity.status(HttpStatus.OK).body(loanService.applyLoan(application));
    }

    /**
     * @param application as a request body. Fields: id, tckn, birth_date.
     * @return ResponseEntity<String>
     * @apiNote Endpoint to pay a loan.
     */
    @ApiOperation(value = "Pay loan with LoanPaymentApplication")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Paid loan successfully", response = String.class, responseContainer = "ResponseEntity"),
            @ApiResponse(code = 400, message = "Bad request. Application is not valid."),
            @ApiResponse(code = 404, message = "Customer OR Loan not found.")})
    @PutMapping("/pay")
    public ResponseEntity<String> payLoan(@Valid @RequestBody LoanPaymentApplication application) {
        validate(application.getTckn());
        return ResponseEntity.status(HttpStatus.OK).body(loanService.payLoan(application));
    }
}
