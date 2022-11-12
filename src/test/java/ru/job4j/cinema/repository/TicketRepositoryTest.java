package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.config.DataSourceConfigForTesting;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class TicketRepositoryTest {

    private final DataSourceConfigForTesting dataSourceConfigForTesting = new DataSourceConfigForTesting();

    private final TicketRepository ticketRepository = new TicketRepository(dataSourceConfigForTesting.getDataSource());
    private final UserRepository userRepository = new UserRepository(dataSourceConfigForTesting.getDataSource());
    private final SessionRepository sessionRepository = new SessionRepository(dataSourceConfigForTesting.getDataSource());

    @AfterEach
    void cleanTable() {
        ticketRepository.deleteAll();
        userRepository.deleteAll();
        sessionRepository.deleteAll();
    }

    @Test
    void whenFindTicketsBySessionIdThenSuccess() throws IOException {
        User user1 = new User(0, "Denis", "denfort50@yandex.ru", "+79999999999", "password");
        User user2 = new User(0, "James", "james_bond@yandex.ru", "+77777777777", "password");
        user1 = userRepository.add(user1).orElseThrow();
        user2 = userRepository.add(user2).orElseThrow();
        Session session = new Session(0, "Cinema", loadPictureForTest());
        session = sessionRepository.add(session).orElseThrow();
        Ticket ticket1 = new Ticket(0, session.getId(), 1, 1, user1.getId());
        Ticket ticket2 = new Ticket(0, session.getId(), 1, 2, user2.getId());
        ticket1 = ticketRepository.add(ticket1).orElseThrow();
        ticket2 = ticketRepository.add(ticket2).orElseThrow();
        List<Ticket> ticketsInDB = ticketRepository.findTicketsBySessionId(session);
        assertThat(ticketsInDB.stream().map(Ticket::getCell).collect(Collectors.toList()))
                .contains(ticket1.getCell(), ticket2.getCell());
    }

    @Test
    void whenFindTicketsBySessionIdAndPosRowThenSuccess() throws IOException {
        User user1 = new User(0, "Denis", "denfort50@yandex.ru", "+79999999999", "password");
        User user2 = new User(0, "James", "james_bond@yandex.ru", "+77777777777", "password");
        User user3 = new User(0, "Tomas", "tomas_anderson@yandex.ru", "+75555555555", "password");
        user1 = userRepository.add(user1).orElseThrow();
        user2 = userRepository.add(user2).orElseThrow();
        user3 = userRepository.add(user3).orElseThrow();
        Session session = new Session(0, "Cinema", loadPictureForTest());
        session = sessionRepository.add(session).orElseThrow();
        int posRow = 3;
        Ticket ticket1 = new Ticket(0, session.getId(), posRow, 1, user1.getId());
        Ticket ticket2 = new Ticket(0, session.getId(), posRow, 2, user2.getId());
        Ticket ticket3 = new Ticket(0, session.getId(), posRow, 3, user3.getId());
        ticket1 = ticketRepository.add(ticket1).orElseThrow();
        ticket2 = ticketRepository.add(ticket2).orElseThrow();
        ticket3 = ticketRepository.add(ticket3).orElseThrow();
        List<Ticket> ticketsInDB = ticketRepository.findTicketsBySessionIdAndPosRow(session, posRow);
        assertThat(ticketsInDB.stream().map(Ticket::getCell).collect(Collectors.toList()))
                .contains(ticket1.getCell(), ticket2.getCell(), ticket3.getCell());
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