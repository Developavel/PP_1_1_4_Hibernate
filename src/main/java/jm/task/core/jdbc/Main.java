package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main { // Запуск и демонстрация
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable(); // создание таблицы
        System.out.println("Таблица users создана");
        System.out.println("=".repeat(70)); // разделитель

        userService.saveUser("Иван", "Иваныч", (byte) 23); // добавление пользователя
        userService.saveUser("Петр", "Петров", (byte) 30); // добавление пользователя
        userService.saveUser("Мария", "Сидорова", (byte) 27); // добавление пользователя
        userService.saveUser("Анна", "Кузнецова", (byte) 37); // добавление пользователя
        System.out.println("=".repeat(70)); // разделитель

        System.out.println("Список всех пользователей таблицы users: ");
        List<User> users = userService.getAllUsers(); // получение всех пользователей
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println("=".repeat(70)); // разделитель

        userService.cleanUsersTable(); // очистка таблицы
        System.out.println("Таблица users очищена");
        System.out.println("=".repeat(70)); // разделитель

        userService.dropUsersTable(); // удаление таблицы
        System.out.println("Таблица users удалена");
        System.out.println("=".repeat(70)); // разделитель
    }
}
