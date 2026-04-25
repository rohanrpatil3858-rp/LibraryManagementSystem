package com.librarymanagement.model;

public class Book {
    private final String isbn;
    private String title;
    private String author;
    private int publicationYear;
    private BookStatus status;

    public Book(String isbn, String title, String author, int publicationYear) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.status = BookStatus.AVAILABLE;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public BookStatus getStatus() {
        return status;
    }

    public boolean isAvailable() {
        return status == BookStatus.AVAILABLE;
    }

    public void markBorrowed() {
        this.status = BookStatus.BORROWED;
    }

    public void markAvailable() {
        this.status = BookStatus.AVAILABLE;
    }

    public void updateDetails(String title, String author, int publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }
}

