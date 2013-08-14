package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import com.peergreen.store.ldap.parser.INodeContext;
import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.store.ldap.parser.node.UnaryNode;


/**
 * JPQL Client to handle UnaryNode and generate a piece of JPQL query.
 */
public class JPQLClientUnaryNode implements ILdapHandler {
    private UnaryNode node;
    private String alias;
    private String mapAlias;
    
    public JPQLClientUnaryNode(String alias, String mapAlias) {
        this.alias = alias;
        this.mapAlias = mapAlias;
    }
    
    /**
     * Method to generate the piece of JPQL for the node.
     * 
     * @return corresponding piece of JPQL query or {@literal empty String} if operator not supported.
     */
    @Override
    public String toQueryElement() {
        String query = "";
        if (node.getData().equals("!")) {
            query += "NOT " + node.getChild().getHandler().toQueryElement();
        }
        node.setJpql(query);
        
        return query;
    }
    
    /**
     * Register this handler on an UnaryNode 
     * 
     * @param node The unaryNode created
     */
    @Override
    public void onUnaryNodeCreation(INodeContext<String> nodeContext) {
        this.node = nodeContext.getProperty(UnaryNode.class);
        this.node.setHandler(this);
    }

    @Override
    public void onBinaryNodeCreation(INodeContext<String> nodeContext) {
        // This is an UnaryNode, nothing to do on BinaryNode events.
    }

    @Override
    public void onNaryNodeCreation(INodeContext<String> nodeContext) {
     // This is an UnaryNode, nothing to do on NaryNode events.
    }
}