package com.peergreen.store.db.client.ejb.session.api;

import java.util.Collection;

import com.peergreen.store.db.client.ejb.entity.Group;
import com.peergreen.store.db.client.ejb.entity.Petal;
import com.peergreen.store.db.client.ejb.entity.User;

public interface ISessionGroup {

	Group addGroup(String groupName);
	Group findGroup(String groupName);
	Group updateGroup(String groupName);
	void deleteGroup(String groupName);
	Group addUser(User myUser);     
	Group deleteUserbyPseudo(String pseudo);
	Collection<User> collectUsers(String groupName);
	Group addPetal(Petal petal);
	Group deletePetalById(int petalId);
	Collection<Petal> collectPetals(String groupName);

}
