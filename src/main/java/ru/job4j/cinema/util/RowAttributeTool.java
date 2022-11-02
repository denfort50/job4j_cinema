package ru.job4j.cinema.util;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public final class RowAttributeTool {

    private RowAttributeTool() {
        throw new UnsupportedOperationException("This is utility class and it cannot be instantiated");
    }

    public static void addAttributeRow(Model model, HttpSession session) {
        int cellId = getAttributeRow(session);
        model.addAttribute("rowId", cellId);
    }

    public static int getAttributeRow(HttpSession session) {
        return (int) session.getAttribute("rowId");
    }
}
