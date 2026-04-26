package com.librarymanagement.service;

import com.librarymanagement.exception.BusinessRuleViolationException;
import com.librarymanagement.exception.NotFoundException;
import com.librarymanagement.model.Book;
import com.librarymanagement.model.LoanRecord;
import com.librarymanagement.model.Patron;
import com.librarymanagement.observer.LendingEvent;
import com.librarymanagement.observer.LendingEventPublisher;
import com.librarymanagement.observer.LendingEventType;
import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.repository.LoanRepository;
import com.librarymanagement.repository.PatronRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class LendingServiceImpl implements LendingService {
    private static final Logger LOGGER = Logger.getLogger(LendingServiceImpl.class.getName());

    private static final int MAX_ACTIVE_LOANS = 2;

    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final LoanRepository loanRepository;
    private final LendingEventPublisher eventPublisher;

    public LendingServiceImpl(BookRepository bookRepository,
                              PatronRepository patronRepository,
                              LoanRepository loanRepository,
                              LendingEventPublisher eventPublisher) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.loanRepository = loanRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public LoanRecord checkoutBook(String patronId, String isbn) {
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new NotFoundException("Patron not found for id " + patronId));

        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Book not found for ISBN " + isbn));

        if (!book.isAvailable()) {
            throw new BusinessRuleViolationException("Book is currently borrowed: " + isbn);
        }

        List<LoanRecord> activeLoans = loanRepository.findActiveLoansByPatron(patronId);
        if (activeLoans.size() >= MAX_ACTIVE_LOANS || !patron.canBorrow(MAX_ACTIVE_LOANS)) {
            throw new BusinessRuleViolationException("Patron has reached active loan limit of " + MAX_ACTIVE_LOANS);
        }

        LocalDate checkoutDate = LocalDate.now();
        LocalDate dueDate = checkoutDate.plusMonths(1);
        LoanRecord loanRecord = new LoanRecord(patronId, isbn, checkoutDate, dueDate);

        book.markBorrowed();
        patron.addActiveLoan(isbn);
        patron.addHistoryEntry(loanRecord);

        loanRepository.save(loanRecord);
        bookRepository.save(book);
        patronRepository.save(patron);

        eventPublisher.publish(new LendingEvent(LendingEventType.CHECKOUT, patronId, isbn, dueDate));
        LOGGER.info(String.format("Checkout successful: patron=%s, isbn=%s, dueDate=%s", patronId, isbn, dueDate));

        return loanRecord;
    }

    @Override
    public LoanRecord returnBook(String patronId, String isbn) {
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new NotFoundException("Patron not found for id " + patronId));

        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Book not found for ISBN " + isbn));

        LoanRecord activeLoan = loanRepository.findActiveLoan(patronId, isbn)
                .orElseThrow(() -> new BusinessRuleViolationException(
                        "No active loan found for patron " + patronId + " and ISBN " + isbn));

        activeLoan.markReturned(LocalDate.now());
        book.markAvailable();
        patron.removeActiveLoan(isbn);

        loanRepository.save(activeLoan);
        bookRepository.save(book);
        patronRepository.save(patron);

        eventPublisher.publish(new LendingEvent(LendingEventType.RETURN, patronId, isbn, null));
        LOGGER.info(String.format("Return successful: patron=%s, isbn=%s", patronId, isbn));

        return activeLoan;
    }
}

