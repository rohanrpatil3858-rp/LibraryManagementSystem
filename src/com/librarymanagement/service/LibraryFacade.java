package com.librarymanagement.service;

public class LibraryFacade {
    private final BookService bookService;
    private final PatronService patronService;
    private final LendingService lendingService;

    public LibraryFacade(BookService bookService, PatronService patronService, LendingService lendingService) {
        this.bookService = bookService;
        this.patronService = patronService;
        this.lendingService = lendingService;
    }

    public BookService books() {
        return bookService;
    }

    public PatronService patrons() {
        return patronService;
    }

    public LendingService lending() {
        return lendingService;
    }
}

