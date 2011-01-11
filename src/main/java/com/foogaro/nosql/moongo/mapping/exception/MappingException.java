package com.foogaro.nosql.moongo.mapping.exception;

/**
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
public class MappingException extends RuntimeException {

    public MappingException() {
        super();
    }

    public MappingException(String message) {
        super(message);
    }

    public MappingException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MappingException(Throwable throwable) {
        super(throwable);
    }

}
