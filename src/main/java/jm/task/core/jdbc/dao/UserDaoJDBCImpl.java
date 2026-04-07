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

    @Override
    public void createUsersTable() { // создание таблицы
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                     "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                     "name VARCHAR(50), " +
                     "lastName VARCHAR(50), " +
                     "age TINYINT)";
        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void dropUsersTable() { // удаление таблицы
        String sql = "DROP TABLE IF EXISTS users";
        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) { // добавление пользователя
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3, age);
            pstmt.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) { // удаление по id
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() { // получение всех пользователей
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() { // очистка таблицы
        String sql = "TRUNCATE TABLE users";
        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
