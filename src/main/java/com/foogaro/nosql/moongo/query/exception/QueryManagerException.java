package com.foogaro.nosql.moongo.query.exception;

/**
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
public class QueryManagerException extends RuntimeException {

    public QueryManagerException() {
        super();
    }

    public QueryManagerException(String message) {
        super(message);
    }

    public QueryManagerException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public QueryManagerException(Throwable throwable) {
        super(throwable);
    }

}
