package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessionControllerTest {

    @Test
    void whenMovieSessionsThenSuccess() {
        Session session1 = new Session(1, "Cinema_1", null);
        Session session2 = new Session(2, "Cinema_2", null);
        Session session3 = new Session(3, "Cinema_3", null);
        List<Session> sessionList =  List.of(session1, session2, session3);
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        SessionController sessionController = new SessionController(sessionService, ticketService);
        when(sessionService.findAll()).thenReturn(sessionList);
        String page = sessionController.movieSessions(model, session);
        verify(model).addAttribute("movieSessions", sessionList);
        assertThat(page).isEqualTo("mainPage");
    }

    @Test
    void whenSelectRowThenSuccess() {
        Session session1 = new Session(1, "Cinema_1", null);
        List<Integer> freeRows = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        SessionController sessionController = new SessionController(sessionService, ticketService);
        when(sessionService.findById(session1.getId())).thenReturn(Optional.of(session1));
        when(ticketService.getFreeRows(session1)).thenReturn(freeRows);
        String page = sessionController.selectRow(model, session1.getId(), session);
        verify(model).addAttribute("movieSession", session1);
        verify(model).addAttribute("rows", freeRows);
        assertThat(page).isEqualTo("selectRow");
    }

    @Test
    void whenSelectCellThenSuccess() {
        Session session1 = new Session(1, "Cinema_1", null);
        int row = 1;
        Set<Integer> freeCells = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        SessionController sessionController = new SessionController(sessionService, ticketService);
        when(session.getAttribute("movieSessionId")).thenReturn(session1.getId());
        when(sessionService.findById(session1.getId())).thenReturn(Optional.of(session1));
        when(ticketService.getFreeCells(session1, row)).thenReturn(freeCells);
        String page = sessionController.selectCell(model, row, session);
        verify(model).addAttribute("movieSession", session1);
        verify(model).addAttribute("rowId", row);
        verify(model).addAttribute("cells", freeCells);
        assertThat(page).isEqualTo("selectCell");
    }

    @Test
    void whenCreateTicketThenSuccess() {
        int row = 1;
        int cell = 1;
        Session session1 = new Session(1, "Cinema_1", null);
        User user = new User(1, "Denis", "denfort50@yandex.ru", "+79999999999", "password");
        Ticket ticket = new Ticket(1, session1.getId(), row, cell, user.getId());
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        SessionController sessionController = new SessionController(sessionService, ticketService);
        when(session.getAttribute("movieSessionId")).thenReturn(session1.getId());
        when(session.getAttribute("rowId")).thenReturn(row);
        when(session.getAttribute("user")).thenReturn(user);
        when(sessionService.findById(session1.getId())).thenReturn(Optional.of(session1));
        when(ticketService.createTicket(session1.getId(), row, cell, user.getId())).thenReturn(Optional.of(ticket));
        String page = sessionController.createTicket(model, cell, session);
        verify(model).addAttribute("movieSession", session1);
        verify(model).addAttribute("rowId", row);
        verify(model).addAttribute("cellId", cell);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("ticketId", ticket.getId());
        assertThat(page).isEqualTo("redirect:/purchaseSuccess");
    }

    @Test
    void whenCreateTicketThenFail() {
        int row = 1;
        int cell = 1;
        Session session1 = new Session(1, "Cinema_1", null);
        User user = new User(1, "Denis", "denfort50@yandex.ru", "+79999999999", "password");
        Ticket ticket = new Ticket(1, session1.getId(), row, cell, user.getId());
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        SessionController sessionController = new SessionController(sessionService, ticketService);
        when(session.getAttribute("movieSessionId")).thenReturn(session1.getId());
        when(session.getAttribute("rowId")).thenReturn(row);
        when(session.getAttribute("user")).thenReturn(user);
        when(sessionService.findById(session1.getId())).thenReturn(Optional.of(session1));
        when(ticketService.createTicket(session1.getId(), row, cell, user.getId())).thenReturn(Optional.empty());
        String page = sessionController.createTicket(model, cell, session);
        verify(model).addAttribute("movieSession", session1);
        verify(model).addAttribute("rowId", row);
        verify(model).addAttribute("cellId", cell);
        verify(model).addAttribute("message", "Выбранное место уже занято");
        assertThat(page).isEqualTo("redirect:/purchaseFail");
    }

    @Test
    void whenAddSessionThenSuccess() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        SessionController sessionController = new SessionController(sessionService, ticketService);
        String page = sessionController.addSession(model, session);
        assertThat(page).isEqualTo("addSession");
    }

    @Test
    void whenCreateSessionThenSuccess() throws IOException {
        Session session1 = new Session(1, "Cinema_1", null);
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        SessionController sessionController = new SessionController(sessionService, ticketService);
        MultipartFile multipartFile = mock(MultipartFile.class);
        String page = sessionController.createSession(session1, multipartFile);
        assertThat(page).isEqualTo("redirect:/mainPage");
    }

    @Test
    void whenPurchaseFailThenSuccess() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        SessionController sessionController = new SessionController(sessionService, ticketService);
        String page = sessionController.purchaseFail(model, session);
        assertThat(page).isEqualTo("purchaseFail");
    }

    @Test
    void whenPurchaseSuccessThenSuccess() {
        int row = 1;
        int cell = 1;
        Session session1 = new Session(1, "Cinema_1", null);
        User user = new User(1, "Denis", "denfort50@yandex.ru", "+79999999999", "password");
        Ticket ticket = new Ticket(1, session1.getId(), row, cell, user.getId());
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        when(session.getAttribute("movieSessionId")).thenReturn(session1.getId());
        when(sessionService.findById(session1.getId())).thenReturn(Optional.of(session1));
        when(session.getAttribute("rowId")).thenReturn(row);
        when(session.getAttribute("cellId")).thenReturn(cell);
        when(session.getAttribute("ticketId")).thenReturn(ticket.getId());
        when(session.getAttribute("user")).thenReturn(user);
        SessionController sessionController = new SessionController(sessionService, ticketService);
        String page = sessionController.purchaseSuccess(model, session);
        assertThat(page).isEqualTo("purchaseSuccess");
    }
}