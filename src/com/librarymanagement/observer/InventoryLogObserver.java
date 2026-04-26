package com.librarymanagement.observer;

import com.librarymanagement.model.Book;
import com.librarymanagement.model.BookStatus;
import com.librarymanagement.repository.BookRepository;

import java.util.List;
import java.util.logging.Logger;

public class InventoryLogObserver implements LendingObserver {
    private static final Logger LOGGER = Logger.getLogger(InventoryLogObserver.class.getName());
    private final BookRepository bookRepository;

    public InventoryLogObserver(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onLendingEvent(LendingEvent event) {
        List<Book> books = bookRepository.findAll();
        long availableCount = books.stream().filter(book -> book.getStatus() == BookStatus.AVAILABLE).count();
        long borrowedCount = books.size() - availableCount;

        LOGGER.info(String.format(
                "INVENTORY: available=%d, borrowed=%d after %s for ISBN %s",
                availableCount,
                borrowedCount,
                event.getEventType(),
                event.getIsbn()));
    }
}

