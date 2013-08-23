package com.peergreen.store.ldap.parser.node;



public interface IUnaryNode extends IValidatorNode<String> {

    /**
     * Method to retrieve node child.
     * 
     * @return child
     */
    public IValidatorNode<String> getChild();

}