package com.acme.hibernate;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.acme.core.entity.User;

public class HibernateFlushModeManual {

	public void addUser() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.setFlushMode(FlushMode.MANUAL);
		Transaction tx = session.beginTransaction();
		User user = new User("ManualUserWithCommit", "asd", null, null);
		session.save(user);
		HibernateUtil.listOfUsers(session);
		tx.commit();
		HibernateUtil.listOfUsers(session);
		session.flush();
		session.close();
	}

	public void addUserWithoutCommit() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.setFlushMode(FlushMode.MANUAL);
		User user = new User("ManualUserWithoutCommit", "asd", null, null);
		HibernateUtil.listOfUsers(session);
		session.save(user);
		session.flush();
		HibernateUtil.listOfUsers(session);
		session.close();
	}

	public void addUserWithRollback() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.setFlushMode(FlushMode.MANUAL);
		Transaction tx = session.beginTransaction();
		User user = new User("ManualUserWithRollback", "asd", null, null);
		HibernateUtil.listOfUsers(session);
		session.save(user);
		HibernateUtil.listOfUsers(session);
		tx.rollback();
		session.flush();
		HibernateUtil.listOfUsers(session);
		session.close();
	}

	public void addUsersWithManager() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.setFlushMode(FlushMode.MANUAL);
		Transaction tx = session.beginTransaction();
		User manager = new User("ManualManager", "Pa$sw0rd", null, null);
		User user = new User("ManualFirstSlave", "asd", null, manager);
		User user2 = new User("ManualSecondSlave", "abc", null, manager);
		session.save(manager);
		session.save(user);
		session.save(user2);
		HibernateUtil.listOfUsers(session);
		session.flush();
		tx.commit();
		HibernateUtil.listOfUsers(session);
		session.close();
	}
	
	public void addUsersWithManagerRollback() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Session session2 = HibernateUtil.getSessionFactory().openSession();
		session.setFlushMode(FlushMode.MANUAL);
		session2.setFlushMode(FlushMode.MANUAL);
		
		Transaction tx = session.beginTransaction();
		Transaction tx2 = session2.beginTransaction();
		
		User manager = new User("ManualRBManager", "Pa$sw0rd", null, null);
		
		
		session2.save(manager);		
		session2.flush();
		tx2.commit();
		session2.close();
		
		User user = new User("ManualRBFirstSlave", "asd", null, manager);
		User user2 = new User("ManualRBSecondSlave", "abc", null, manager);
		session.save(user);
		session.save(user2);	
		HibernateUtil.listOfUsers(session);
		
		session.flush();
		tx.commit();
		session.close();
		
	}
}
