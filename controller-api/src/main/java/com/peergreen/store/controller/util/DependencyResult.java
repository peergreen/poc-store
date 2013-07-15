package com.peergreen.store.controller.util;

import java.util.Map;
import java.util.Set;

import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;

public class DependencyResult {
    private Map<Requirement, Set<Petal>> resolvedRequirements;
    private Set<Requirement> unresolvedRequirements;

    public Map<Requirement, Set<Petal>> addResolvedDependency(Requirement requirement, Set<Petal> providers) {
        resolvedRequirements.put(requirement, providers);
        return resolvedRequirements;
    }
    
    public Map<Requirement, Set<Petal>> removeResolvedDependency(Requirement requirement, Set<Petal> providers) {
        resolvedRequirements.put(requirement, providers);
        return resolvedRequirements;
    }
    
    public Set<Requirement> addUnresolvedRequirement(Requirement requirement) {
        unresolvedRequirements.add(requirement);
        return unresolvedRequirements;
    }
    
    public Set<Requirement> removeUnresolvedRequirement(Requirement requirement) {
        unresolvedRequirements.remove(requirement);
        return unresolvedRequirements;
    }
    
    public Map<Requirement, Set<Petal>> getResolvedRequirements() {
        return resolvedRequirements;
    }
    
    public void setResolvedRequirements(Map<Requirement, Set<Petal>> resolvedRequirements) {
        this.resolvedRequirements = resolvedRequirements;
    }
    
    public Set<Requirement> getUnresolvedRequirements() {
        return unresolvedRequirements;
    }
    
    public void setUnresolvedRequirements(Set<Requirement> unresolvedRequirements) {
        this.unresolvedRequirements = unresolvedRequirements;
    }
}
