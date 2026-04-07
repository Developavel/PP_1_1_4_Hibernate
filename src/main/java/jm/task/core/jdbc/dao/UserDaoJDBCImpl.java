package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    private void executeUpdate(String sql, QueryExecutor executor) {
        Connection connection = null;
        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);

            executor.execute(connection, sql);

            connection.commit();

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException("Ошибка выполнения операции", e);

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private interface QueryExecutor {
        void execute(Connection connection, String sql) throws SQLException;
    }

    @Override
    public void createUsersTable() { // создание таблицы
        executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                     "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                     "name VARCHAR(50), " +
                     "lastName VARCHAR(50), " +
                     "age TINYINT)",
                     (connection, sql) -> {
                         try (Statement statement = connection.createStatement()) {
                             statement.executeUpdate(sql);
                         }
                     });
    }

    @Override
    public void dropUsersTable() { // удаление таблицы
        executeUpdate("DROP TABLE IF EXISTS users",
                     (connection, sql) -> {
                        try (Statement statement = connection.createStatement()){
                            statement.executeUpdate(sql);
                        }
                     });
    }

    @Override
    public void saveUser(String name, String lastName, byte age) { // добавление пользователя
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        executeUpdate(sql, (connection, query) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, name);
                pstmt.setString(2, lastName);
                pstmt.setByte(3, age);
                pstmt.executeUpdate();
            }
        });
        System.out.println("В таблицу users добавлен новый пользователь: " + name + " " + lastName + ", возраст " + age);
    }

    @Override
    public void removeUserById(long id) { // удаление по id
        String sql = "DELETE FROM users WHERE id = ?";
        executeUpdate(sql, (connection, query) -> {
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setLong(1, id);
                pstmt.executeUpdate();
            }
        });
    }

    @Override
    public List<User> getAllUsers() { // получение всех пользователей
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                        rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age")
                );
                user.setId(rs.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения всех пользователей", e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() { // очистка таблицы
        executeUpdate("TRUNCATE TABLE users",
                (connection, sql) -> {
                    try (Statement statement = connection.createStatement()) {
                        statement.executeUpdate(sql);
                    }
                });
    }
}
