package ru.job4j.cinema.repository;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostgreSessionRepository implements SessionRepository {

    private final static String FIND_ALL = "SELECT * FROM sessions";
    private final static String FIND_BY_ID = "SELECT * FROM sessions WHERE id = (?)";
    private final static String ADD = "INSERT INTO sessions(name, photo) VALUES (?, ?)";
    private final static String DELETE_ALL = "DELETE FROM sessions";

    private static final Logger LOG = LogManager.getLogger(PostgreSessionRepository.class.getName());

    private final DataSource pool;

    public PostgreSessionRepository(DataSource pool) {
        this.pool = pool;
    }

    public Optional<Session> add(Session session) {
        Optional<Session> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, session.getName());
            ps.setBytes(2, session.getPhoto());
            ps.execute();
            try (ResultSet it = ps.getGeneratedKeys()) {
                if (it.next()) {
                    session.setId(it.getInt(1));
                }
                result = Optional.of(session);
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return result;
    }

    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    sessions.add(createSession(it));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return sessions;
    }

    public Optional<Session> findById(int id) {
        Optional<Session> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = Optional.of(createSession(it));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return result;
    }

    public Session createSession(ResultSet it) throws SQLException {
        return new Session(it.getInt("id"), it.getString("name"), it.getBytes("photo"));
    }

    public void deleteAll() {
        try (Connection cn = pool.getConnection();
             Statement statement = cn.createStatement()) {
            statement.execute(DELETE_ALL);
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
    }
}
