package com.librarymanagement.service;

import com.librarymanagement.model.Book;
import com.librarymanagement.search.SearchType;

import java.util.List;

public interface BookService {
    Book addBook(String isbn, String title, String author, int publicationYear);

    Book updateBook(String isbn, String title, String author, int publicationYear);

    boolean removeBook(String isbn);

    List<Book> searchBooks(SearchType searchType, String query);

    List<Book> getAllBooks();
}

