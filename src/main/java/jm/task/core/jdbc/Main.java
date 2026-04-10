package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        System.out.println("Таблица users создана");
        System.out.println("=".repeat(70));

        userService.saveUser("Иван", "Иваныч", (byte) 23);
        userService.saveUser("Петр", "Петров", (byte) 30);
        userService.saveUser("Мария", "Сидорова", (byte) 27);
        userService.saveUser("Анна", "Кузнецова", (byte) 37);
        System.out.println("=".repeat(70));

        System.out.println("Список всех пользователей таблицы users: ");
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println("=".repeat(70));

        userService.cleanUsersTable();
        System.out.println("Таблица users очищена");
        System.out.println("=".repeat(70));

        userService.dropUsersTable();
        System.out.println("Таблица users удалена");
        System.out.println("=".repeat(70));
    }
}
