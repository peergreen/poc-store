package com.peergreen.db.ejb.session.impl;

import java.util.Collection;

import javax.ejb.Stateless;

import com.peergreen.db.ejb.entity.Category;
import com.peergreen.db.ejb.entity.Feature;
import com.peergreen.db.ejb.session.ICategory;

@Stateless
public class DefaultCategory implements ICategory{

	public Category addCategory(int categoryId, String name, String version) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteCategory(int categoryId) {
		// TODO Auto-generated method stub
		
	}

	public Category findCategory(int categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Feature> collectFeatures() {
		// TODO Auto-generated method stub
		return null;
	}

	public Category addFeature(Feature feature) {
		// TODO Auto-generated method stub
		return null;
	}

	public Category removeFeature(Feature feature) {
		// TODO Auto-generated method stub
		return null;
	}

}
