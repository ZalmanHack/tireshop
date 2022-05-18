package com.zalmanhack.tireshop.exceptions;

import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@ToString(includeFieldNames = true)
public class ErrorMessage {
    private String status;
    private int code;
    private LocalDateTime timestamp;
    private String message;
    private List<ErrorDetails> errors;
    private String path;

    public ErrorMessage(HttpStatus status, String message, List<ErrorDetails> errors, String path) {
        this.status = status.getReasonPhrase();
        this.code = status.value();
        this.message = message;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }
}
