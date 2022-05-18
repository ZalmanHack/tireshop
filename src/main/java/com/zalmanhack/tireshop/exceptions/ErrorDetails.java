package com.zalmanhack.tireshop.exceptions;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.util.List;

@Data
public class ErrorDetails {
    private String field;
    private List<String> errors;

    public ErrorDetails(String field, List<String> errors) {
        this.field = field;
        this.errors = errors;
    }
}
