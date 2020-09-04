package com.mybank.customer.controller;

import com.mybank.customer.exception.AuthenticationException;
import com.mybank.customer.exception.CustomerRegistrationException;
import com.mybank.customer.exception.GeneralException;
import com.mybank.customer.exception.ValidationException;
import com.mybank.customer.model.CustomerDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Api(value = "customer API")
@RequestMapping("/customer")
public interface CustomerApi {

    @ApiOperation(value="Customer login", nickname = "login", tags = {"customerMs"})
    @ApiResponses(value = {
            @ApiResponse(code=400, message = "Invalid Input"),
            @ApiResponse(code=400, message = "Invalid input - UserName is mandatory"),
            @ApiResponse(code=400, message = "Invalid input - Password is mandatory"),
            @ApiResponse(code=401, message = "Unauthorized"),
            @ApiResponse(code=401, message = "Inactive Customer"),
            @ApiResponse(code=500, message = "Internal Server Error"),
            @ApiResponse(code=200, message = "Success")
    })
    @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
    ResponseEntity<CustomerDetails> login(
            @Valid @RequestBody CustomerDetails customerDetails
    ) throws AuthenticationException, GeneralException;

    @ApiOperation(value="create a new customer", nickname = "register", tags = {"customerMs"})
    @ApiResponses(value = {
            @ApiResponse(code=409, message = "Customer already exists - Same emailId found"),
            @ApiResponse(code=409, message = "Customer already exists - Same mobileNumber found"),
            @ApiResponse(code=409, message = "Provided username is already taken"),
            @ApiResponse(code=500, message = "Internal Server Error"),
            @ApiResponse(code=200, message = "Success")
    })
    @PostMapping(value = "/register", produces = "application/json", consumes = "application/json")
    ResponseEntity<CustomerDetails> register(
            @Valid @RequestBody CustomerDetails customerDetails
    ) throws ValidationException, CustomerRegistrationException, GeneralException;

}
