package com.peergreen.store.db.client.handler.ldaptree.impl;

import com.peergreen.store.db.client.handler.ldaptree.api.ILdapHandler;
import com.peergreen.store.ldap.parser.Element;
import com.peergreen.tree.Node;

public class AndHandler implements ILdapHandler {

    @Override
    public String toQueryElement(Node<Element> node) {
       return null;
    }

}
