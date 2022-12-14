package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Класс представляет собой модель данных билета
 * @author Denis Kalchenko
 * @version 1.0
 */
public class Ticket {

    /**
     * Идентификатор билета
     */
    private int id;

    /**
     * Идентификатор сеанса
     */
    private int sessionId;

    /**
     * Ряд
     */
    private int posRow;

    /**
     * Место
     */
    private int cell;

    /**
     * Идентификатор пользователя
     */
    private int userId;

    public Ticket() { }

    public Ticket(int id, int sessionId, int posRow, int cell, int userId) {
        this.id = id;
        this.sessionId = sessionId;
        this.posRow = posRow;
        this.cell = cell;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getPosRow() {
        return posRow;
    }

    public void setPosRow(int posRow) {
        this.posRow = posRow;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id
                && sessionId == ticket.sessionId
                && posRow == ticket.posRow
                && cell == ticket.cell
                && userId == ticket.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sessionId, posRow, cell, userId);
    }
}
