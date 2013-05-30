package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;

/**
 * Interface defining an entity bean representing a group of users
 */
public interface IGroup {

    /**
     * @return the groupname
     */
    public String getGroupname() ;


    /**
     * @param groupname the groupname to set
     */
    public void setGroupname(String groupname);


    /**
     * @return the users
     */
    public List<IUser> getUsers();


    /**
     * @param users the users to set
     */
    public void setUsers(List<IUser> users);


    /**
     * @return the petals
     */
    public List<IPetal> getPetals();


    /**
     * @param petals the petals to set
     */
    public void setPetals(List<IPetal> petals);


}
