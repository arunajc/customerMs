package com.mybank.customer.exception;

public class ValidationException extends Exception{

    private static final long serialVersionUID = -5466103685790084580L;
    private String message;

    public enum ValidationError{

        INVALID_REQUEST("Invalid input"),
        LASTNAME_IS_MANDATORY("Invalid input - latName is mandatory"),
        EMAILID_IS_MANDATORY("Invalid input - emailId is mandatory"),
        MOBILENUMBER_IS_MANDATORY("Invalid input - mobileNumber is mandatory");

        final String message;
        ValidationError(String message){
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public ValidationException(String message){
        this.message = message;
    }

    public ValidationException(ValidationError error){
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
