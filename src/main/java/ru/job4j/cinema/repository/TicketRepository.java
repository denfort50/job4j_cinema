package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;

import java.sql.*;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс описывает методы взаимодействия с хранилищем билетов
 * @author Denis Kalchenko
 * @version 1.0
 */
public interface TicketRepository {

    /**
     * Метод сохраняет билет
     * @param ticket билет
     * @return возвращает Optional билета
     */
    Optional<Ticket> add(Ticket ticket);

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

    /**
     * Метод создает билет
     * @param session сеанс
     * @param it результат выполнения sql-запроса
     * @return возвращает билет
     */
    Ticket createTicket(Session session, ResultSet it) throws SQLException;

    /**
     * Метод удаляет все билеты
     */
    void deleteAll();

}
