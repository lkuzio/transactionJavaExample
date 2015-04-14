package com.acme.hibernate;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.acme.core.entity.User;

public class HibernateFlushModeCommit {

	// The Session is flushed when Transaction.commit() is called.

	public void addUser() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.setFlushMode(FlushMode.COMMIT);
		Transaction tx = session.beginTransaction();
		User user = new User("UserWithCommit", "asd", null, null);
		session.save(user);
		HibernateUtil.checkNumberOfUsers(session);
		tx.commit();
		HibernateUtil.checkNumberOfUsers(session);
		session.close();
	}

	public void addUserWithoutCommit() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.setFlushMode(FlushMode.COMMIT);
		User user = new User("UserWithoutCommit", "asd", null, null);
		HibernateUtil.checkNumberOfUsers(session);
		session.save(user);
		HibernateUtil.checkNumberOfUsers(session);
		session.close();
	}

	public void addUserWithRollback() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.setFlushMode(FlushMode.COMMIT);
		Transaction tx = session.beginTransaction();
		User user = new User("UserWithRollback", "asd", null, null);
		HibernateUtil.checkNumberOfUsers(session);
		session.save(user);
		HibernateUtil.checkNumberOfUsers(session);
		tx.rollback();
		HibernateUtil.checkNumberOfUsers(session);
		session.close();
	}

	public void addUsersWithManager() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.setFlushMode(FlushMode.COMMIT);
		Transaction tx = session.beginTransaction();
		User manager = new User("Manager", "Pa$sw0rd", null, null);
		User user = new User("FirstSlave", "asd", null, manager);
		User user2 = new User("SecondSlave", "abc", null, manager);
		session.save(manager);
		session.save(user);
		session.save(user2);
		HibernateUtil.checkNumberOfUsers(session);
		tx.commit();
		HibernateUtil.checkNumberOfUsers(session);
		session.close();
	}
	
	public void addUsersWithManagerRollback() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Session session2 = HibernateUtil.getSessionFactory().openSession();
		session.setFlushMode(FlushMode.COMMIT);
		session2.setFlushMode(FlushMode.COMMIT);
		
		Transaction tx = session.beginTransaction();
		Transaction tx2 = session2.beginTransaction();
		
		User manager = new User("Manager", "Pa$sw0rd", null, null);
		
		
		session2.save(manager);
		tx2.commit();
		session2.close();
		
		User user = new User("FirstSlave", "asd", null, manager);
		User user2 = new User("SecondSlave", "abc", null, manager);
		session.save(user);
		session.save(user2);
		HibernateUtil.checkNumberOfUsers(session);
		tx.commit();
		HibernateUtil.checkNumberOfUsers(session);
		
		session.close();
		
	}
}
