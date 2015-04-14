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
		User user = new User("Commiter", "asd", null, null);
		session.save(user);
		HibernateUtil.listOfUsers(session);
		tx.commit();
		HibernateUtil.listOfUsers(session);
		session.close();
	}

	public void addUserWithoutCommit() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.setFlushMode(FlushMode.COMMIT);
		User user = new User("NoCommiter", "asd", null, null);
		HibernateUtil.listOfUsers(session);
		session.save(user);
		HibernateUtil.listOfUsers(session);
		session.close();
	}

	public void addUserWithRollback() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.setFlushMode(FlushMode.COMMIT);
		Transaction tx = session.beginTransaction();
		User user = new User("NoCommiter", "asd", null, null);
		HibernateUtil.listOfUsers(session);
		session.save(user);
		HibernateUtil.listOfUsers(session);
		tx.rollback();
		HibernateUtil.listOfUsers(session);
		session.close();
	}

	public void addUsers() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.setFlushMode(FlushMode.COMMIT);
		Transaction tx = session.beginTransaction();
		User user = new User("Commiter", "asd", null, null);
		session.save(user);
		HibernateUtil.listOfUsers(session);
		tx.commit();
		HibernateUtil.listOfUsers(session);
		session.close();
	}

	public void addUsersWithManager() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.setFlushMode(FlushMode.COMMIT);
		Transaction tx = session.beginTransaction();
		User manager = new User("Manager", "Pa$sw0rd", null, null);
		User user = new User("Commiter", "asd", null, manager);
		User user2 = new User("Second", "abc", null, manager);
		session.save(manager);
		session.save(user);
		session.save(user2);
		HibernateUtil.listOfUsers(session);
		tx.commit();
		HibernateUtil.listOfUsers(session);
		session.close();
	}
}
