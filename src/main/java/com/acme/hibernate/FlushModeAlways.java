package com.acme.hibernate;

import com.acme.core.entity.User;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FlushModeAlways {

    public void addUserWithCommit() {
        System.out.println("****** Save with commit (FlashMode = ALWAYS) ******");

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.setFlushMode(FlushMode.ALWAYS);
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("From User ");
        checkNumberOfElement(query);

        session.save(new User("FMAlways", "Commit"));
        checkNumberOfElement(query);

        tx.commit();
    }

    public void addUserWithoutCommit() {
        System.out.println("****** Save without commit (FlashMode = ALWAYS) ******");

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.setFlushMode(FlushMode.ALWAYS);
        session.beginTransaction();

        Query query = session.createQuery("From User ");
        checkNumberOfElement(query);

        session.save(new User("FMAlways", "withoutCommit"));
        checkNumberOfElement(query);
    }

    public void addUserWithRollback() {
        System.out.println("****** Save without rollback (FlashMode = ALWAYS) ******");

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.setFlushMode(FlushMode.ALWAYS);
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("From User ");
        checkNumberOfElement(query);

        session.save(new User("FMAlways", "rollback"));
        checkNumberOfElement(query);

        tx.rollback();
        checkNumberOfElement(query);
    }

    private void checkNumberOfElement(Query query) {
        List<User> resultList = query.list();
        System.out.println("***** Number of users: " + resultList.size() + " *****");
    }
}
