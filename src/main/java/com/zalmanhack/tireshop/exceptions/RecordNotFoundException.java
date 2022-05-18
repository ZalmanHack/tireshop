package com.zalmanhack.tireshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// В данном проекте аннотация не "ResponseStatus" нужна,
// так как установлен хендлер "RestExceptionHandler", в котором приписано то же самое
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {

    public <T> RecordNotFoundException(Class<T> tClass, Long id) {
        super(String.format("Record type \"%s\" with id %d not found", tClass.getSimpleName(), id));
    }

    public <T> RecordNotFoundException(Class<T> tClass) {
        super(String.format("No records found for type \"%s\"", tClass.getSimpleName()));
    }
}
