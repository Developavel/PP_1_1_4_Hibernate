package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        // cоздание таблицы
        userService.createUsersTable();
        System.out.println("Таблица users создана");

        // добавление пользователей
        userService.saveUser("Иван", "Иваныч", (byte) 25);
        userService.saveUser("Петр", "Петров", (byte) 30);
        userService.saveUser("Мария", "Сидорова", (byte) 28);
        userService.saveUser("Анна", "Кузнецова", (byte) 22);

        // получение всех пользователей
        System.out.println("\nСписок всех пользователей:");
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        // очистка таблицы
        userService.cleanUsersTable();
        System.out.println("\nТаблица users очищена");

        // удаление таблицы
        userService.dropUsersTable();
        System.out.println("Таблица users удалена");
    }
}
