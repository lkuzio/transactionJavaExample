package com.acme.core.dao.impl;


import com.acme.core.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UserDao  {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    public void insertUser(User user){
        sessionFactory.getCurrentSession().save(user);
    }

    public User getUserById(Long userId){
        return (User) sessionFactory.getCurrentSession().get(User.class, userId);
    }

    public User getUser(String name){
        Query query = sessionFactory.
                getCurrentSession().
                createQuery("from User where name = :name");
        query.setParameter("name", name);
        return (User) query.list().get(0);
    }

    public List<User> getUsers() {
        Criteria criteria = sessionFactory.
                getCurrentSession().
                createCriteria(User.class);
        return criteria.list();

    }

    public void checkNumberOfUsers(Session session) {
        Query query = session.createQuery("From User ");
        List<User> resultList = query.list();
        System.out.println("***Number of users: " + resultList.size());
    }


}
