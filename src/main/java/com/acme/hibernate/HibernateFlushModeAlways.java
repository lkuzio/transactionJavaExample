package com.acme.hibernate;

import com.acme.core.entity.User;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class HibernateFlushModeAlways {

    // The Session is flushed before every query.

    public void oneSession() {
        System.out.println("+++ Save and read by the same session (FlashMode = ALWAYS) +++");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.setFlushMode(FlushMode.ALWAYS);
        Transaction tx = session.beginTransaction();

        System.out.println("* Checking number of users at the begin *");
        HibernateUtil.checkNumberOfUsers(session);

        session.save(new User("FMAlways", "Commit"));
        System.out.println("* Checking number of users after saved without commit  *");
        HibernateUtil.checkNumberOfUsers(session);

        System.out.println("* Now, transaction commit  *");
        tx.commit();
        session.close();
    }

    public void oneSessionRollback() {
        System.out.println("+++ Save with rollback (FlashMode = ALWAYS) +++");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.setFlushMode(FlushMode.ALWAYS);
        Transaction tx = session.beginTransaction();

        System.out.println("* Checking number of users at the begin *");
        HibernateUtil.checkNumberOfUsers(session);

        System.out.println("* Save and checking *");
        session.save(new User("FMAlways", "rollback"));
        HibernateUtil.checkNumberOfUsers(session);

        System.out.println("* Now, transaction rollback  *");
        tx.rollback();
        HibernateUtil.checkNumberOfUsers(session);
        session.close();
    }

    public void twoSession() {
        System.out.println("+++ Save by 1st session and read by 2nd (FlashMode = ALWAYS) +++");
        Session s1 = HibernateUtil.getSessionFactory().openSession();
        s1.setFlushMode(FlushMode.ALWAYS);
        Transaction tx1 = s1.beginTransaction();
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Transaction tx2 = s2.beginTransaction();

        System.out.println("* Checking number of users at the begin *");
        HibernateUtil.checkNumberOfUsers(s1);

        System.out.println("* Save without commit *");
        s1.save(new User("FMAlways", "s1"));
        System.out.println("* Checking number of users - query from 1th session *");
        HibernateUtil.checkNumberOfUsers(s1);

        System.out.println("* Checking number of users - query from 2nd session *");
        HibernateUtil.checkNumberOfUsers(s2);

        System.out.println("* Commit and checking number of users *");
        tx1.commit();
        HibernateUtil.checkNumberOfUsers(s2);
        s1.close();
        s2.close();
    }

    public void addUsersWithManager() {
        System.out.println("+++ Save one user (menager) and save 2nd user with set manager(FlashMode = ALWAYS) +++");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.setFlushMode(FlushMode.ALWAYS);
        Transaction tx = session.beginTransaction();

        System.out.println("* Checking number of users at the begin *");
        HibernateUtil.checkNumberOfUsers(session);

        System.out.println("* Save user-manager and user with set menager *");
        User manager = new User("Menager", "FirstUser");
        session.save(manager);
        session.save( new User("User", "SecondUser", null, manager));
        System.out.println("* Checking befor commit number of users *");
        HibernateUtil.checkNumberOfUsers(session);

        System.out.println("* Commit and checking number of users *");
        tx.commit();
        HibernateUtil.checkNumberOfUsers(session);
        session.close();
    }
}
