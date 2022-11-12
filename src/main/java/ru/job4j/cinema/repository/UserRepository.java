package ru.job4j.cinema.repository;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Repository
public class UserRepository {

    private static final String INSERT_USER = "INSERT INTO users(username, email, phone, password) VALUES (?, ?, ?, ?)";
    private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String DELETE_ALL_USERS = "DELETE FROM users";

    private static final Logger LOG = LogManager.getLogger(UserRepository.class.getName());

    private final DataSource pool;

    public UserRepository(DataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.execute();
            try (ResultSet it = ps.getGeneratedKeys()) {
                if (it.next()) {
                    user.setId(it.getInt(1));
                }
                result = Optional.of(user);
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return result;
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    result = Optional.of(createUser(it));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return result;
    }

    public Optional<User> findUserById(int userId) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_USER_BY_ID)) {
            ps.setInt(1, userId);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = Optional.of(createUser(it));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return result;
    }

    private User createUser(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id"), rs.getString("username"),
                rs.getString("email"), rs.getString("phone"),
                rs.getString("password"));
    }

    public void deleteAll() {
        try (Connection cn = pool.getConnection();
             Statement statement = cn.createStatement()) {
            statement.execute(DELETE_ALL_USERS);
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
    }
}
