package ru.job4j.cinema.util;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public final class CellAttributeTool {

    private CellAttributeTool() {
        throw new UnsupportedOperationException("This is utility class and it cannot be instantiated");
    }

    public static void addAttributeCell(Model model, HttpSession session) {
        int cellId = getAttributeCell(session);
        model.addAttribute("cellId", cellId);
    }

    public static int getAttributeCell(HttpSession session) {
        return (int) session.getAttribute("cellId");
    }
}
