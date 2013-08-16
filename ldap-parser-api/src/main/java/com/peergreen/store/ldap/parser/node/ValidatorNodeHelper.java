package com.peergreen.store.ldap.parser.node;

import java.util.ArrayList;
import java.util.List;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.tree.node.SimpleNode;


public abstract class ValidatorNodeHelper extends SimpleNode<String> implements IValidatorNode<String> {

    private IValidatorNode<String> parentValidator;
    private List<IValidatorNode<String>> childrenValidator;
    private ILdapHandler handler;
    private String jpql;
    
    public ValidatorNodeHelper(String data) {
        super(data);
        parentValidator = null;
        childrenValidator = new ArrayList<>();
        handler = null;
        jpql = "";
    }
    
    /**
     * Method to retrieve parent node, using IValidatorNode<T> format.
     * 
     * @return parent node
     */
    @Override
    public IValidatorNode<String> getParentValidatorNode() {
        return this.parentValidator;
    }

    /**
     * Method to set parent node, using IValidatorNode<T> format.
     * 
     * @param parentNode parent node to set
     */
    @Override
    public void setParentValidatorNode(IValidatorNode<String> parentNode) {
        super.setParent(parentNode);
        this.parentValidator = parentNode;
    }
    
    /**
     * Method to get children nodes, using IValidatorNode<T> format.
     * 
     * @return list of children nodes, using IValidatorNode<T> format.
     */
    @Override
    public List<IValidatorNode<String>> getChildrenValidatorNode() {
        return childrenValidator;
    }
    
    /**
     * Method to retrieve associated handler.
     * 
     * @return associated handler
     */
    @Override
    public ILdapHandler getHandler() {
        return handler;
    }

    /**
     * Method to set associated handler.
     * 
     * @param handler associated hendler to set
     */
    @Override
    public void setHandler(ILdapHandler handler) {
        this.handler = handler;
    }
    
    /**
     * Method to get generated JPQL query for this node.
     * 
     * @return associated handler
     */
    @Override
    public String getJpql() {
        return jpql;
    }

    /**
     * Method to set generated JPQL query for this node.
     * 
     * @param generated query to set
     */
    @Override
    public void setJpql(String jpql) {
        this.jpql = jpql;
    }
}