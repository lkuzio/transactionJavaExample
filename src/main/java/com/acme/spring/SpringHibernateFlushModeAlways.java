package com.acme.spring;


import com.acme.core.dao.impl.UserDao;
import com.acme.core.entity.User;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpringHibernateFlushModeAlways {

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
        System.out.println("+++ Save and read by the same session (FlushMode = ALWAYS) +++");
        Session s = sessionFactory.getCurrentSession();
        s.setFlushMode(FlushMode.ALWAYS);

        System.out.print("* Checking number of users at the begin - ");
        userDao.checkNumberOfUsers(s);

        User manager = new User("Manager", "Pa$sw0rd", null, null);
        User user = new User("FirstSlave", "asd", null, manager);
        userDao.insertUser(user);

        System.out.print("* Checking number of users after saved without commit - ");
        userDao.checkNumberOfUsers(s);

    }

    @Transactional
    public void oneSessionRollback() {
        System.out.println("+++ Save with rollback (FlushMode = ALWAYS) +++");
        Session s = sessionFactory.openSession();
        s.setFlushMode(FlushMode.ALWAYS);

        System.out.print("* Checking number of users at the begin - ");
        userDao.checkNumberOfUsers(s);

        System.out.println("* Save and checking *");
        userDao.insertUser(new User("FMAlways", "rollback"));

        userDao.checkNumberOfUsers(s);


        System.out.println("* Now, transaction rollback - ");

        sessionFactory.getCurrentSession().getTransaction().rollback();
        userDao.checkNumberOfUsers(s);
    }

    @Transactional
    public void twoSession() {
        System.out.println("+++ Save by 1st session and read by 2nd (FlashMode = ALWAYS) +++");

        Session s1 = sessionFactory.openSession();
        s1.setFlushMode(FlushMode.ALWAYS);
        Transaction tx1 = s1.beginTransaction();

        Session s2 = sessionFactory.openSession();
        Transaction tx2 = s2.beginTransaction();

        System.out.print("* Checking number of users at the begin - ");
        userDao.checkNumberOfUsers(s1);

        System.out.println("* Save without commit *");
        s1.save(new User("FMAlways", "s1"));
        System.out.println("* Before commit in 1th session - ");
        userDao.checkNumberOfUsers(s1);

        System.out.println("* ...and in 2th session - ");
        userDao.checkNumberOfUsers(s2);

        System.out.println("* Commit and checking for 2th session - ");
        tx1.commit();
        userDao.checkNumberOfUsers(s2);
        s1.close();
        s2.close();
    }


}
