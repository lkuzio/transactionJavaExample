package com.acme.hibernate;

import com.acme.core.entity.Group;
import com.acme.core.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;


public class HibernateApp {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        Group group = new Group("users");

        session.save(group);
        session.save(new User("jan", "pass", group));
        session.save(new User("adam", "pass", group));

        session.getTransaction().commit();

        Query query = session.createQuery("From User ");

        List<User> resultList = query.list();
        System.out.println("num of users: " + resultList.size());
        for (User user : resultList) {
            System.out.println("user: " + group);
        }
    }

}
