package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Util { // класс для подключения к БД (JDBC и Hibernate)

    private static final String URL = "jdbc:mysql://localhost:3306/my_db_test";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "TestSQL5566!!";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не удалось загрузить JDBC драйвер MySQL", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    private static SessionFactory sessionFactory; // создает сессии Hibernate для работы с БД

    public static SessionFactory getSessionFactory() { // конфиг Hibernate
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration()
                        .setProperty("hibernate.connection.url", URL)
                        .setProperty("hibernate.connection.username", USERNAME)
                        .setProperty("hibernate.connection.password", PASSWORD)
                        .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
            } catch (Throwable e) {
                throw new RuntimeException("Ошибка создания SessionFactory", e);
            }
        }
        return sessionFactory;
    }
}
