package com.acme.hibernate;


import com.acme.core.entity.User;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateFlushModeAuto {

    // The Session is sometimes flushed before query execution in order to ensure that queries never return stale state. (DEFAULT)

    public void oneSession() {
        System.out.println("+++ Save and read by the same session (FlashMode = AUTO) +++");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        System.out.print("* Checking number of users at the begin - ");
        HibernateUtil.checkNumberOfUsers(session);

        session.save(new User("FMAuto", "Commit"));
        System.out.print("* Checking number of users after saved without commit - ");
        HibernateUtil.checkNumberOfUsers(session);

        System.out.print("* Now, transaction commit and chacking number of users - ");
        tx.commit();
        HibernateUtil.checkNumberOfUsers(session);
        session.close();
    }

    public void twoSession() {
        System.out.println("+++ Save by 1st session and read by 2nd (FlashMode = AUTO) +++");
        Session s1 = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = s1.beginTransaction();
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Transaction tx2 = s2.beginTransaction();

        System.out.print("* Checking number of users at the begin - ");
        HibernateUtil.checkNumberOfUsers(s1);

        System.out.println("* Save without commit user-manager *");
        User manager = new User("FMAuto", "s1");
        s1.save(manager);
        System.out.print("* Before commit tx1 (1th session) - ");
        HibernateUtil.checkNumberOfUsers(s1);
        System.out.print("* ...and (2nd session) - ");
        HibernateUtil.checkNumberOfUsers(s2);
        // We must commit and close 1st session before saving object with reference to 1st object!
        tx1.commit();
        s1.close();
        System.out.print("* After commit tx1 (2nd session) - ");
        HibernateUtil.checkNumberOfUsers(s2);

        s2.save( new User("User", "SecondUser", null, manager));
        tx2.commit();
        System.out.print("* After commit tx2 - ");
        HibernateUtil.checkNumberOfUsers(s2);
        s2.close();
    }
}
