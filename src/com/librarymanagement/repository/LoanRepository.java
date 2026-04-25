package com.librarymanagement.repository;

import com.librarymanagement.model.LoanRecord;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    LoanRecord save(LoanRecord loanRecord);

    Optional<LoanRecord> findActiveLoan(String patronId, String isbn);

    List<LoanRecord> findActiveLoansByPatron(String patronId);

    List<LoanRecord> findAllByPatron(String patronId);
}

