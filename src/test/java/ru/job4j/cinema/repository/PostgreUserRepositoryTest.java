package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.config.TestDataSourceConfig;
import ru.job4j.cinema.model.User;

import static org.assertj.core.api.Assertions.*;

class PostgreUserRepositoryTest {

    private final TestDataSourceConfig testDataSourceConfig = new TestDataSourceConfig();

    private final UserRepository userRepository = new PostgreUserRepository(testDataSourceConfig.getDataSource());

    @AfterEach
    void cleanTable() {
        userRepository.deleteAll();
    }

    @Test
    void whenAddThenSuccess() {
        User user = new User(1, "Denis", "denfort50@yandex.ru", "+79999999999", "password");
        userRepository.add(user);
        User userInDB = userRepository.findUserById(user.getId()).orElseThrow();
        assertThat(userInDB.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void whenFindUserByEmailAndPasswordThenSuccess() {
        User user = new User(1, "Denis", "denfort50@yandex.ru", "+79999999999", "password");
        userRepository.add(user);
        User userInDB = userRepository.findUserByEmailAndPassword("denfort50@yandex.ru", "password").orElseThrow();
        assertThat(userInDB.getUsername()).isEqualTo(user.getUsername());
    }


}