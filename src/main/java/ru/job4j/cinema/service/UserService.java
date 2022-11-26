package ru.job4j.cinema.service;

import ru.job4j.cinema.model.User;

import java.util.Optional;

/**
 * Интерфейс описывает методы сервиса, через который происходит взаимодействие с хранилищем пользователей
 * @author Denis Kalchenko
 * @version 1.0
 */
public interface UserService {

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
     * @param userId идентификатор
     * @return возвращает Optional пользователя
     */
    Optional<User> findUserById(int userId);
}
