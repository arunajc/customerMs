package com.mybank.customer.controller;

import com.mybank.customer.exception.AuthenticationException;
import com.mybank.customer.exception.CustomerRegistrationException;
import com.mybank.customer.exception.GeneralException;
import com.mybank.customer.exception.ValidationException;
import com.mybank.customer.model.CustomerDetails;
import com.mybank.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CustomerApiImpl implements CustomerApi{

    @Autowired
    CustomerService customerService;

    @Override
    public ResponseEntity<CustomerDetails> login(@Valid CustomerDetails customerDetails)
            throws AuthenticationException, GeneralException {
        CustomerDetails userDetailsResponse = customerService.login(customerDetails);
        return new ResponseEntity<>(userDetailsResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerDetails> register(@Valid CustomerDetails customerDetails)
            throws ValidationException, CustomerRegistrationException, GeneralException {
        CustomerDetails userDetailsResponse = customerService.register(customerDetails);
        return new ResponseEntity<>(userDetailsResponse, HttpStatus.OK);
    }
}
