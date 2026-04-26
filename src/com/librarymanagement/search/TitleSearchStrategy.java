package com.librarymanagement.search;

import com.librarymanagement.model.Book;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TitleSearchStrategy implements BookSearchStrategy {
    @Override
    public List<Book> search(Collection<Book> books, String query) {
        String normalizedQuery = query.toLowerCase();
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(normalizedQuery))
                .collect(Collectors.toList());
    }
}

