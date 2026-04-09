package jm.task.core.jdbc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Objects;

@Entity // сообщаем Hibernate, что это таблица в БД
@Table(name = "users") // явно указываем имя таблицы
public class User { // сущность пользователя в таблице users
    @Id // указываем первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hibernate автоматически id
    @Column(name = "id") // явно указываем имя колонки
    private Long id;

    @Column(name = "name") // явно указываем имя колонки
    private String name;

    @Column(name = "lastName") // явно указываем имя колонки
    private String lastName;

    @Column(name = "age") // явно указываем имя колонки
    private Byte age;

    public User() { // пустой конструктор для Hibernate (создание объектов через рефлексию)
    }

    public User(String name, String lastName, Byte age) { // конструктор для нового пользователя (без ID, сгенерируется автоматически)
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public User(Long id, String name, String lastName, Byte age) { // конструктор для восстановления пользователя из БД (с готовым ID)
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override // сравниваем пользователей по всем полям (для кеширования в Hibernate)
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(age, user.age);
    }

    @Override // вычисляем хеш-код на основе всех полей (для коллекций)
    public int hashCode() {
        return Objects.hash(id, name, lastName, age);
    }

    @Override // для читаемого вывода в консоль
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
