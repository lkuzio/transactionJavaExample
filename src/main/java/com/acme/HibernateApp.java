package com.acme;

import com.acme.core.entity.Group;
import com.acme.core.entity.User;
import com.acme.hibernate.FlushModeAlways;
import com.acme.hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;


public class HibernateApp {

    public static void main(String[] args) {

        FlushModeAlways fma = new FlushModeAlways();

        fma.addUserWithCommit();
        fma.addUserWithoutCommit();
        fma.addUserWithRollback();
    }
}
