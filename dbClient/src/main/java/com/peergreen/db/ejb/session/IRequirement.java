package com.peergreen.db.ejb.session;

import java.util.Collection;

import com.peergreen.db.ejb.entity.Feature;
import com.peergreen.db.ejb.entity.Requirement;

public interface IRequirement {
	Requirement addRequirement( String namespace, Collection<String> properties);
	void deleteRequirement (int requirementId);
	Requirement findRequirement (int requirementId);
	Collection<Feature> collectFeatures();
	Requirement addFeature(Feature feature);
	Requirement removeFeature (Feature feature);
}
