package com.dataqu.carrental.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MESSAGE = "Check the list of errors shown above";
    private static final String CURRENT_DATE_TIME = LocalDateTime.now().toString();

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        ErrorResponse errorResponse = new ErrorResponse(CURRENT_DATE_TIME,status.value(),errors,ex.getMessage(), request.getContextPath());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), headers, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        List<String> errorList = (ex.getCustomMessage() == null) ? ex.getErrorList()
                .stream()
                .map(ErrorMessages::getErrorMessage)
                .collect(Collectors.toList()) : List.of(ex.getCustomMessage());
        ErrorResponse errorResponse = new ErrorResponse(CURRENT_DATE_TIME, status.value(), errorList, MESSAGE, request.getRequestURI());
        return ResponseEntity.status(status).body(errorResponse);
    }
}
