/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author eliezer
 */
public class HibernateManager {

    public static final int TRANSACTION_TIMEOUT = 5;//tiempo en segundos
    public static final int TRANSACTION_TIMEOUT_LONG = 60;//tiempo en segundos
    public static final int HIBERNATE_BATCH_SIZE = 30;

    private static SessionFactory factory;

    private HibernateManager() {
    }

    public static synchronized SessionFactory getSessionFactory() {
        if (factory == null) {
            factory = buildSessionFactory();
        }
        return factory;
    }

    private static SessionFactory buildSessionFactory() {
        final BootstrapServiceRegistry bootstrapServiceRegistry = new BootstrapServiceRegistryBuilder().enableAutoClose()
                .applyIntegrator(MetadataExtractorIntegrator.INSTANCE).build();

        final StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder(bootstrapServiceRegistry).configure().build();
        return new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
    }

    public static Session getSession() {
        Session hibernateSession = getSessionFactory().getCurrentSession();
        if (hibernateSession == null) {
            hibernateSession = getSessionFactory().openSession();
            Logger.getLogger(HibernateManager.class.getName()).log(Level.WARNING, "Nuevo session...");
        }
        return hibernateSession;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

    public static void closeSession(Session session) {
        if (session != null) {
            if (session.isOpen()) {
                if (session.getTransaction().isActive()) {
                    session.flush();
                }
                session.close();
            }
        }
    }

    public static void transactionRollBack(Transaction tx) {
        if (tx != null) {
            if (tx.isActive() || tx.getRollbackOnly()) {
                tx.rollback();
            }
        }
    }

    public static void closeTransaction(Transaction transaction) {
        throw new java.lang.UnsupportedOperationException("");

    }
}

