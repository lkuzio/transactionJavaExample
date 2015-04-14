package com.acme;

import com.acme.hibernate.*;

public class HibernateApp {

    public static void main(String[] args) {

    	HibernateUtil.deleteAllRowFromTable("User");
    	HibernateUtil.deleteAllRowFromTable("Group");

        //Flushmode=Always examples
        HibernateFlushModeAlways modeAlways = new HibernateFlushModeAlways();
        modeAlways.oneSession();
        modeAlways.oneSessionRollback();
        modeAlways.twoSession();
        modeAlways.addUsersWithManager();
        
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

        //Flushmode=AUTO examples
        HibernateFlushModeAuto modeAuto = new HibernateFlushModeAuto();
        modeAuto.oneSession();
        modeAuto.twoSession();
    }
}
