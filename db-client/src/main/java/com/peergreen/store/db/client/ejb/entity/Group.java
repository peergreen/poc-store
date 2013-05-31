package com.peergreen.store.db.client.ejb.entity;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.*;

@Entity
public class Group {

    @Id
    private String groupname;
    @ManyToMany(mappedBy="groupSet",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Collection<User> users = new HashSet<User>();
    @ManyToMany(mappedBy="groupSet")
    private Collection<Petal> petals = new HashSet<Petal>();

}
