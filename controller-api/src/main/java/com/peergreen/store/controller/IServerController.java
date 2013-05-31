package com.peergreen.store.controller;

import java.util.List;

import com.peergreen.store.db.client.ejb.entity.api.IGroup;
import com.peergreen.store.db.client.ejb.entity.api.ILink;
import com.peergreen.store.db.client.ejb.entity.api.IPetal;
import com.peergreen.store.db.client.ejb.entity.api.IUser;

public interface IServerController {

    /**
     * Method to a link between a remote store and the current one.
     * 
     * @param url path to the remote store
     * @param description link's description
     */
    void addLink(String url, String description);
    
    /**
     * Method to remove a link between a remote store and the current one.
     * 
     * @param linkId link's id
     */
    void removeLink(int linkId);
    
    /**
     * Method to collect all existing links in database.
     * 
     * @return list of all existing links in database
     */
    List<ILink> collectLinks();
    
    /**
     * Method to collect available petals.<br />
     * Retrieve available petals only for user's group.
     * 
     * @return list of available petals
     */
    List<IPetal> collectPetals(); 
    
    /**
     * Method to collect all existing users on database.
     * 
     * @return list of all database's users
     */
    List<IUser> collectUsers();
    
    /**
     * Method to collect all existing groups on database.
     * 
     * @return list of all database's groups
     */
    List<IGroup> collectGroups();
    
}
