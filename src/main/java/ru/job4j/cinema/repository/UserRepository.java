package ru.job4j.cinema.repository;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserRepository {

    private static final String INSERT_USER = "INSERT INTO users(username, email, phone) VALUES (?, ?, ?)";
    private static final String SELECT_USER_BY_EMAIL_AND_PHONE = "SELECT * FROM users WHERE email = ? AND phone = ?";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";

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

    public Optional<User> findUserByEmailAndPhone(String email, String phone) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_USER_BY_EMAIL_AND_PHONE)) {
            ps.setString(1, email);
            ps.setString(2, phone);
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
                rs.getString("email"), rs.getString("phone"));
    }
}
