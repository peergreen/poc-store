package com.peergreen.store.db.client.ejb.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IVendor;

@Entity
public class Vendor implements IVendor {
	
	@Id
	private String vendorName;
	private String vendorDescription;
	private List<Petal> petals;


	@Override
	public String getVendorDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVendorDescription(String vendorDescription) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<IPetal> getPetals() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPetals(List<IPetal> petals) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getVendorName() {
		// TODO Auto-generated method stub
		return null;
	}

}