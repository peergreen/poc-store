package com.peergreen.store.db.client.ejb.entity.api;

import java.util.List;

/**
 * Interface defining an entity bean representing a group of users
 */
public interface IGroup {

    /**
     * @return the groupname
     */
    String getGroupname() ;


    /**
     * @param groupname the groupname to set
     */
    void setGroupname(String groupname);


    /**
     * @return the users
     */
    List<IUser> getUsers();


    /**
     * @param users the users to set
     */
    void setUsers(List<IUser> users);


    /**
     * @return the petals
     */
    List<IPetal> getPetals();


    /**
     * @param petals the petals to set
     */
    void setPetals(List<IPetal> petals);


}
