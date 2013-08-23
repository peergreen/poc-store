package com.peergreen.store.ldap.parser.handler;

import com.peergreen.store.ldap.parser.INodeContext;
import com.peergreen.store.ldap.parser.node.IBinaryNode;
import com.peergreen.store.ldap.parser.node.INaryNode;
import com.peergreen.store.ldap.parser.node.IUnaryNode;
import com.peergreen.store.ldap.parser.node.IValidatorNode;


/**
 * Interface defining call-backs for node creation events.
 * 
 * @param <T> type wrapped on nodes
 */
public interface ILdapHandler {

    /**
     * Method called when all node children have been created.
     * 
     * @param nodeContext node context
     */
    void afterCreatingAllChildren(INodeContext<? extends IValidatorNode<String>> nodeContext);

    /**
     * Method called when an unary node is created.
     * 
     * @param nodeContext node context
     */
    void onUnaryNodeCreation(INodeContext<? extends IUnaryNode> nodeContext);

    /**
     * Method called when a binary node is created.
     * 
     * @param nodeContext node context
     */
    void onBinaryNodeCreation(INodeContext<? extends IBinaryNode> nodeContext);

    /**
     * Method called when an n-ary node is created.
     * 
     * @param nodeContext node context
     */
    void onNaryNodeCreation(INodeContext<? extends INaryNode> nodeContext);
}