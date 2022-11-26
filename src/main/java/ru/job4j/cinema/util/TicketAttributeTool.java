package ru.job4j.cinema.util;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

/**
 * Утилитный класс, предназначенный для получения и добавления атрибута "билет"
 * @author Denis Kalchenko
 * @version 1.0
 */
public final class TicketAttributeTool {

    private TicketAttributeTool() {
        throw new UnsupportedOperationException("This is utility class and it cannot be instantiated");
    }

    public static void addAttributeTicket(Model model, HttpSession session) {
        int cellId = getAttributeTicket(session);
        model.addAttribute("ticketId", cellId);
    }

    public static int getAttributeTicket(HttpSession session) {
        return (int) session.getAttribute("ticketId");
    }
}
