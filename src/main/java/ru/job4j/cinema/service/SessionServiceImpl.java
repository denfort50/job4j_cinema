package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.SessionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository store;

    public SessionServiceImpl(SessionRepository store) {
        this.store = store;
    }

    public List<Session> findAll() {
        return store.findAll();
    }

    public Optional<Session> findById(int id) {
        return store.findById(id);
    }

    public void add(Session session) {
        store.add(session);
    }
}
