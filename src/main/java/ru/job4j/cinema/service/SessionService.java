package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Session;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс описывает методы сервиса, через который происходит взаимодействие с хранилищем сеансов
 * @author Denis Kalchenko
 * @version 1.0
 */
public interface SessionService {

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
     * Метод сохраняет сеанс
     * @param session сеанс
     */
    void add(Session session);
}
