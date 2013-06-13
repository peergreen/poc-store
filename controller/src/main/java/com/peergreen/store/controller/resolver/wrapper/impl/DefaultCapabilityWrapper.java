package com.peergreen.store.controller.resolver.wrapper.impl;

import java.util.Map;

import org.osgi.resource.Capability;
import org.osgi.resource.Resource;

public class DefaultCapabilityWrapper implements Capability {
    
    private String capabilityName;
    private String namespace;
    private Map<String, Object> properties;
    
    @Override
    public String getNamespace() {
        return this.namespace;
    }

    @Override
    public Map<String, String> getDirectives() {
        // no directive for Capability
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return properties;
    }

    @Override
    public Resource getResource() {
        return (Resource) this;
    }

}
