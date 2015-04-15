package com.acme.spring;


import com.acme.core.dao.impl.UserDao;
import com.acme.core.entity.User;
import com.acme.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SpringHibernateFlushModeAuto {

    // The Session is sometimes flushed before query execution in order to ensure that queries never return stale state. (DEFAULT)

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserDao userDao;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void oneSession() {
        System.out.println("+++ Save and read by the same session (FlashMode = AUTO) +++");
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();

        System.out.println("* Checking number of users at the begin - ");
        userDao.checkNumberOfUsers(s);

        userDao.insertUser(new User("FMAuto", "Commit"));
        System.out.println("* Checking number of users after saved without commit - ");
        userDao.checkNumberOfUsers(s);

        System.out.println("* Now, transaction commit and checking number of users - ");
        tx.commit();
        userDao.checkNumberOfUsers(s);
    }

    @Transactional
    public void twoSession() {
        System.out.println("+++ Save by 1st session and read by 2nd (FlashMode = AUTO) +++");
        Session s1 = sessionFactory.openSession();
        Transaction tx1 = s1.beginTransaction();
        Session s2 = sessionFactory.openSession();
        Transaction tx2 = s2.beginTransaction();

        System.out.print("* Checking number of users at the begin - ");
        userDao.checkNumberOfUsers(s1);

        System.out.println("* Save without commit user-manager *");
        User manager = new User("FMAuto", "s1");
        userDao.insertUser(manager);

        System.out.print("* Before commit tx1 (1th session) - ");
        userDao.checkNumberOfUsers(s1);

        System.out.print("* ...and (2nd session) - ");
        userDao.checkNumberOfUsers(s2);

        // We must commit and close 1st session before saving object with reference to 1st object!
        tx1.commit();

        System.out.print("* After commit tx1 (2nd session) - ");
        userDao.checkNumberOfUsers(s2);

        s2.save(new User("User", "SecondUser", null, manager));
        tx2.commit();
        System.out.print("* After commit tx2 - ");
        userDao.checkNumberOfUsers(s2);
    }
}
