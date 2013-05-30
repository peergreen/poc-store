package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;

/**
 * Interface defining an entity bean representing a group of users
 */
public interface Group {

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
    public List<User> getUsers();


    /**
     * @param users the users to set
     */
    public void setUsers(List<User> users);


    /**
     * @return the petals
     */
    public List<Petal> getPetals();


    /**
     * @param petals the petals to set
     */
    public void setPetals(List<Petal> petals);


}
