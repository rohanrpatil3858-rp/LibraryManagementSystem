package com.librarymanagement.repository;

import com.librarymanagement.model.Patron;

import java.util.List;
import java.util.Optional;

public interface PatronRepository {
    Patron save(Patron patron);

    Optional<Patron> findById(String patronId);

    List<Patron> findAll();
}

