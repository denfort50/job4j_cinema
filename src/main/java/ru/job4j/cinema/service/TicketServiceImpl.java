package ru.job4j.cinema.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.PostgreTicketRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TicketServiceImpl implements TicketService {

    private static final int STUB_ID = 0;

    private final int rowCount;

    private final List<Integer> cellList;

    private final PostgreTicketRepository store;

    public TicketServiceImpl(PostgreTicketRepository store,
                             @Value("${hall.row.count}") int rowCount,
                             @Value("${hall.cell.count}") int cellCount) {
        this.store = store;
        this.rowCount = rowCount;
        this.cellList = IntStream.rangeClosed(1, cellCount).boxed().toList();
    }

    public Optional<Ticket> createTicket(int movieSessionId, int row, int cell, int userId) {
        Ticket ticket = new Ticket(STUB_ID, movieSessionId, row, cell, userId);
        return store.add(ticket);
    }

    public List<Integer> getFreeRows(Session session) {
        List<Ticket> purchased = findTicketsBySessionId(session);
        Map<Integer, Set<Integer>> nonPurchasedCellMap = createCellMap();
        purchased.forEach(ticket -> nonPurchasedCellMap.get(ticket.getPosRow()).remove(ticket.getCell()));
        return nonPurchasedCellMap.entrySet().stream()
                .filter(row -> row.getValue().size() > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Set<Integer> getFreeCells(Session session, int row) {
        List<Ticket> purchased = findTicketsBySessionIdAndPosRow(session, row);
        Set<Integer> freeCells = new HashSet<>(cellList);
        purchased.forEach(ticket -> freeCells.remove(ticket.getCell()));
        return freeCells;
    }

    public List<Ticket> findTicketsBySessionId(Session session) {
        return store.findTicketsBySessionId(session);
    }

    public List<Ticket> findTicketsBySessionIdAndPosRow(Session session, int posRow) {
        return store.findTicketsBySessionIdAndPosRow(session, posRow);
    }

    private Map<Integer, Set<Integer>> createCellMap() {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int row = 1; row <= rowCount; row++) {
            map.put(row, new HashSet<>(cellList));
        }
        return map;
    }
}
