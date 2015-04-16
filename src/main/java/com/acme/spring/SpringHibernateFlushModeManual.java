package com.acme.spring;


import com.acme.core.dao.impl.UserDao;
import com.acme.core.entity.User;
import com.acme.hibernate.HibernateUtil;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

public class SpringHibernateFlushModeManual {

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

    public void addUser() {
        Session s = sessionFactory.openSession();
        s.setFlushMode(FlushMode.MANUAL);
        System.out.println("+++ Save and read by the same session (FlashMode = MANUAL) +++");
        userDao.checkNumberOfUsers(s);
        Transaction tx = s.beginTransaction();
        User user = new User("ManualUserWithCommit", "asd", null, null);
        s.save(user);
        tx.commit();
        System.out.print("Before session.flush(): ");
        userDao.checkNumberOfUsers(s);
        s.flush();
        System.out.print("After session.flush(): ");
        userDao.checkNumberOfUsers(s);
        s.close();
    }

    public void addUserWithoutCommit() {
        Session s = sessionFactory.openSession();
        s.setFlushMode(FlushMode.MANUAL);
        User user = new User("ManualUserWithoutCommit", "asd", null, null);
        userDao.checkNumberOfUsers(s);
        s.save(user);
        s.flush();
        userDao.checkNumberOfUsers(s);
        s.close();
    }

    public void addUserWithRollback() {
        Session s = sessionFactory.openSession();
        s.setFlushMode(FlushMode.MANUAL);
        Transaction tx = s.beginTransaction();
        User user = new User("ManualUserWithRollback", "asd", null, null);
        System.out.print("Before session.save(): ");
        userDao.checkNumberOfUsers(s);
        s.save(user);
        System.out.print("Before tx.rollback(): ");
        userDao.checkNumberOfUsers(s);
        tx.rollback();
        System.out.print("Before session.flush(): ");
        userDao.checkNumberOfUsers(s);
        s.flush();
        System.out.print("after session.flush(): ");
        userDao.checkNumberOfUsers(s);
        s.close();
    }

    public void addUsersWithManager() {
        Session s = sessionFactory.openSession();
        s.setFlushMode(FlushMode.MANUAL);
        Transaction tx = s.beginTransaction();
        User manager = new User("ManualManager", "Pa$sw0rd", null, null);
        User user = new User("ManualFirstSlave", "asd", null, manager);
        User user2 = new User("ManualSecondSlave", "abc", null, manager);
        s.save(manager);
        s.save(user);
        s.save(user2);
        userDao.checkNumberOfUsers(s);
        s.flush();
        tx.commit();
        userDao.checkNumberOfUsers(s);
        s.close();
    }

    public void addUsersWithManagerTwoSession() {
        Session s = sessionFactory.openSession();
        Session s2 = sessionFactory.openSession();
        s.setFlushMode(FlushMode.MANUAL);
        s2.setFlushMode(FlushMode.MANUAL);

        Transaction tx = s.beginTransaction();
        Transaction tx2 = s2.beginTransaction();

        User manager = new User("ManualRBManager", "Pa$sw0rd", null, null);

        s2.save(manager);
        s2.flush();
        tx2.commit();
        s2.close();

        User user = new User("ManualRBFirstSlave", "asd", null, manager);
        User user2 = new User("ManualRBSecondSlave", "abc", null, manager);
        s.save(user);
        s.save(user2);
        userDao.checkNumberOfUsers(s);

        s.flush();
        tx.commit();
        s.close();
    }
}
