package com.acme.core.entity;

import javax.persistence.*;


@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;

	private String password;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_id")
	private Group group;

    public User() {
    }

    public User(String name, String password, Group group) {
        this.name = name;
        this.password = password;
        this.group = group;
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
}
