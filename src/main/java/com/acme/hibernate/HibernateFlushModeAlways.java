package com.acme.hibernate;

import com.acme.core.entity.User;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class HibernateFlushModeAlways {

    // The Session is flushed before every query.

    public void oneSession() {
        System.out.println("+++ Save and read by the same session (FlashMode = ALWAYS) +++");

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.setFlushMode(FlushMode.ALWAYS);
        Transaction tx = session.beginTransaction();

        System.out.println("* Checking number of users at the begin *");
        Query query = session.createQuery("From User ");
        checkNumberOfElement(query);

        session.save(new User("FMAlways", "Commit"));
        System.out.println("* Checking number of users after saved without commit  *");
        checkNumberOfElement(query);

        System.out.println("* Now, transaction commit  *");
        tx.commit();
    }

    public void oneSessionRollback() {
        System.out.println("+++ Save with rollback (FlashMode = ALWAYS) +++");

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.setFlushMode(FlushMode.ALWAYS);
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("From User ");
        checkNumberOfElement(query);

        session.save(new User("FMAlways", "rollback"));
        checkNumberOfElement(query);

        System.out.println("* Now, transaction rollback  *");
        tx.rollback();
        checkNumberOfElement(query);
    }

    public void twoSession() {
        System.out.println("+++ Save by 1st session and read by 2nd (FlashMode = ALWAYS) +++");

        Session s1 = HibernateUtil.getSessionFactory().openSession();
        s1.setFlushMode(FlushMode.ALWAYS);
        Transaction tx1 = s1.beginTransaction();
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Transaction tx2 = s2.beginTransaction();

        System.out.println("* Checking number of users at the begin *");
        Query query = s1.createQuery("From User ");
        checkNumberOfElement(query);

        System.out.println("* Save without commit *");
        s1.save(new User("FMAlways", "s1"));
        System.out.println("* Checking number of users - query from 1th session *");
        checkNumberOfElement(query);

        System.out.println("* Checking number of users - query from 2nd session *");
        query = s2.createQuery("From User ");
        checkNumberOfElement(query);

        System.out.println("* Commit... *");
        tx1.commit();
        System.out.println("* ...and checking number of users in 2nd session *");
        checkNumberOfElement(query);
    }

    private void checkNumberOfElement(Query query) {
        List<User> resultList = query.list();
        System.out.println("Number of users: " + resultList.size());
    }
}
