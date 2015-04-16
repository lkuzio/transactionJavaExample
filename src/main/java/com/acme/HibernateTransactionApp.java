package com.acme;

import com.acme.hibernate.HibernateUtil;
import com.acme.hibernate.HibernateIsolationTransaction;

import java.sql.SQLException;

public class HibernateTransactionApp {

    public static void main(String[] args) {
            //IsolationTransaction
            clearDatabase();
            HibernateIsolationTransaction it = new HibernateIsolationTransaction();
            it.transactionReadCommitted();
            it.transactionRepetableRead();
            it.transactionSerializable();
    }

    private static void clearDatabase() {
        HibernateUtil.deleteAllRowFromTable("User");
        HibernateUtil.deleteAllRowFromTable("Group");
    }
}
