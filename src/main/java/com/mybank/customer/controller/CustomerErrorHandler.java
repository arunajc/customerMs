package com.mybank.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybank.customer.exception.AuthenticationException;
import com.mybank.customer.exception.CustomerRegistrationException;
import com.mybank.customer.exception.GeneralException;
import com.mybank.customer.exception.ValidationException;
import com.mybank.customer.model.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomerErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerErrorHandler.class);

    @Autowired
    ObjectMapper objectMapper;

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ResponseEntity<Error> handleValidationException(
            final ValidationException validationException){
        Error error = new Error("VALIDATION-001",
                validationException.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<Error> handleAuthenticationException(
            final AuthenticationException authenticationException){
        Error error = new Error("AUTHENTICATION-001",
                authenticationException.getMessage());

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GeneralException.class)
    @ResponseBody
    public ResponseEntity<Error> handleGeneralException(
            final GeneralException generalException){
        Error error = new Error("GENERAL-001", generalException.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Error> handleMethodArgumentExceptions(
            MethodArgumentNotValidException ex) {
        LOGGER.warn("Input payload validation failed");
        Map<String, String> validationFailedFields = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationFailedFields.put(fieldName, errorMessage);
        });

        String description = "Failed to get validation failure message";
        try {
            description = objectMapper.writeValueAsString(validationFailedFields);
        } catch (JsonProcessingException e) {
            LOGGER.warn("Could not extract the validation failure reason- ", e);
        }
        LOGGER.warn("Input payload validation failed. Reason(s): {}", description);

        Error error = new Error("VALIDATION-002", description);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerRegistrationException.class)
    @ResponseBody
    public ResponseEntity<Error> handleCustomerRegistrationException(
            final CustomerRegistrationException customerRegistrationException){
        Error error = new Error("CUSTOMERREGISTRATION-001",
                customerRegistrationException.getMessage());

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
