package com.mybank.customer.exception;

public class AuthenticationException extends Exception{

    private static final long serialVersionUID = 5039472124580876105L;
    private String message;

    public enum AuthenticationError{

        AUTHENTICATION_FAILED("Invalid username/password"),
        INACTIVE_CUSTOMER("Customer is not activated");

        final String message;
        AuthenticationError(String message){
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public AuthenticationException(String message){
        this.message = message;
    }

    public AuthenticationException(AuthenticationError error){
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
