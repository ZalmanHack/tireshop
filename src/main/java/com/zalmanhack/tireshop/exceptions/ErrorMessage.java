package com.zalmanhack.tireshop.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
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
