package com.dataqu.carrental.exception;

public enum ErrorMessages {
    RENTAL_LIST_NOT_FOUND(5000, "Cannot find a rental list with the given parameters"),
    CUSTOMER_NOT_FOUND(5001, "Cannot find customer with the given id"),
    COMPANY_NOT_FOUND(5002, "Cannot find company with the given id");

    private final int errorCode;
    private final String errorMessage;

    ErrorMessages(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
