package com.foogaro.nosql.moongo.persistence.exception;

/**
 * @author Luigi Fugaro
 * @version 0.0.1
 * @since 0.0.1
 */
public class PersistenceManagerException extends RuntimeException {

    public PersistenceManagerException() {
        super();
    }

    public PersistenceManagerException(String message) {
        super(message);
    }

    public PersistenceManagerException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public PersistenceManagerException(Throwable throwable) {
        super(throwable);
    }

}
