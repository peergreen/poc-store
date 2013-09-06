package com.peergreen.store.db.client.ejb.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Entity Bean representing in the database group of users.
 */
@NamedQueries({
    @NamedQuery(
            name = "Series.findAll",
            query = "select s from Series s"),
            @NamedQuery(
                    name = "GroupByName",
                    query = "select s from Series s where s.groupname = :name")
})
@Entity(name = "Series")
public class Group {

    /**
     * Name of the group.
     */
    @Id
    @Column(name = "name")
    private String groupname;

    /**
     * Set of the users which belongs to the group. The group can have any or
     * many users.
     */
    @ManyToMany
    private Set<User> users;

    /**
     * Set of the petals to which the group have access. It can have access to
     * any or many petals.
     */
    @ManyToMany
    private Set<Petal> petals;

    /**
     * Default constructor.
     */
    public Group() {

    }

    /**
     * Constructs a group with the name given in parameter.
     * @param groupname name of the group to create
     */
    public Group(String groupname) {
        super();
        this.groupname = groupname;
        Set<User> users = new HashSet<User>();
        Set<Petal> petals = new HashSet<Petal>();
        this.setPetals(petals);
        this.setUsers(users);
    }

    /**
     * Method to retrieve the group's name.
     * @return the groupname
     */
    public String getGroupname() {
        return groupname;
    }

    /**
     * Method to retrieve all the users that belongs to this group.
     * @return Set of users of the group
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Method to add users into the group.
     * @param users Set of users to add into the group
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * Method to retrieve all the petals which the users of the group have
     * access.
     *
     * @return Set containing the petals attainable via the group instance
     */
    public Set<Petal> getPetals() {
        return petals;
    }

    /**
     * Method to add petals to the Set of petals which the users of the
     * group have access.
     *
     * @param petals Set of petals to add, for making it attainable
     * via the group instance
     */
    public void setPetals(Set<Petal> petals) {
        this.petals = petals;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return groupname;
    }

}
