package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;

import java.util.*;

/**
 * Интерфейс описывает методы сервиса, через который происходит взаимодействие с хранилищем билетов
 * @author Denis Kalchenko
 * @version 1.0
 */
public interface TicketService {

    /**
     * Метод создает и сохраняет билет
     * @param movieSessionId идентификатор сеанса
     * @param row ряд
     * @param cell место
     * @param userId идентификатор пользователя
     * @return возвращает Optional билета
     */
    Optional<Ticket> createTicket(int movieSessionId, int row, int cell, int userId);

    /**
     * Метод находит ряды, со свободными местами
     * @param session сеанс
     * @return возвращает номера рядов в виде коллекции, реализующей интерфейс List
     */
    List<Integer> getFreeRows(Session session);

    /**
     * Метод находят свободные места
     * @param session сеанс
     * @param row ряд
     * @return возвращает номера мест в виде коллекции, реализующей интерфейс Set
     */
    Set<Integer> getFreeCells(Session session, int row);

    /**
     * Метод находит купленные билеты по сеансу
     * @param session сеанс
     * @return возвращает список билетов в виде коллекции, реализующей интерфейс List
     */
    List<Ticket> findTicketsBySessionId(Session session);

    /**
     * Метод находит купленные билеты по сеансу и ряду
     * @param session сеанс
     * @param posRow ряд
     * @return возвращает список билетов в виде коллекции, реализующей интерфейс List
     */
    List<Ticket> findTicketsBySessionIdAndPosRow(Session session, int posRow);
}
