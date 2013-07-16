package com.peergreen.store.db.client.exception;


public class EntityAlreadyExistsException extends Exception {
    
    /**
     * Generated UID
     */
    private static final long serialVersionUID = 4618460862134904358L;

    /**
     * Generic constructor
     */
    public EntityAlreadyExistsException() {

    }

    /** 
     * Build a new EntityAlreadyExists instance with a specific message.
     * 
     * @param message detailed exception message
     */  
    public EntityAlreadyExistsException(String message) {  
        super(message); 
    }
    
    /** 
     * Build a new EntityAlreadyExists instance with a specific message.
     *  
     * @param cause exception causing this exception
     */  
    public EntityAlreadyExistsException(Throwable cause) {  
        super(cause); 
    }
    
    /** 
     * Build a new EntityAlreadyExists instance with a message and a cause.
     * 
     * @param message detailed exception message
     * @param cause exception causing this exception
     */  
    public EntityAlreadyExistsException(String message, Throwable cause) {  
        super(message, cause); 
    }     
}
