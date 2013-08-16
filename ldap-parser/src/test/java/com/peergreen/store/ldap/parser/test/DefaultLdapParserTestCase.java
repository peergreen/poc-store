package com.peergreen.store.ldap.parser.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;
import com.peergreen.store.ldap.parser.impl.DefaultLdapParser;


public class DefaultLdapParserTestCase {
    private DefaultLdapParser parser; 
    private String filter;
    
    @BeforeMethod
    public void setUp() {
        parser = new DefaultLdapParser();
    }

    @Test(expectedExceptions = InvalidLdapFormatException.class)
    public void missingInitialOpeningParenthesisTest() throws InvalidLdapFormatException {
        filter = "!(name=jpa))";
        parser.parse(filter);
    }
    
    @Test(expectedExceptions = InvalidLdapFormatException.class)
    public void missingLastClosingParenthesisTest() throws InvalidLdapFormatException {
        filter = "(name=jpa";
        parser.parse(filter);
    }
    
    @Test
    public void validUnaryNodeTest() throws InvalidLdapFormatException {
        filter = "(!(name=jpa))";
        parser.parse(filter);
        
        // TODO: verify
    }
    
    @Test(expectedExceptions = InvalidLdapFormatException.class)
    public void invalidUnaryNodeTest() throws InvalidLdapFormatException {
        filter = "(!(name=jpa)(version=2.1))";
        parser.parse(filter);
    }
    
    @Test
    public void validBinaryNodeTest() throws InvalidLdapFormatException {
        filter = "(name=jpa)";
        parser.parse(filter);
    }
    
    @Test(expectedExceptions = InvalidLdapFormatException.class)
    public void invalidBinaryNodeTest() throws InvalidLdapFormatException {
        filter = "(name^jpa)";
        parser.parse(filter);
    }
    
    @Test
    public void validNaryNodeTest() throws InvalidLdapFormatException {
        filter = "(&(name=jpa)(version=2.1)(namespace=BD))";
        parser.parse(filter);
    }
    
    @Test(expectedExceptions = InvalidLdapFormatException.class)
    public void invalidNaryNodeTest() throws InvalidLdapFormatException {
        filter = "(&(name=jpa))";
        parser.parse(filter);
    }

    @Test
    public void validNaryNodesConjonction() throws InvalidLdapFormatException {
        filter = "(&(name=jpa)(|(version=2.1)(version<=3.4)(namespace=BD)))";
        parser.parse(filter);
    }

    @Test
    public void invalidNaryNodesConjonction() throws InvalidLdapFormatException {
        filter = "(&(name=jpa)(|(version=2.1)(version<=3.4)(namespace=BD)))";
        parser.parse(filter);
    }
    
    @Test(expectedExceptions = InvalidLdapFormatException.class)
    public void invalidOperateurTest() throws InvalidLdapFormatException {
        filter = "(#(name=jpa))";
        parser.parse(filter);
    }
    
    @Test(expectedExceptions = InvalidLdapFormatException.class)
    public void missingOpeningParenthesisTest() throws InvalidLdapFormatException {
        filter = "(|name=jpa)(version=2.1))";
        parser.parse(filter);
    }
    
    @Test(expectedExceptions = InvalidLdapFormatException.class)
    public void missingClosingParenthesisTest() throws InvalidLdapFormatException {
        filter = "(|(name=jpa)(version=2.1)";
        parser.parse(filter);
    }
    
    @Test(expectedExceptions = InvalidLdapFormatException.class)
    public void emptyOperandTest() throws InvalidLdapFormatException {
        filter = "(&(name=jpa)())";
        parser.parse(filter);
    }
    
    @Test
    public void surnumeraryWhitespacesTest() throws InvalidLdapFormatException {
        filter = "(&(name  =     PrintService    )(  version=  4.3))";
        parser.parse(filter);
    }
    
    @Test
    public void composedOperatorTest() throws InvalidLdapFormatException {
        filter = "(name~=tomcat)";
        parser.parse(filter);
    }
}