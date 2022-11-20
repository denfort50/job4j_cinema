package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;

import java.util.*;

public interface TicketService {

    Optional<Ticket> createTicket(int movieSessionId, int row, int cell, int userId);

    List<Integer> getFreeRows(Session session);

    Set<Integer> getFreeCells(Session session, int row);

    List<Ticket> findTicketsBySessionId(Session session);

    List<Ticket> findTicketsBySessionIdAndPosRow(Session session, int posRow);
}
