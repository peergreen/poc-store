package com.peergreen.store.controller.resolver.wrapper.impl;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Constants;
import org.osgi.resource.Requirement;
import org.osgi.resource.Resource;

import com.peergreen.store.controller.resolver.wrapper.IRequirementWrapper;

public class DefaultRequirementWrapper implements IRequirementWrapper {

    private int requirementId;
    private String requirementName;
    private String namespace;
    Map<String, String> directives = new HashMap<>();
    private String filter;

    /**
     * Method to init OSGi like requirement with Requirement instance.
     * 
     * @param requirement to use for init OSGi Requirement
     */
    @Override
    public void initFromRequirement(com.peergreen.store.db.client.ejb.entity.Requirement requirement) {
        this.requirementId = requirement.getRequirementId();
        this.requirementName = requirement.getRequirementName();
//        this.namespace = requirement.getNamespace();
        setFilter(requirement.getFilter());
    }
    
    @Override
    public String getNamespace() {
        return this.namespace;
    }

    @Override
    public Map<String, String> getDirectives() {
        return this.directives;
    }

    @Override
    public Map<String, Object> getAttributes() {
        // no attribute for Requirement
        return null;
    }

    @Override
    public Resource getResource() {
        return (Resource) this;
    }
    
    public void setFilter(String filter) {
        this.filter = filter;
        directives.put(Constants.FILTER_DIRECTIVE, filter);
    }

}
