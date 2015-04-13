package com.acme.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;

@Entity
@Table(name="groups")
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;

	@OneToMany
    private Set<User> users = new HashSet<User>();

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
