package com.librarymanagement.factory;

import com.librarymanagement.model.Book;

public class BookFactory {
    public Book createBook(String isbn, String title, String author, int publicationYear) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN is required");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author is required");
        }
        return new Book(isbn.trim(), title.trim(), author.trim(), publicationYear);
    }
}

