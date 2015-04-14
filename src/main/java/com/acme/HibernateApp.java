package com.acme;

import com.acme.hibernate.*;

public class HibernateApp {

    public static void main(String[] args) {

        //Flushmode=Always examples
        clearDatabase();
        HibernateFlushModeAlways modeAlways = new HibernateFlushModeAlways();
        modeAlways.oneSession();
        modeAlways.oneSessionRollback();
        modeAlways.twoSession();
        modeAlways.addUsersWithManager();

        //Flushmode=Commit examples
        clearDatabase();
        HibernateFlushModeCommit hbm = new HibernateFlushModeCommit();
        hbm.addUser();
        hbm.addUserWithoutCommit();
        hbm.addUserWithRollback();
        hbm.addUsersWithManager();
        hbm.addUsersWithManagerRollback();

        //Flushmode=MANUAL examples
        clearDatabase();
        HibernateFlushModeManual hbmm = new HibernateFlushModeManual();
        hbmm.addUser();
        hbmm.addUserWithoutCommit();
        hbmm.addUserWithRollback();
        hbmm.addUsersWithManager();
        hbmm.addUsersWithManagerRollback();

        //Flushmode=AUTO examples
        clearDatabase();
        HibernateFlushModeAuto modeAuto = new HibernateFlushModeAuto();
        modeAuto.oneSession();
        modeAuto.twoSession();
    }

    private static void clearDatabase() {
        HibernateUtil.deleteAllRowFromTable("User");
        HibernateUtil.deleteAllRowFromTable("Group");
    }
}
