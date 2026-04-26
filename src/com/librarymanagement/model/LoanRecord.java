package com.librarymanagement.model;

import java.time.LocalDate;

public class LoanRecord {
    private final String patronId;
    private final String isbn;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    public LoanRecord(String patronId, String isbn, LocalDate checkoutDate, LocalDate dueDate) {
        this.patronId = patronId;
        this.isbn = isbn;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }

    public String getPatronId() {
        return patronId;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isActive() {
        return returnDate == null;
    }

    public void markReturned(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}

