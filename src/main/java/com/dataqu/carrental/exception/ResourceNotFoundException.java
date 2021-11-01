package com.dataqu.carrental.exception;

import java.util.List;

public class ResourceNotFoundException extends RuntimeException {

    private List<ErrorMessages> errorList;
    private String customMessage;

    public ResourceNotFoundException(String customMessage) {
        super(customMessage);
        this.customMessage = customMessage;
    }

    public ResourceNotFoundException(ErrorMessages errorList) {
        super(errorList.getErrorMessage());
        this.errorList = List.of(errorList);
    }

    public List<ErrorMessages> getErrorList() {
        return errorList;
    }

    public String getCustomMessage() {
        return customMessage;
    }
}
