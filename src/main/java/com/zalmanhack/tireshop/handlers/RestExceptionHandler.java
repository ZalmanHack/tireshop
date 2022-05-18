package com.zalmanhack.tireshop.handlers;

import com.zalmanhack.tireshop.exceptions.ErrorDetails;
import com.zalmanhack.tireshop.exceptions.ErrorMessage;
import com.zalmanhack.tireshop.exceptions.RecordMaxCountException;
import com.zalmanhack.tireshop.exceptions.RecordNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
    public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(RecordNotFoundException.class)
    public ErrorMessage recordNotFoundException(RecordNotFoundException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.UNPROCESSABLE_ENTITY,
                ex.getMessage(),
                null,
                ((ServletWebRequest) request).getRequest().getRequestURI());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(RecordMaxCountException.class)
    public ErrorMessage recordMaxCountException(RecordMaxCountException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.UNPROCESSABLE_ENTITY,
                ex.getMessage(),
                null,
                ((ServletWebRequest) request).getRequest().getRequestURI());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessage methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        Set<String> errorFields = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getField)
                .collect(Collectors.toSet());
        List<ErrorDetails> errorDetails = errorFields.stream()
                .map(field -> new ErrorDetails(field, ex.getBindingResult().getFieldErrors(field).stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new ErrorMessage(
                HttpStatus.UNPROCESSABLE_ENTITY,
                ex.getMessage(),
                errorDetails,
                ((ServletWebRequest) request).getRequest().getRequestURI());
    }
}
