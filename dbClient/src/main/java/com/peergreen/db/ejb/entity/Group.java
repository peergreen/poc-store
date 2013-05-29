package com.peergreen.db.ejb.entity;

import java.util.Collection;
import java.util.HashSet;


import javax.persistence.*;

@Entity
public class Group {

	@Id
	private String groupname;
	@ManyToMany(mappedBy="groupSet")
	private Collection<User> users = new HashSet<User>();
	@ManyToMany(mappedBy="groupSet")
	private Collection<Feature> features = new HashSet<Feature>();
	
	
}
