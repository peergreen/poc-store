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

	Collection <IPetal> collectPetalsByGroup(IGroup group); 

	IPetal updatePetal(IVendor vendor, String artifactId, 
			String version, String description, ICategory category, 
			Collection<ICapability> capabilities, Collection<IRequirement> requirements);

	void deletePetal(IPetalId petalId); 

	void deletePetal (IVendor vendor, String artifactId, String version);

	IPetal giveAccesToGroup(IGroup group);

	IPetal removeAccesToGroup(IGroup group);

	IPetal addCapability(ICapability capability);

	IPetal removeCapability(ICapability capability);

	IPetal addRequirement(IRequirement requirement);

	IPetal removeRequirement(IRequirement requirement);

}
