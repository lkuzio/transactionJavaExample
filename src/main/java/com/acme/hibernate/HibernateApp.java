package com.acme.hibernate;

import com.acme.core.entity.Group;
import com.acme.core.entity.User;

import org.hibernate.Session;


public class HibernateApp {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        Group group = new Group("users");

        session.save(group);
        session.save(new User("jan", "pass", group, null));
       // session.save(new User("adam", "pass", group));

        session.getTransaction().commit();


        HibernateUtil.listOfUsers(session);
        
        
        //Flushmode=Commit examples
        HibernateFlushModeCommit hbm = new HibernateFlushModeCommit();
        hbm.addUser();
        hbm.addUserWithoutCommit();
        hbm.addUserWithRollback();
        hbm.addUsersWithManager();
    }

    
	
}
