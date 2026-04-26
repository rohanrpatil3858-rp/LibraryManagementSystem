package com.librarymanagement.repository;

import com.librarymanagement.model.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> booksByIsbn = new HashMap<>();

    @Override
    public Book save(Book book) {
        booksByIsbn.put(book.getIsbn(), book);
        return book;
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return Optional.ofNullable(booksByIsbn.get(isbn));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(booksByIsbn.values());
    }

    @Override
    public boolean deleteByIsbn(String isbn) {
        return booksByIsbn.remove(isbn) != null;
    }
}

