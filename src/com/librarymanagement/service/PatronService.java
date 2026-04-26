package com.librarymanagement.service;

import com.librarymanagement.model.LoanRecord;
import com.librarymanagement.model.Patron;

import java.util.List;

public interface PatronService {
    Patron addPatron(String patronId, String name, String email);

    Patron updatePatron(String patronId, String name, String email);

    Patron getPatronById(String patronId);

    List<LoanRecord> getBorrowingHistory(String patronId);
}

