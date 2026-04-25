package com.librarymanagement.observer;

import java.time.LocalDate;

public class LendingEvent {
    private final LendingEventType eventType;
    private final String patronId;
    private final String isbn;
    private final LocalDate dueDate;

    public LendingEvent(LendingEventType eventType, String patronId, String isbn, LocalDate dueDate) {
        this.eventType = eventType;
        this.patronId = patronId;
        this.isbn = isbn;
        this.dueDate = dueDate;
    }

    public LendingEventType getEventType() {
        return eventType;
    }

    public String getPatronId() {
        return patronId;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}

