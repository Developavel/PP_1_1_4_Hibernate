package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try (Connection conn = Util.getConnection()) {
            System.out.println("Успешное подключение к базе данных: " + conn.getCatalog());
        } catch (RuntimeException e) {
            System.out.println("❌ Ошибка: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        // создание таблицы
        userService.createUsersTable();

        // добавление пользователей
        userService.saveUser("Иван", "Иванов", (byte) 25);
        userService.saveUser("Петр", "Петров", (byte) 30);
        userService.saveUser("Мария", "Сидорова", (byte) 28);
        userService.saveUser("Анна", "Кузнецова", (byte) 22);

        // получение всех пользователей
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        // очистка таблицы
        userService.cleanUsersTable();

        // удаление таблицы
        userService.dropUsersTable();
    }
}
