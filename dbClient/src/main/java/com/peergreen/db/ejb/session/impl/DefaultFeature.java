package com.peergreen.db.ejb.session.impl;

import java.util.Collection;

import com.peergreen.db.ejb.entity.Capability;
import com.peergreen.db.ejb.entity.Category;
import com.peergreen.db.ejb.entity.Feature;
import com.peergreen.db.ejb.entity.Group;
import com.peergreen.db.ejb.session.IFeature;
import com.peergreen.db.ejb.session.IRequirement;

public class DefaultFeature implements IFeature {

	public Feature addFeature(int featureId, String groupId, String artifactId,
			String version, String description, Category category,
			Collection<Capability> capabilities,
			Collection<IRequirement> requirements) {
		// TODO Auto-generated method stub
		return null;
	}

	public Feature findFeature(int featureId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Feature> collectFeaturesByGroup(Group group) {
		// TODO Auto-generated method stub
		return null;
	}

	public Feature updateFeature(int featureId, String groupId,
			String artifactId, String version, String description,
			Category category, Collection<Capability> capabilities,
			Collection<IRequirement> requirements) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteFeature(int featureId) {
		// TODO Auto-generated method stub

	}

	public Feature giveAccesToGroup(Group group) {
		// TODO Auto-generated method stub
		return null;
	}

	public Feature removeAccesToGroup(Group group) {
		// TODO Auto-generated method stub
		return null;
	}

	public Feature addCapability(Capability capability) {
		// TODO Auto-generated method stub
		return null;
	}

	public Feature removeCapability(Capability capability) {
		// TODO Auto-generated method stub
		return null;
	}

	public Feature addRequirement(IRequirement requirement) {
		// TODO Auto-generated method stub
		return null;
	}

	public Feature removeRequirement(IRequirement requirement) {
		// TODO Auto-generated method stub
		return null;
	}

}
