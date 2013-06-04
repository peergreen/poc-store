package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.ICategory;
import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IRequirement;
import com.peergreen.store.db.client.ejb.entity.api.IVendor;
import com.peergreen.store.db.client.ejb.key.primary.api.IPetalId;

public interface ISessionPetal {

	IPetal addPetal(IPetalId petalId, String description, ICategory category, 
			Collection<ICapability> capabilities, Collection<IRequirement> requirements);

	IPetal addPetal(IVendor vendor, String artifactId, 
			String version, String description, ICategory category, 
			Collection<ICapability> capabilities, Collection<IRequirement> requirements);

	IPetal findPetal(IPetalId petalId);

	IPetal findPetal(IVendor vendor, String artifactId,String version);
	
	Collection<IGroup> collectGroups(IPetal petal); 
	
	Collection<ICapability> collectCapabilities(IPetal petal); 
		
	Collection<IRequirement> collectRequirements(IPetal petal); 

	IPetal updatePetal(IVendor vendor, String artifactId, 
			String version, String description, ICategory category, 
			Collection<ICapability> capabilities, Collection<IRequirement> requirements);

	void deletePetal(IPetal petal); 

	IPetal giveAccesToGroup(IPetal petal, IGroup group);

	IPetal removeAccesToGroup(IPetal petal, IGroup group);

	IPetal addCapability(IPetal petal, ICapability capability);
	
	IPetal removeCapability(IPetal petal, ICapability capability);

	IPetal addRequirement(IPetal petal, IRequirement requirement);

	IPetal removeRequirement(IPetal petal, IRequirement requirement);

}
