package com.acme;

import com.acme.hibernate.HibernateFlushModeAlways;
import com.acme.hibernate.HibernateFlushModeCommit;
import com.acme.hibernate.HibernateFlushModeManual;
import com.acme.hibernate.HibernateUtil;

public class HibernateApp {

    public static void main(String[] args) {

    	HibernateUtil.deleteAllRowFromTable("User");
    	HibernateUtil.deleteAllRowFromTable("Group");

        //Flushmode=Always examples
        HibernateFlushModeAlways fma = new HibernateFlushModeAlways();
        fma.oneSession();
        fma.oneSessionRollback();
        fma.twoSession();
        fma.addUsersWithManager();
        
        //Flushmode=Commit examples
        HibernateFlushModeCommit hbm = new HibernateFlushModeCommit();
        hbm.addUser();
        hbm.addUserWithoutCommit();
        hbm.addUserWithRollback();
        hbm.addUsersWithManager();
        hbm.addUsersWithManagerRollback();
        
        //Flushmode=MANUAL examples
        HibernateFlushModeManual hbmm = new HibernateFlushModeManual();
        hbmm.addUser();
        hbmm.addUserWithoutCommit();
        hbmm.addUserWithRollback();
        hbmm.addUsersWithManager();
        hbmm.addUsersWithManagerRollback();
    }
}
