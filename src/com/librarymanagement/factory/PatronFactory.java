package com.librarymanagement.factory;

import com.librarymanagement.model.Patron;

public class PatronFactory {
    public Patron createPatron(String patronId, String name, String email) {
        if (patronId == null || patronId.trim().isEmpty()) {
            throw new IllegalArgumentException("Patron ID is required");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Patron name is required");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Patron email is required");
        }
        return new Patron(patronId.trim(), name.trim(), email.trim());
    }
}

