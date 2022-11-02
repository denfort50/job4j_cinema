package ru.job4j.cinema.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static ru.job4j.cinema.util.CellAttributeTool.addAttributeCell;
import static ru.job4j.cinema.util.RowAttributeTool.addAttributeRow;
import static ru.job4j.cinema.util.RowAttributeTool.getAttributeRow;
import static ru.job4j.cinema.util.TicketAttributeTool.addAttributeTicket;
import static ru.job4j.cinema.util.UserAttributeTool.addAttributeUser;
import static ru.job4j.cinema.util.UserAttributeTool.getAttributeUser;

@Controller
public class SessionController {

    private final SessionService sessionService;
    private final TicketService ticketService;

    public SessionController(SessionService sessionService, TicketService ticketService) {
        this.sessionService = sessionService;
        this.ticketService = ticketService;
    }

    @GetMapping("/mainPage")
    public String movieSessions(Model model, HttpSession session) {
        model.addAttribute("movieSessions", sessionService.findAll());
        addAttributeUser(model, session);
        return "mainPage";
    }

    @GetMapping("/sessionPoster/{movieSessionId}")
    public ResponseEntity<Resource> download(@PathVariable("movieSessionId") Integer movieSessionId) {
        Optional<Session> movieSession = sessionService.findById(movieSessionId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(movieSession.orElseThrow().getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(movieSession.orElseThrow().getPhoto()));
    }

    @GetMapping("/selectRow/{movieSessionId}")
    public String selectRow(Model model, @PathVariable("movieSessionId") int id, HttpSession session) {
        Session movieSession = sessionService.findById(id).orElseThrow();
        session.setAttribute("movieSessionId", id);
        model.addAttribute("movieSession", movieSession);
        model.addAttribute("rows", ticketService.getFreeRows(movieSession));
        addAttributeUser(model, session);
        return "selectRow";
    }

    @GetMapping("/selectCell")
    public String selectCell(Model model, @RequestParam("row.id") int rowId, HttpSession session) {
        Session movieSession = getMovieSessionFromHttpSession(session);
        session.setAttribute("rowId", rowId);
        model.addAttribute("movieSession", movieSession);
        model.addAttribute("rowId", rowId);
        model.addAttribute("cells", ticketService.getFreeCells(movieSession, rowId));
        addAttributeUser(model, session);
        return "selectCell";
    }

    @PostMapping("/buyTicket")
    public String createTicket(Model model, @RequestParam("cell.id") int cellId, HttpSession session) {
        Session movieSession = getMovieSessionFromHttpSession(session);
        session.setAttribute("cellId", cellId);
        addAttributeRow(model, session);
        model.addAttribute("movieSession", movieSession);
        model.addAttribute("cellId", cellId);
        addAttributeUser(model, session);
        Optional<Ticket> purchasedTicket = ticketService.createTicket(
                movieSession.getId(), getAttributeRow(session), cellId, getAttributeUser(session).getId());
        if (purchasedTicket.isEmpty()) {
            model.addAttribute("message", "Выбранное место уже занято");
            return "redirect:/purchaseFail";
        } else {
            int ticketId = purchasedTicket.get().getId();
            session.setAttribute("ticketId", ticketId);
            model.addAttribute("ticketId", ticketId);
        }
        return "redirect:/purchaseSuccess";
    }

    @GetMapping("/addSession")
    public String addSession(Model model, HttpSession session) {
        model.addAttribute("movieSession", new Session(0, "Название киносеанса", null));
        addAttributeUser(model, session);
        return "addSession";
    }

    @PostMapping("/createSession")
    public String createSession(@ModelAttribute Session movieSession,
                                @RequestParam("file") MultipartFile file) throws IOException {
        movieSession.setPhoto(file.getBytes());
        sessionService.add(movieSession);
        return "redirect:/mainPage";
    }

    @GetMapping("/purchaseFail")
    public String purchaseFail(Model model, HttpSession session) {
        addAttributeUser(model, session);
        return "purchaseFail";
    }

    @GetMapping("/purchaseSuccess")
    public String purchaseSuccess(Model model, HttpSession session) {
        model.addAttribute("movieSession", getMovieSessionFromHttpSession(session));
        addAttributeRow(model, session);
        addAttributeCell(model, session);
        addAttributeTicket(model, session);
        addAttributeUser(model, session);
        return "purchaseSuccess";
    }

    private Session getMovieSessionFromHttpSession(HttpSession session) {
        return sessionService.findById((int) session.getAttribute("movieSessionId")).orElseThrow();
    }
}
