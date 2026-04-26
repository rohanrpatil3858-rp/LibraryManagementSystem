package com.librarymanagement.service;

import com.librarymanagement.model.LoanRecord;

public interface LendingService {
    LoanRecord checkoutBook(String patronId, String isbn);

    LoanRecord returnBook(String patronId, String isbn);
}

