package com.peergreen.store.db.client.ejb.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IVendor;

@Entity
public class Vendor implements IVendor {

	@Id
	private String vendorName;
	private String vendorDescription;
	@OneToMany(mappedBy="vendor", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private List<IPetal> petals;


	@Override
	public String getVendorName() {
		return vendorName;
	}

	@Override
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}


	@Override
	public String getVendorDescription() {
		return vendorDescription;
	}

	@Override
	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}

	@Override
	public List<IPetal> getPetals() {
		return petals;
	}

	@Override
	public void setPetals(List<IPetal> petals) {
		this.petals = petals;
	}


}
