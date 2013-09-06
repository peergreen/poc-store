package com.peergreen.store.db.client.exception;


public class NoEntityFoundException extends Exception {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 4536352061842931662L;

    /**
     * Generic constructor
     */
    public NoEntityFoundException() {

    }

    /** 
     * Build a new NoEntityFound instance with a specific message.
     *
     * @param message detailed exception message
     */
    public NoEntityFoundException(String message) {
        super(message);
    }

    /**
     * Build a new NoEntityFound instance with a specific message.
     *
     * @param cause exception causing this exception
     */
    public NoEntityFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Build a new NoEntityFound instance with a message and a cause.
     *
     * @param message detailed exception message
     * @param cause exception causing this exception
     */
    public NoEntityFoundException(String message, Throwable cause) {
        super(message, cause);
    }     
}
