package com.peergreen.store.ldap.parser.element;

import com.peergreen.store.ldap.parser.handler.ILdapHandler;


public class LdapElement {
    private String filter;
    private ILdapHandler handler;
    
    public LdapElement() {
        filter = "";
        handler = null;
    }
    
    public String getFilter() {
        return filter;
    }
    
    public void setFilter(String filter) {
        this.filter = filter;
    }
    
    public ILdapHandler getHandler() {
        return this.handler;
    }
    
    public void setHandler(ILdapHandler handler) {
        this.handler = handler;
    }
}