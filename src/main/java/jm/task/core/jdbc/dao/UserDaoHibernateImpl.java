package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao { // работа с БД через Hibernate (вместо JDBC)

    private final SessionFactory sessionFactory = Util.getSessionFactory(); // создает сессии (из Util) для работы с БД
    public UserDaoHibernateImpl() { // пустой конструктор, чтобы Hibernate мог создавать объекты (рефлексия)
    }

    @Override // создание таблицы через обычный SQL запрос
    public void createUsersTable() {
        Transaction transaction = null; // для отката при ошибке в catch
        try (Session session = sessionFactory.openSession()) { // открываем сессию
            transaction = session.beginTransaction(); // начинаем транзакцию
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(50), " +
                    "lastName VARCHAR(50), " +
                    "age TINYINT)";
            Query query = session.createNativeQuery(sql); // создаём обычный SQL запрос
            query.executeUpdate(); // выполняем запрос
            transaction.commit(); // фиксируем изменения
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // откат при ошибке (меняем всё назад)
            throw new RuntimeException("Ошибка создания таблицы", e);
        }
    }

    @Override // удаление таблицы через обычный SQL запрос
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users";
            Query query = session.createNativeQuery(sql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Ошибка удаления таблицы", e);
        }
    }

    @Override // добавляем в базу через Hibernate (Hibernate сам генерирует INSERT)
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null; // для отката при ошибке в catch
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age); // создаём объект (ID сгенерируется автоматически)
            session.save(user); // Hibernate сам генерирует INSERT и сохраняет в БД
            transaction.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null ) transaction.rollback(); // откат при ошибке (меняем всё назад)
            throw new RuntimeException("Ошибка сохранения пользователя", e);
        }
    }

    @Override // удаляем пользователя по ID
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id); // загружаем пользователя из БД по ID
            if (user != null) { // если пользователь существует
                session.delete(user); // удаляем
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Ошибка удаления пользователя", e);
        }
    }

    @Override // получаем всех пользователей из БД
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            // HQL запрос (работает с классом User)
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка получения всех пользователей", e);
        }
    }

    @Override // очищаем таблицу (удаляем все записи)
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate(); // удаляем всё
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Ошибка очистки таблицы", e);
        }
    }
}
