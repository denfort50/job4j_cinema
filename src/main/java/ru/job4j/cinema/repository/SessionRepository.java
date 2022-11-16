package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface SessionRepository {

    Optional<Session> add(Session session);

    List<Session> findAll();

    Optional<Session> findById(int id);

    Session createSession(ResultSet it) throws SQLException;

    void deleteAll();
}
