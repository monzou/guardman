package com.github.monzou.guardman.exception;

/**
 * GuardManRuntimeException
 */
@SuppressWarnings("serial")
public class GuardManRuntimeException extends RuntimeException {

    public GuardManRuntimeException(String message) {
        super(message);
    }

    public GuardManRuntimeException(Throwable cause) {
        super(cause);
    }

    public GuardManRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
