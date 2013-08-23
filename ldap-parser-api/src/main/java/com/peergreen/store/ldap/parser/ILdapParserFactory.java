package com.peergreen.store.ldap.parser;

public interface ILdapParserFactory {

    /**
     * Method to retrieve a new instance of ILdapParser.
     */
    ILdapParser newLdapParser();
    
}
