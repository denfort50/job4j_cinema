package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Session;

import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс описывает методы взаимодействия с хранилищем сеансов
 * @author Denis Kalchenko
 * @version 1.0
 */
public interface SessionRepository {

    /**
     * Метод сохраняет сеанс
     * @param session сеанс
     */
    Optional<Session> add(Session session);

    /**
     * Метод находит все сеансы
     * @return возвращает все сеансы в виде коллекции, реализующей интерфейс List
     */
    List<Session> findAll();

    /**
     * Метод находит сеанс по id
     * @param id идентификатор сеанса
     * @return возвращает Optional сеанса
     */
    Optional<Session> findById(int id);

    /**
     * Метод создает сеанс
     * @param it результат выполнения sql-запроса
     * @return сеанс
     */
    Session createSession(ResultSet it) throws SQLException;

    /**
     * Метод удаляет все сеансы
     */
    void deleteAll();
}
