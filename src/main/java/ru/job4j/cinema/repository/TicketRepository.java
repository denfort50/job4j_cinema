package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    Optional<Ticket> add(Ticket ticket);

    List<Ticket> findTicketsBySessionId(Session session);

    List<Ticket> findTicketsBySessionIdAndPosRow(Session session, int posRow);

    Ticket createTicket(Session session, ResultSet it) throws SQLException;

    void deleteAll();

}
