package com.acme.spring;


import com.acme.core.dao.impl.UserDao;
import com.acme.core.entity.User;
import com.acme.hibernate.HibernateUtil;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SpringHibernateFlushModeCommit {

    // The Session is flushed when Transaction.commit() is called.

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
        s.setFlushMode(FlushMode.COMMIT);
        Transaction tx = s.beginTransaction();
        User user = new User("UserWithCommit", "asd", null, null);
        s.save(user);
        userDao.checkNumberOfUsers(s);
        tx.commit();
        userDao.checkNumberOfUsers(s);
        s.close();
    }

    public void addUserWithoutCommit() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.setFlushMode(FlushMode.COMMIT);
        User user = new User("UserWithoutCommit", "asd", null, null);
        userDao.checkNumberOfUsers(session);
        session.save(user);
        userDao.checkNumberOfUsers(session);
        session.close();
    }

    public void addUserWithRollback() {
        Session session = sessionFactory.openSession();
        session.setFlushMode(FlushMode.COMMIT);
        Transaction tx = session.beginTransaction();
        User user = new User("UserWithRollback", "asd", null, null);
        userDao.checkNumberOfUsers(session);
        session.save(user);
        userDao.checkNumberOfUsers(session);
        tx.rollback();
        userDao.checkNumberOfUsers(session);
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
        userDao.checkNumberOfUsers(session);
        tx.commit();
        userDao.checkNumberOfUsers(session);
        session.close();
    }

    public void addUsersWithManagerRollback() {
        Session s1 = sessionFactory.openSession();
        Session s2 = sessionFactory.openSession();
        s1.setFlushMode(FlushMode.COMMIT);
        s2.setFlushMode(FlushMode.COMMIT);

        Transaction tx = s1.beginTransaction();
        Transaction tx2 = s2.beginTransaction();

        User manager = new User("Manager", "Pa$sw0rd", null, null);

        s2.save(manager);
        tx2.commit();
        s2.close();

        User user = new User("FirstSlave", "asd", null, manager);
        User user2 = new User("SecondSlave", "abc", null, manager);
        s1.save(user);
        s1.save(user2);
        userDao.checkNumberOfUsers(s1);
        tx.commit();
        userDao.checkNumberOfUsers(s1);

        s1.close();
    }
}
