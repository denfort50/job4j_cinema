package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.PostgreUserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final PostgreUserRepository store;

    public UserServiceImpl(PostgreUserRepository store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return store.findUserByEmailAndPassword(email, password);
    }

    public Optional<User> findUserById(int userId) {
        return store.findUserById(userId);
    }
}
