package com.peergreen.store.ldap.parser.element;

import com.peergreen.tree.Node;

public interface IElement {

    /**
     * Method to retrieve node content.
     * 
     * @return node content
     */
    String getContent();
    
    /**
     * Method to verify if element is valid.
     * 
     * @return {@literal true} if node is valid, {@literal false} otherwise
     */
    boolean validate(Node<IElement> node);
    
}
