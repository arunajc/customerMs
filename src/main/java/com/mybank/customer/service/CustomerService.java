package com.mybank.customer.service;

import com.mybank.customer.exception.AuthenticationException;
import com.mybank.customer.exception.CustomerRegistrationException;
import com.mybank.customer.exception.GeneralException;
import com.mybank.customer.exception.ValidationException;
import com.mybank.customer.model.CustomerDetails;

public interface CustomerService {

    CustomerDetails login(CustomerDetails customerDetails)
            throws AuthenticationException, GeneralException;
    CustomerDetails register(CustomerDetails customerDetails)
            throws CustomerRegistrationException, GeneralException, ValidationException;
}
