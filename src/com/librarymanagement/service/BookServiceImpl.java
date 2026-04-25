package com.librarymanagement.service;

import com.librarymanagement.exception.NotFoundException;
import com.librarymanagement.factory.BookFactory;
import com.librarymanagement.model.Book;
import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.search.BookSearchStrategy;
import com.librarymanagement.search.BookSearchStrategyFactory;
import com.librarymanagement.search.SearchType;

import java.util.List;
import java.util.logging.Logger;

public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = Logger.getLogger(BookServiceImpl.class.getName());

    private final BookRepository bookRepository;
    private final BookFactory bookFactory;
    private final BookSearchStrategyFactory searchStrategyFactory;

    public BookServiceImpl(BookRepository bookRepository,
                           BookFactory bookFactory,
                           BookSearchStrategyFactory searchStrategyFactory) {
        this.bookRepository = bookRepository;
        this.bookFactory = bookFactory;
        this.searchStrategyFactory = searchStrategyFactory;
    }

    @Override
    public Book addBook(String isbn, String title, String author, int publicationYear) {
        Book book = bookFactory.createBook(isbn, title, author, publicationYear);
        LOGGER.info("Adding book with ISBN " + isbn);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(String isbn, String title, String author, int publicationYear) {
        Book existingBook = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Book not found for ISBN " + isbn));

        existingBook.updateDetails(title, author, publicationYear);
        LOGGER.info("Updated book with ISBN " + isbn);
        return bookRepository.save(existingBook);
    }

    @Override
    public boolean removeBook(String isbn) {
        LOGGER.info("Removing book with ISBN " + isbn);
        return bookRepository.deleteByIsbn(isbn);
    }

    @Override
    public List<Book> searchBooks(SearchType searchType, String query) {
        BookSearchStrategy strategy = searchStrategyFactory.getStrategy(searchType);
        return strategy.search(bookRepository.findAll(), query == null ? "" : query.trim());
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}

