package com.foogaro.nosql.moongo.exception;

/**
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
public class MoongoException extends RuntimeException {

    public MoongoException() {
        super();
    }

    public MoongoException(String message) {
        super(message);
    }

    public MoongoException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MoongoException(Throwable throwable) {
        super(throwable);
    }

}
