package com.librarymanagement.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Patron {
    private final String patronId;
    private String name;
    private String email;
    private final Set<String> activeLoanIsbns;
    private final List<LoanRecord> borrowingHistory;

    public Patron(String patronId, String name, String email) {
        this.patronId = patronId;
        this.name = name;
        this.email = email;
        this.activeLoanIsbns = new HashSet<>();
        this.borrowingHistory = new ArrayList<>();
    }

    public String getPatronId() {
        return patronId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getActiveLoanIsbns() {
        return Collections.unmodifiableSet(activeLoanIsbns);
    }

    public List<LoanRecord> getBorrowingHistory() {
        return Collections.unmodifiableList(borrowingHistory);
    }

    public void updateDetails(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public boolean canBorrow(int maxAllowed) {
        return activeLoanIsbns.size() < maxAllowed;
    }

    public void addActiveLoan(String isbn) {
        activeLoanIsbns.add(isbn);
    }

    public void removeActiveLoan(String isbn) {
        activeLoanIsbns.remove(isbn);
    }

    public void addHistoryEntry(LoanRecord loanRecord) {
        borrowingHistory.add(loanRecord);
    }
}

