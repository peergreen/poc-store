package com.peergreen.store.db.client.ejb.impl;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Capability;
import com.peergreen.store.db.client.ejb.entity.Category;
import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.Requirement;
import com.peergreen.store.db.client.ejb.entity.Vendor;
import com.peergreen.store.db.client.ejb.key.primary.PetalId;
import com.peergreen.store.db.client.ejb.session.api.ISessionPetal;


public class DefaultPetal implements ISessionPetal {

	@Override
	public Petal addPetal(PetalId petalId, String description,
			Category category, Collection<Capability> capabilities,
			Collection<Requirement> requirements) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Petal addPetal(Vendor vendor, String artifactId, String version,
			String description, Category category,
			Collection<Capability> capabilities,
			Collection<Requirement> requirements) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Petal findPetal(PetalId petalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Petal findPetal(Vendor vendor, String artifactId, String version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Group> collectGroups(Petal petal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Capability> collectCapabilities(Petal petal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Requirement> collectRequirements(Petal petal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Petal updatePetal(Vendor vendor, String artifactId,
			String version, String description, Category category,
			Collection<Capability> capabilities,
			Collection<Requirement> requirements) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePetal(Petal petal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Petal giveAccesToGroup(Petal petal, Group group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Petal removeAccesToGroup(Petal petal, Group group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Petal addCapability(Petal petal, Capability capability) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Petal removeCapability(Petal petal, Capability capability) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Petal addRequirement(Petal petal, Requirement requirement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Petal removeRequirement(Petal petal, Requirement requirement) {
		// TODO Auto-generated method stub
		return null;
	}



}
