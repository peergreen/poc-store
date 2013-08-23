package com.peergreen.store.ldap.parser.exception;

public class InvalidLdapFormatException extends RuntimeException {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -9182121571433771301L;

    /**
     * Generic constructor
     */
    public InvalidLdapFormatException() {

    }

    /** 
     * Build a new InvalidLdapFormatException instance with a specific message
     * 
     * @param message detailed exception message
     */  
    public InvalidLdapFormatException(String message) {  
        super(message); 
    }
    
    /** 
     * Build a new InvalidLdapFormatException instance with a specific message
     *  
     * @param cause exception causing this exception
     */  
    public InvalidLdapFormatException(Throwable cause) {  
        super(cause); 
    }
    
    /** 
     * Build a new InvalidLdapFormatExcepion instance with a message and a cause
     * 
     * @param message detailed exception message
     * @param cause exception causing this exception
     */  
    public InvalidLdapFormatException(String message, Throwable cause) {  
        super(message, cause); 
    }     
}
