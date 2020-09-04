package com.mybank.customer.exception;

public class GeneralException extends Exception{

    private static final long serialVersionUID = 797769105419815258L;
    private String message;

    public enum GeneralError{

        UNEXPECTED_ERROR("Unexpected error occured");

        final String message;
        GeneralError(String message){
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public GeneralException(String message){
        this.message = message;
    }

    public GeneralException(GeneralError generalError){
        this.message = generalError.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
