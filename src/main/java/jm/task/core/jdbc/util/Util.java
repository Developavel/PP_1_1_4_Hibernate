package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/my_db_test";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "TestSQL5566!!";

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
                sessionFactory = new Configuration()
                        .setProperty("hibernate.connection.url", URL)
                        .setProperty("hibernate.connection.username", USERNAME)
                        .setProperty("hibernate.connection.password", PASSWORD)
                        .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
        }
        return sessionFactory;
    }
}
