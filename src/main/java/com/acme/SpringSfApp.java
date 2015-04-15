package com.acme;

import com.acme.core.dao.impl.UserDao;
import com.acme.core.entity.User;
import com.acme.core.entity.User;
import com.acme.spring.SpringHibernateFlushModeAlways;
import com.acme.spring.SpringHibernateFlushModeAuto;
import com.acme.spring.SpringHibernateFlushModeCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;


class SpringSfApp {

//    @Autowired
//    SpringHibernateFlushModeAlways springHibernateFlushModeAlways;

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

//        SpringHibernateFlushModeAlways springHibernateFlushModeAlways = (SpringHibernateFlushModeAlways) ctx.getBean("springHibernateFlushModeAlways");
//        springHibernateFlushModeAlways.oneSession();
//        springHibernateFlushModeAlways.oneSessionRollback();
//        springHibernateFlushModeAlways.twoSession();

//        SpringHibernateFlushModeAuto springHibernateFlushModeAuto = (SpringHibernateFlushModeAuto) ctx.getBean("springHibernateFlushModeAuto");
//        springHibernateFlushModeAuto.oneSession();
//        springHibernateFlushModeAuto.twoSession();

        SpringHibernateFlushModeCommit springHibernateFlushModeCommit = (SpringHibernateFlushModeCommit) ctx.getBean("springHibernateFlushModeCommit");
//springHibernateFlushModeCommit.addUser();
//        springHibernateFlushModeCommit.addUserWithoutCommit();

        springHibernateFlushModeCommit.addUserWithRollback();
    }
}