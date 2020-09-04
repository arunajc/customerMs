package com.mybank.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybank.customer.exception.AuthenticationException;
import com.mybank.customer.exception.CustomerRegistrationException;
import com.mybank.customer.exception.GeneralException;
import com.mybank.customer.exception.ValidationException;
import com.mybank.customer.model.CustomerDetails;
import com.mybank.customer.model.Error;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

public class CustomerErrorHandlerTest {

    CustomerErrorHandler customerErrorHandler = new CustomerErrorHandler();

    ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        customerErrorHandler.objectMapper = objectMapper;
    }

    @Test
    public void handleValidationException_EMAILID_IS_MANDATORY() {

        ValidationException validationException = new ValidationException(ValidationException.ValidationError.EMAILID_IS_MANDATORY);
        Object response = customerErrorHandler.handleValidationException(validationException);

        assertThat(response, instanceOf(ResponseEntity.class));
        assertEquals(HttpStatus.BAD_REQUEST, ((ResponseEntity) response).getStatusCode());
        assertThat(((ResponseEntity) response).getBody(), instanceOf(Error.class));
        Error error = (Error) ((ResponseEntity) response).getBody();
        assertEquals("VALIDATION-001", error.getCode());
        assertEquals("Invalid input - emailId is mandatory", error.getDescription());
    }

    @Test
    public void handleValidationException_MOBILENUMBER_IS_MANDATORY() {

        ValidationException validationException = new ValidationException(ValidationException.ValidationError.MOBILENUMBER_IS_MANDATORY);
        Object response = customerErrorHandler.handleValidationException(validationException);

        assertThat(response, instanceOf(ResponseEntity.class));
        assertEquals(HttpStatus.BAD_REQUEST, ((ResponseEntity) response).getStatusCode());
        assertThat(((ResponseEntity) response).getBody(), instanceOf(Error.class));
        Error error = (Error) ((ResponseEntity) response).getBody();
        assertEquals("VALIDATION-001", error.getCode());
        assertEquals("Invalid input - mobileNumber is mandatory", error.getDescription());
    }

    @Test
    public void handleValidationException_LASTNAME_IS_MANDATORY() {

        ValidationException validationException = new ValidationException(ValidationException.ValidationError.LASTNAME_IS_MANDATORY);
        Object response = customerErrorHandler.handleValidationException(validationException);

        assertThat(response, instanceOf(ResponseEntity.class));
        assertEquals(HttpStatus.BAD_REQUEST, ((ResponseEntity) response).getStatusCode());
        assertThat(((ResponseEntity) response).getBody(), instanceOf(Error.class));
        Error error = (Error) ((ResponseEntity) response).getBody();
        assertEquals("VALIDATION-001", error.getCode());
        assertEquals("Invalid input - latName is mandatory", error.getDescription());
    }

    @Test
    public void handleValidationException_success() {

        ValidationException validationException = new ValidationException(ValidationException.ValidationError.INVALID_REQUEST);
        Object response = customerErrorHandler.handleValidationException(validationException);

        assertThat(response, instanceOf(ResponseEntity.class));
        assertEquals(HttpStatus.BAD_REQUEST, ((ResponseEntity) response).getStatusCode());
        assertThat(((ResponseEntity) response).getBody(), instanceOf(Error.class));
        Error error = (Error) ((ResponseEntity) response).getBody();
        assertEquals("VALIDATION-001", error.getCode());
        assertEquals("Invalid input", error.getDescription());
    }

    @Test
    public void handleAuthenticationException_AUTHENTICATION_FAILED() {

        AuthenticationException authenticationException = new AuthenticationException(AuthenticationException.AuthenticationError.AUTHENTICATION_FAILED);
        Object response = customerErrorHandler.handleAuthenticationException(authenticationException);

        assertThat(response, instanceOf(ResponseEntity.class));
        assertEquals(HttpStatus.CONFLICT, ((ResponseEntity) response).getStatusCode());
        assertThat(((ResponseEntity) response).getBody(), instanceOf(Error.class));
        Error error = (Error) ((ResponseEntity) response).getBody();
        assertEquals("AUTHENTICATION-001", error.getCode());
        assertEquals("Invalid username/password", error.getDescription());
    }

    @Test
    public void handleAuthenticationException_INACTIVE_CUSTOMER() {

        AuthenticationException authenticationException = new AuthenticationException(AuthenticationException.AuthenticationError.INACTIVE_CUSTOMER);
        Object response = customerErrorHandler.handleAuthenticationException(authenticationException);

        assertThat(response, instanceOf(ResponseEntity.class));
        assertEquals(HttpStatus.CONFLICT, ((ResponseEntity) response).getStatusCode());
        assertThat(((ResponseEntity) response).getBody(), instanceOf(Error.class));
        Error error = (Error) ((ResponseEntity) response).getBody();
        assertEquals("AUTHENTICATION-001", error.getCode());
        assertEquals("Customer is not activated", error.getDescription());
    }

    @Test
    public void handleGeneralException_UNEXPECTED_ERROR() {

        GeneralException generalException = new GeneralException(GeneralException.GeneralError.UNEXPECTED_ERROR);
        Object response = customerErrorHandler.handleGeneralException(generalException);

        assertThat(response, instanceOf(ResponseEntity.class));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ((ResponseEntity) response).getStatusCode());
        assertThat(((ResponseEntity) response).getBody(), instanceOf(Error.class));
        Error error = (Error) ((ResponseEntity) response).getBody();
        assertEquals("GENERAL-001", error.getCode());
        assertEquals("Unexpected error occured", error.getDescription());
    }

    @Test
    public void handleCustomerRegistrationException_CUSTOMER_ALREADY_EXISTS_EMAIL() {

        CustomerRegistrationException customerRegistrationException = new CustomerRegistrationException(
                CustomerRegistrationException.CustomerRegistrationError.CUSTOMER_ALREADY_EXISTS_EMAIL);
        Object response = customerErrorHandler.handleCustomerRegistrationException(customerRegistrationException);

        assertThat(response, instanceOf(ResponseEntity.class));
        assertEquals(HttpStatus.CONFLICT, ((ResponseEntity) response).getStatusCode());
        assertThat(((ResponseEntity) response).getBody(), instanceOf(Error.class));
        Error error = (Error) ((ResponseEntity) response).getBody();
        assertEquals("CUSTOMERREGISTRATION-001", error.getCode());
        assertEquals("Customer already exists - Same emailId found", error.getDescription());
    }

    @Test
    public void handleCustomerRegistrationException_CUSTOMER_ALREADY_EXISTS_MOBILE() {

        CustomerRegistrationException customerRegistrationException = new CustomerRegistrationException(
                CustomerRegistrationException.CustomerRegistrationError.CUSTOMER_ALREADY_EXISTS_MOBILE);
        Object response = customerErrorHandler.handleCustomerRegistrationException(customerRegistrationException);

        assertThat(response, instanceOf(ResponseEntity.class));
        assertEquals(HttpStatus.CONFLICT, ((ResponseEntity) response).getStatusCode());
        assertThat(((ResponseEntity) response).getBody(), instanceOf(Error.class));
        Error error = (Error) ((ResponseEntity) response).getBody();
        assertEquals("CUSTOMERREGISTRATION-001", error.getCode());
        assertEquals("Customer already exists - Same mobileNumber found", error.getDescription());
    }

    @Test
    public void handleCustomerRegistrationException_USERNAME_ALREADY_TAKEN() {

        CustomerRegistrationException customerRegistrationException = new CustomerRegistrationException(
                CustomerRegistrationException.CustomerRegistrationError.USERNAME_ALREADY_TAKEN);
        Object response = customerErrorHandler.handleCustomerRegistrationException(customerRegistrationException);

        assertThat(response, instanceOf(ResponseEntity.class));
        assertEquals(HttpStatus.CONFLICT, ((ResponseEntity) response).getStatusCode());
        assertThat(((ResponseEntity) response).getBody(), instanceOf(Error.class));
        Error error = (Error) ((ResponseEntity) response).getBody();
        assertEquals("CUSTOMERREGISTRATION-001", error.getCode());
        assertEquals("Provided username is already taken", error.getDescription());
    }

    @Test
    public void handleMethodArgumentExceptions_many() throws NoSuchMethodException {

        CustomerDetails customerDetails = new CustomerDetails();

        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(customerDetails, "customerDetails");
        errors.rejectValue("userName", "invalid", "invalid username");
        MethodParameter parameter = new MethodParameter(CustomerDetails.class.getMethod("getUserName"), -1);
        MethodArgumentNotValidException methodArgumentNotValidException = new MethodArgumentNotValidException(parameter, errors);

        Object response = customerErrorHandler.handleMethodArgumentExceptions(methodArgumentNotValidException);

        assertThat(response, instanceOf(ResponseEntity.class));
        assertEquals(HttpStatus.BAD_REQUEST, ((ResponseEntity) response).getStatusCode());
        assertThat(((ResponseEntity) response).getBody(), instanceOf(Error.class));
        Error error = (Error) ((ResponseEntity) response).getBody();
        assertEquals("VALIDATION-002", error.getCode());
        assertEquals("{\"userName\":\"invalid username\"}", error.getDescription());
    }
}
