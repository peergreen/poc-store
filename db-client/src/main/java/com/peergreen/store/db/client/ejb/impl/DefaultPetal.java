package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.api.ICapability;
import com.peergreen.store.db.client.ejb.entity.api.ICategory;
import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IRequirement;
import com.peergreen.store.db.client.ejb.entity.api.IVendor;
import com.peergreen.store.db.client.ejb.key.primary.api.IPetalId;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;


public class DefaultPetal implements ISessionPetal {

	@Override
	public IPetal addPetal(IPetalId petalId, String description,
			ICategory category, Collection<ICapability> capabilities,
			Collection<IRequirement> requirements) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPetal addPetal(IVendor vendor, String artifactId, String version,
			String description, ICategory category,
			Collection<ICapability> capabilities,
			Collection<IRequirement> requirements) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPetal findPetal(IPetalId petalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPetal findPetal(IVendor vendor, String artifactId, String version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IGroup> collectGroups(IPetal petal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ICapability> collectCapabilities(IPetal petal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IRequirement> collectRequirements(IPetal petal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPetal updatePetal(IVendor vendor, String artifactId,
			String version, String description, ICategory category,
			Collection<ICapability> capabilities,
			Collection<IRequirement> requirements) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePetal(IPetal petal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IPetal giveAccesToGroup(IPetal petal, IGroup group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPetal removeAccesToGroup(IPetal petal, IGroup group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPetal addCapability(IPetal petal, ICapability capability) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPetal removeCapability(IPetal petal, ICapability capability) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPetal addRequirement(IPetal petal, IRequirement requirement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPetal removeRequirement(IPetal petal, IRequirement requirement) {
		// TODO Auto-generated method stub
		return null;
	}



}
