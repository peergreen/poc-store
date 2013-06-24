package com.peergreen.store.db.client.ejb.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Entity Bean representing in the database group of users
 */
@NamedQueries({
    @NamedQuery(
            name = "Series.findAll",
            query = "select s from Series s"),
            @NamedQuery(
                    name="GroupByName",
                    query="select s from Series s where s.groupname = :name")
})
@Entity(name="Series")
public class Group {

    @Id
    @Column(name ="name")
    private String groupname;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> users ;

    @ManyToMany
    private Set<Petal> petals ;

    public Group() {
        
    }
    
    public Group(String groupname) {
        super();
        this.groupname = groupname;
        Set<User> users = new HashSet<User>();
        Set<Petal> petals = new HashSet<Petal>();
        this.setPetals(petals);
        this.setUsers(users);
    }

    /**
     * Method to retrieve the group's name
     * 
     * @return the groupname
     */
    public String getGroupname() {
        return groupname;
    }

    /**
     * Method to set the group's name
     * 
     * @param groupname the name to set
     */
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    /**
     * Method to retrieve all the users that belongs to this group
     * 
     * @return Set of users of the group
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Method to add users into the group 
     * 
     * @param users Set of users to add into the group
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * Method to retrieve all the petals which the users of the group have 
     * access
     * 
     * @return Set containing the petals attainable via the group instance
     */
    public Set<Petal> getPetals() {
        return petals;
    }

    /**
     * Method to add petals to the Set of petals which the users of the 
     * group have access
     * 
     * @param petals Set of petals to add, for making it attainable via the group
     * instance
     */
    public void setPetals(Set<Petal> petals) {
        this.petals = petals;
    }

}
