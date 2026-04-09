package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;

import org.hibernate.HibernateException;
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

    private static SessionFactory sessionFactory; // фабрика, которая создает сессии для работы с БД

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // настройка подключения Hibernate к БД
                configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                configuration.setProperty("hibernate.connection.url", URL);
                configuration.setProperty("hibernate.connection.username", USERNAME);
                configuration.setProperty("hibernate.connection.password", PASSWORD);

                // настройки работы Hibernate
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect"); // указываем диалект MySQL 8
                configuration.setProperty("hibernate.show_sql", "true"); // показывать SQL-запросы в консоли
                configuration.setProperty("hibernate.format_sql", "true"); // форматировать SQL для читаемости
                configuration.setProperty("hibernate.hbm2ddl.auto", "update"); // автоматически обновлять схему БД

                // регистрация сущности User, которую Hibernate будет отслеживать
                configuration.addAnnotatedClass(User.class);

                sessionFactory = configuration.buildSessionFactory(); // создаем фабрику сессий для работы с БД
            } catch (HibernateException e) {
                throw new RuntimeException("Ошибка создания SessionFactory", e);
            }
        }
        return sessionFactory;
    }
}
