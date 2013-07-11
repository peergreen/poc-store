package com.peergreen.store.db.client.ldap.handler.client.jpql.impl;

import java.util.List;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;
import com.peergreen.store.ldap.parser.node.BinaryNode;
import com.peergreen.store.ldap.parser.node.IValidatorNode;
import com.peergreen.store.ldap.parser.node.NaryNode;
import com.peergreen.store.ldap.parser.node.UnaryNode;
import com.peergreen.tree.Node;

/**
 * 
 *JPQL Client for handle NaryNode and generate a piece of JPQL query 
 *
 */
public class JPQLClientNaryNode implements ILdapHandler<String> {
    /**
     * This handler is used when a NaryNode is created so
     * we concatenate all the child with the Narynode's operation 
     * the piece of JPQL corresponding
     * 
     * @param node input tree to transcript on JPQL
     * @return corresponding piece of JPQL query
     */
    @Override
    public String toQueryElement(Node<String> node)throws NullPointerException {
        String queryString = null;

        List<Node<String>> children = node.getChildren();
        int size = children.size();

        ILdapHandler<String> handler;
        IValidatorNode<String> child;

        for(int i =0; i<size-1; i++)
        {
            child = (IValidatorNode<String>) children.get(i);
            handler = child.getHandler();
            if(handler == null){
                throw new NullPointerException("No handler for child of the unaryNode");
            }else{
                queryString = queryString + handler.toQueryElement(child) + " " + node.getData()+ " ";
            }
        }
        child= (IValidatorNode<String>) children.get(size-1);
        handler = child.getHandler();
        queryString = queryString + " " + handler.toQueryElement(child);

        return queryString;
    }

    @Override
    public void onUnaryNodeCreation(UnaryNode<String> node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBinaryNodeCreation(BinaryNode<String> node) {
        // TODO Auto-generated method stub

    }
    
    /**
     * Register this handler on a NaryNode 
     * 
     * @param node The NaryNode created
     */
    @Override
    public void onNaryNodeCreation(NaryNode<String> node) {
        node.setHandler(this);
    }

}
