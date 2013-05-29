package com.peergreen.db.ejb.session;

import java.util.Collection;

import com.peergreen.db.ejb.entity.Category;
import com.peergreen.db.ejb.entity.Feature;

public interface ICategory {

	Category addCategory(int categoryId, String name, String version);
	void deleteCategory(int categoryId);
	Category findCategory(int categoryId);
	Collection<Feature> collectFeatures();
	Category addFeature(Feature feature);
	Category removeFeature (Feature feature);
}
