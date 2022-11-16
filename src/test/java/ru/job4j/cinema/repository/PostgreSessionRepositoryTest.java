package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.config.TestDataSourceConfig;
import ru.job4j.cinema.model.Session;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class PostgreSessionRepositoryTest {

    private final TestDataSourceConfig testDataSourceConfig = new TestDataSourceConfig();

    private final SessionRepository sessionRepository =
            new PostgreSessionRepository(testDataSourceConfig.getDataSource());

    @AfterEach
    void cleanTable() {
        sessionRepository.deleteAll();
    }

    @Test
    void whenAddThenSuccess() throws IOException {
        Session session = new Session(1, "Cinema", loadPictureForTest());
        sessionRepository.add(session);
        Session sessionInDB = sessionRepository.findById(session.getId()).orElseThrow();
        assertThat(sessionInDB.getName()).isEqualTo(session.getName());
    }

    @Test
    void whenFindAllThenSuccess() throws IOException {
        Session session1 = new Session(1, "Cinema", loadPictureForTest());
        Session session2 = new Session(2, "Movie", loadPictureForTest());
        sessionRepository.add(session1);
        sessionRepository.add(session2);
        List<Session> sessionsInDB = sessionRepository.findAll();
        assertThat(sessionsInDB.stream().map(Session::getName).collect(Collectors.toList()))
                .contains(session1.getName(), session2.getName());
    }

    private static byte[] loadPictureForTest() throws IOException {
        byte[] picture;
        BufferedImage bufferedImage = ImageIO.read(new File("src/test/resources/pictureForTest.jpg"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        picture = byteArrayOutputStream.toByteArray();
        return picture;
    }

}