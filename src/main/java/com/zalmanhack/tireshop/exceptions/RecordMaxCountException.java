package com.zalmanhack.tireshop.exceptions;


public class RecordMaxCountException extends RuntimeException{
    public <T> RecordMaxCountException(Class<T> tClass, long maxCount) {
        super(String.format("The maximum number of \"%s\" records is %d", tClass.getSimpleName(), maxCount));
    }
}
