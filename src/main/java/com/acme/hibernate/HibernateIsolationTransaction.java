package com.acme.hibernate;

import com.acme.core.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class HibernateIsolationTransaction {

    // Default level isolation trasaction is read committed.
    // We set level before creating object session factory, as property connection.

    public void transactionReadCommitted() {
        System.out.println("+++ TRANSACTION READ COMMITTED +++");
        Session s1 = HibernateUtil.getSessionFactory().openSession();
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = s1.beginTransaction();
        Transaction tx2 = s2.beginTransaction();

        System.out.println("\nSelected before insert: ");
        selectAllRows(s1);
        s2.save(new User("User", "pass"));

        System.out.println("\nSelected before commit: ");
        selectAllRows(s1);
        tx2.commit();

        System.out.println("\nSelected after commit: ");
        selectAllRows(s1);

        s1.close();
        s2.close();
        System.out.println("\n ************ end TRANSACTION READ COMMITTED");
    }

    public void transactionRepetableRead() {
        System.out.println("+++ TRANSACTION REPEATABLE READ +++");
        Session s1 = HibernateUtil.getSessionFactoryRR().openSession();
        Session s2 = HibernateUtil.getSessionFactoryRR().openSession();
        Session s3 = HibernateUtil.getSessionFactoryRR().openSession();
        Transaction tx1 = s1.beginTransaction();
        Transaction tx2 = s2.beginTransaction();
        Transaction tx3 = s3.beginTransaction();

        System.out.println("\nSelected before insert: ");
        selectAllRows(s1);
        s2.save(new User("User", "pass"));

        System.out.println("\nSelected before commit: ");
        selectAllRows(s1);
        tx2.commit();

        System.out.println("\nSelected in 1th transaction after commit transaction which insert date: ");
        selectAllRows(s1);
        System.out.println("\n And selected in 3th: ");
        selectAllRows(s3);

        tx1.commit();
        System.out.println("\nSelected in 1th transaction after commit this transaction: ");
        selectAllRows(s1);

        s1.close();
        s2.close();
        s3.close();
        System.out.println("\n ************ end TRANSACTION REPEATABLE READ");
    }

    public void transactionSerializable() {
        System.out.println("+++ TRANSACTION SERIALIZABLE +++");
        Session s1 = HibernateUtil.getSessionFactorySerializable().openSession();
        Session s2 = HibernateUtil.getSessionFactorySerializable().openSession();
        Transaction tx1 = s1.beginTransaction();
        Transaction tx2 = s2.beginTransaction();

        System.out.println("\nSelected before insert: ");
        selectAllRows(s1);

        User user = new User("User", "pass");
        long userId = (Long) s2.save(user);
        tx2.commit();

        System.out.println("\nSelected by session 1 after insert and commit: ");
        selectAllRows(s1);
        System.out.println("\nSelected by transaction 2: ");
        selectAllRows(s2);

        tx2 = s2.beginTransaction();
        user.setId(userId);
        user.setName("ChangedName");
        s2.update(user);
        System.out.println("\nSelected after update in 1th transaction: ");
        selectAllRows(s1);
        System.out.println("\nSelected after update in the same transaction: ");
        selectAllRows(s2);
        tx2.commit();

        System.out.println("\nSelected in 1th transaction after commit transaction which update: ");
        selectAllRows(s1);

        s2.close();
        Session s3 = HibernateUtil.getSessionFactoryRR().openSession();
        Transaction tx3 = s3.beginTransaction();
        System.out.println("\n And selected in 3th: ");
        selectAllRows(s3);

        tx1.commit();
        System.out.println("\nSelected in 1th transaction after commit this transaction: ");
        selectAllRows(s1);


        s1.close();

        s3.close();
        System.out.println("\n ************ end TRANSACTION REPEATABLE READ");
    }


    private void selectAllRows(Session session) {
        List<User> resultList = session.createQuery("From User ").list();
        for (User user : resultList) {
            System.out.print(" |" + user.getId() + " " + user.getName());
        }
    }
}
