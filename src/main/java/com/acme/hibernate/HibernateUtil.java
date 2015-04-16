package com.acme.hibernate;

import java.sql.Connection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.acme.core.entity.User;

public class HibernateUtil {

    //Session Factory with set transaction isolation read commited
    private static SessionFactory sessionFactory = buildSessionFactory(2);

    //Session Factory with set transaction isolation repetable read
    private static SessionFactory sessionFactoryRR = buildSessionFactory(4);

    //Session Factory with set transaction isolation repetable read
    private static SessionFactory sessionFactorySerializable = buildSessionFactory(8);

    private static SessionFactory buildSessionFactory(Integer isolation) {
        try {
            Configuration configuration = new Configuration().configure().setProperty("hibernate.connection.isolation", isolation.toString());
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            return configuration.buildSessionFactory(builder.build());
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static SessionFactory getSessionFactoryRR() {
        return sessionFactoryRR;
    }

    public static SessionFactory getSessionFactorySerializable() {
        return sessionFactorySerializable;
    }
    
    @SuppressWarnings("unchecked")
	public static void listOfUsers(Session session) {
		Query query = session.createQuery("From User ");
		List<User> resultList = query.list();
		System.out.println("num of users: " + resultList.size());
		for (User user : resultList) {
            System.out.println("user: " + user.toString());
        }
	}

    public static void deleteAllRowFromTable(String myTable){
    	Session session = getSessionFactory().openSession();
    	session.beginTransaction();
        String hql = String.format("delete from %s",myTable);
        Query query = session.createQuery(hql);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    public static void checkNumberOfUsers(Session session) {
        Query query = session.createQuery("From User ");
        List<User> resultList = query.list();
        System.out.println("Number of users: " + resultList.size());
    }
}