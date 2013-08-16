package com.peergreen.store.ldap.parser;

import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;
import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.store.ldap.parser.node.IValidatorNode;

/**
 * Interface providing a method to parse a LDAP filter to a tree.
 * 
 * @author Guillaume Dupraz Canard
 */
public interface ILdapParser {

    /**
     * Method to register handlers for JPQL generation.
     * 
     * @param handler handler to register
     */
    void register(ILdapHandler handler);

    /**
     * Method to parse a LDAP filter to a tree.<br />
     * Automatically check if output tree is valid.
     * 
     * @param filter LDAP filter
     * @return tree constructed from LDAP filter parsing.
     * @throws InvalidLdapFormatException
     */
    IValidatorNode<String> parse(String filter) throws InvalidLdapFormatException;

    /**
     * Method to add a new property to the Map.
     * 
     * @param propClass property class
     * @param prop property
     */
    <Prop> void setProperty(Class<Prop> propClass, Prop property);

    /**
     * Method to retrieve a property from the Map.
     * 
     * @param propClass property class
     * @return property corresponding property, {@literal null} otherwise
     */
    <Prop> Prop getProperty(Class<Prop> propClass);

}