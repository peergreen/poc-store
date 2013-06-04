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
	public IPetal updatePetal(IVendor vendor, String artifactId,
			String version, String description, ICategory category,
			Collection<ICapability> capabilities,
			Collection<IRequirement> requirements) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePetal(IPetalId petalId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePetal(IVendor vendor, String artifactId, String version) {
		// TODO Auto-generated method stub

	}

	@Override
	public IPetal giveAccesToGroup(IGroup group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPetal removeAccesToGroup(IGroup group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPetal addCapability(ICapability capability) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPetal removeCapability(ICapability capability) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPetal addRequirement(IRequirement requirement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPetal removeRequirement(IRequirement requirement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IGroup> collectGroups(IPetalId petalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IGroup> collectGroups(IVendor vendor, String artifactId,
			String version) {
		// TODO Auto-generated method stub
		return null;
	}



}
