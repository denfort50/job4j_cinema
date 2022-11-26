package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.sql.*;
import java.util.Optional;

/**
 * Интерфейс описывает методы взаимодействия с хранилищем пользователей
 * @author Denis Kalchenko
 * @version 1.0
 */
public interface UserRepository {

    /**
     * Метод сохраняет пользователя
     * @param user пользователь
     * @return возвращает Optional пользователя
     */
    Optional<User> add(User user);

    /**
     * Метод находит пользователя по email и паролю
     * @param email почта
     * @param password пароль
     * @return возвращает Optional пользователя
     */
    Optional<User> findUserByEmailAndPassword(String email, String password);

    /**
     * Метод находит пользователя по id
     * @param id идентификатор
     * @return возвращает Optional пользователя
     */
    Optional<User> findUserById(int id);

    /**
     * Метод создает пользователя
     * @param rs результат выполнения sql-запроса
     * @return возвращает пользователя
     */
    User createUser(ResultSet rs) throws SQLException;

    /**
     * Метод удаляет всех пользователей
     */
    void deleteAll();
}
