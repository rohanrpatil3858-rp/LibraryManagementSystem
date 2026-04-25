package com.librarymanagement.search;

import java.util.EnumMap;
import java.util.Map;

public class BookSearchStrategyFactory {
    private final Map<SearchType, BookSearchStrategy> strategies = new EnumMap<>(SearchType.class);

    public BookSearchStrategyFactory() {
        strategies.put(SearchType.TITLE, new TitleSearchStrategy());
        strategies.put(SearchType.AUTHOR, new AuthorSearchStrategy());
        strategies.put(SearchType.ISBN, new IsbnSearchStrategy());
    }

    public BookSearchStrategy getStrategy(SearchType searchType) {
        BookSearchStrategy strategy = strategies.get(searchType);
        if (strategy == null) {
            throw new IllegalArgumentException("No search strategy registered for " + searchType);
        }
        return strategy;
    }
}

