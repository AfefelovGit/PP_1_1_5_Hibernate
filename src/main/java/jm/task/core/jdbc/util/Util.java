package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {
    private static final Configuration cfg = new Configuration();
    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        cfg.addAnnotatedClass(User.class);
        if (sessionFactory == null) {
            sessionFactory = cfg.buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
}
