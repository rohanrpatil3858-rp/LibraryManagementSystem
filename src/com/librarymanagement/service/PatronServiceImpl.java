package com.librarymanagement.service;

import com.librarymanagement.exception.NotFoundException;
import com.librarymanagement.factory.PatronFactory;
import com.librarymanagement.model.LoanRecord;
import com.librarymanagement.model.Patron;
import com.librarymanagement.repository.LoanRepository;
import com.librarymanagement.repository.PatronRepository;

import java.util.List;
import java.util.logging.Logger;

public class PatronServiceImpl implements PatronService {
    private static final Logger LOGGER = Logger.getLogger(PatronServiceImpl.class.getName());

    private final PatronRepository patronRepository;
    private final PatronFactory patronFactory;
    private final LoanRepository loanRepository;

    public PatronServiceImpl(PatronRepository patronRepository,
                             PatronFactory patronFactory,
                             LoanRepository loanRepository) {
        this.patronRepository = patronRepository;
        this.patronFactory = patronFactory;
        this.loanRepository = loanRepository;
    }

    @Override
    public Patron addPatron(String patronId, String name, String email) {
        Patron patron = patronFactory.createPatron(patronId, name, email);
        LOGGER.info("Adding patron " + patronId);
        return patronRepository.save(patron);
    }

    @Override
    public Patron updatePatron(String patronId, String name, String email) {
        Patron existing = getPatronById(patronId);
        existing.updateDetails(name, email);
        LOGGER.info("Updated patron " + patronId);
        return patronRepository.save(existing);
    }

    @Override
    public Patron getPatronById(String patronId) {
        return patronRepository.findById(patronId)
                .orElseThrow(() -> new NotFoundException("Patron not found for id " + patronId));
    }

    @Override
    public List<LoanRecord> getBorrowingHistory(String patronId) {
        getPatronById(patronId);
        return loanRepository.findAllByPatron(patronId);
    }
}

