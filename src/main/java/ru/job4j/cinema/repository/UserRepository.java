package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.sql.*;
import java.util.Optional;

public interface UserRepository {

    Optional<User> add(User user);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    Optional<User> findUserById(int id);

    User createUser(ResultSet rs) throws SQLException;

    void deleteAll();
}
