package com.acme;

import com.acme.spring.SpringHibernateFlushModeAlways;
import com.acme.spring.SpringHibernateFlushModeAuto;
import com.acme.spring.SpringHibernateFlushModeCommit;
import com.acme.spring.SpringHibernateFlushModeManual;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


class SpringSfApp {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        SpringHibernateFlushModeAlways springHibernateFlushModeAlways = (SpringHibernateFlushModeAlways) ctx.getBean("springHibernateFlushModeAlways");
        springHibernateFlushModeAlways.oneSession();
        springHibernateFlushModeAlways.oneSessionRollback();
        springHibernateFlushModeAlways.twoSession();

        SpringHibernateFlushModeAuto springHibernateFlushModeAuto = (SpringHibernateFlushModeAuto) ctx.getBean("springHibernateFlushModeAuto");
        springHibernateFlushModeAuto.oneSession();
        springHibernateFlushModeAuto.twoSession();

        SpringHibernateFlushModeCommit springHibernateFlushModeCommit = (SpringHibernateFlushModeCommit) ctx.getBean("springHibernateFlushModeCommit");
        springHibernateFlushModeCommit.addUser();
        springHibernateFlushModeCommit.addUserWithoutCommit();
        springHibernateFlushModeCommit.addUserWithRollback();

        SpringHibernateFlushModeManual springHibernateFlushModeManual = (SpringHibernateFlushModeManual) ctx.getBean("springHibernateFlushModeManual");
        springHibernateFlushModeManual.addUser();
        springHibernateFlushModeManual.addUsersWithManager();
    }
}