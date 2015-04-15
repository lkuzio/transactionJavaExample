package com.acme.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;

	private String password;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="group_id")
	private Group group;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="manager_id")
	private User manager;
	
	@OneToMany(mappedBy="manager", cascade=CascadeType.ALL)
    private Set<User> subordinates = new HashSet<User>();

    public User() {
    }

    public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}
    
    public User(String name, String password, Group group, User manager) {
		super();
		this.name = name;
		this.password = password;
		this.group = group;
		this.manager = manager;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    
	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public Set<User> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(Set<User> subordinates) {
		this.subordinates = subordinates;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password
				+ ", group=" + (group!=null?group.toString():"no group") + ", manager=" + (manager!=null? manager.toString():"not manager") 
				+ ", subordinates count: "+subordinates.size()+"]";
	}
    
    
}
