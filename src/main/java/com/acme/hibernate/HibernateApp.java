package com.acme.hibernate;

import com.acme.hibernate.FlushModeAlways;


public class HibernateApp {

    public static void main(String[] args) {

        FlushModeAlways fma = new FlushModeAlways();

        fma.addUserWithCommit();
        fma.addUserWithoutCommit();
        fma.addUserWithRollback();
        
        //Flushmode=Commit examples
        HibernateFlushModeCommit hbm = new HibernateFlushModeCommit();
        hbm.addUser();
        hbm.addUserWithoutCommit();
        hbm.addUserWithRollback();
        hbm.addUsersWithManager();
    }
}
