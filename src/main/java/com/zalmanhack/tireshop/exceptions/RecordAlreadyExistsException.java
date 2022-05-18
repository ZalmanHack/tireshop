package com.zalmanhack.tireshop.exceptions;

public class RecordAlreadyExistsException extends RuntimeException {
    public <T> RecordAlreadyExistsException(Class<T> tClass, Long id) {
        super(String.format("For the entity \"%s\", a record with the Id %d already exists", tClass.getSimpleName(), id));
    }
}
