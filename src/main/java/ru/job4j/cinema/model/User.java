package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Класс представляет собой модель данных пользователя
 * @author Denis Kalchenko
 * @version 1.0
 */
public class User {

    /**
     * Идентификатор пользователя
     */
    private int id;

    /**
     * Имя
     */
    private String username;

    /**
     * Электронная почта
     */
    private String email;

    /**
     * Номер телефона
     */
    private String phone;

    /**
     * Пароль
     */
    private String password;

    public User() { }

    public User(int id, String username, String email, String phone, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id
                && Objects.equals(username, user.username)
                && Objects.equals(email, user.email)
                && Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, phone);
    }

}
