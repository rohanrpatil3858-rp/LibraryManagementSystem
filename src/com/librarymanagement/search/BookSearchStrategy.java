package com.librarymanagement.search;

import com.librarymanagement.model.Book;

import java.util.Collection;
import java.util.List;

public interface BookSearchStrategy {
    List<Book> search(Collection<Book> books, String query);
}

