package com.librarymanagement.repository;

import com.librarymanagement.model.LoanRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryLoanRepository implements LoanRepository {
    private final List<LoanRecord> loans = new ArrayList<>();

    @Override
    public LoanRecord save(LoanRecord loanRecord) {
        if (!loans.contains(loanRecord)) {
            loans.add(loanRecord);
        }
        return loanRecord;
    }

    @Override
    public Optional<LoanRecord> findActiveLoan(String patronId, String isbn) {
        return loans.stream()
                .filter(loan -> loan.getPatronId().equals(patronId))
                .filter(loan -> loan.getIsbn().equals(isbn))
                .filter(LoanRecord::isActive)
                .findFirst();
    }

    @Override
    public List<LoanRecord> findActiveLoansByPatron(String patronId) {
        return loans.stream()
                .filter(loan -> loan.getPatronId().equals(patronId))
                .filter(LoanRecord::isActive)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanRecord> findAllByPatron(String patronId) {
        return loans.stream()
                .filter(loan -> loan.getPatronId().equals(patronId))
                .collect(Collectors.toList());
    }
}

