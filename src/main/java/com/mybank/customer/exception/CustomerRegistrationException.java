package com.mybank.customer.exception;

public class CustomerRegistrationException extends Exception{

    private static final long serialVersionUID = -6528138917073327274L;
    private String message;

    public enum CustomerRegistrationError{

        CUSTOMER_ALREADY_EXISTS_EMAIL("Customer already exists - Same emailId found"),
        CUSTOMER_ALREADY_EXISTS_MOBILE("Customer already exists - Same mobileNumber found"),
        USERNAME_ALREADY_TAKEN("Provided username is already taken");

        final String message;
        CustomerRegistrationError(String message){
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public CustomerRegistrationException(String message){
        this.message = message;
    }

    public CustomerRegistrationException(CustomerRegistrationException.CustomerRegistrationError error){
        this.message = error.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
