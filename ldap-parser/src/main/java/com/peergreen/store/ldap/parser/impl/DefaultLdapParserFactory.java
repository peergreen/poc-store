package com.peergreen.store.ldap.parser.impl;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import com.peergreen.store.ldap.parser.ILdapParser;
import com.peergreen.store.ldap.parser.ILdapParserFactory;


@Component
@Instantiate
@Provides
public class DefaultLdapParserFactory implements ILdapParserFactory {

    /**
     * Method to retrieve a new instance of ILdapParser.
     */
    @Override
    public ILdapParser newLdapParser() {
        return new DefaultLdapParser();
    }

}