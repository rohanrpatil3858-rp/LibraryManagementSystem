package com.librarymanagement.repository;

import com.librarymanagement.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findByIsbn(String isbn);

    List<Book> findAll();

    boolean deleteByIsbn(String isbn);
}

