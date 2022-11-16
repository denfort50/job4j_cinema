package ru.job4j.cinema.repository;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostgreTicketRepository implements TicketRepository {

    private static final String ADD_TICKET = "INSERT INTO tickets(session_id, pos_row, cell, user_id) VALUES (?, ?, ?, ?)";
    private static final String FIND_TICKETS_BY_SESSION_ID = "SELECT * FROM tickets WHERE session_id = ?";
    private static final String FIND_TICKETS_BY_SESSION_ID_AND_POS_ROW = "SELECT * FROM tickets WHERE session_id = ? AND pos_row = ?";
    private static final String DELETE_ALL_TICKETS = "DELETE FROM tickets";

    private static final Logger LOG = LogManager.getLogger(PostgreTicketRepository.class.getName());

    private final DataSource pool;

    public PostgreTicketRepository(DataSource pool) {
        this.pool = pool;
    }

    public Optional<Ticket> add(Ticket ticket) {
        Optional<Ticket> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD_TICKET,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getPosRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUserId());
            ps.execute();
            try (ResultSet it = ps.getGeneratedKeys()) {
                if (it.next()) {
                    ticket.setId(it.getInt("id"));
                    result = Optional.of(ticket);
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return result;
    }

    public List<Ticket> findTicketsBySessionId(Session session) {
        List<Ticket> result = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_TICKETS_BY_SESSION_ID)) {
            ps.setInt(1, session.getId());
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    result.add(createTicket(session, it));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return result;
    }

    public List<Ticket> findTicketsBySessionIdAndPosRow(Session session, int posRow) {
        List<Ticket> result = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_TICKETS_BY_SESSION_ID_AND_POS_ROW)) {
            ps.setInt(1, session.getId());
            ps.setInt(2, posRow);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    result.add(createTicket(session, it));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return result;
    }

    public Ticket createTicket(Session session, ResultSet it) throws SQLException {
        return new Ticket(it.getInt("id"), session.getId(), it.getInt("pos_row"),
                it.getInt("cell"), it.getInt("user_id"));
    }

    public void deleteAll() {
        try (Connection cn = pool.getConnection();
             Statement statement = cn.createStatement()) {
            statement.execute(DELETE_ALL_TICKETS);
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
    }
}
