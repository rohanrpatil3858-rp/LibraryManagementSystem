package com.librarymanagement.repository;

import com.librarymanagement.model.Patron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryPatronRepository implements PatronRepository {
    private final Map<String, Patron> patronsById = new HashMap<>();

    @Override
    public Patron save(Patron patron) {
        patronsById.put(patron.getPatronId(), patron);
        return patron;
    }

    @Override
    public Optional<Patron> findById(String patronId) {
        return Optional.ofNullable(patronsById.get(patronId));
    }

    @Override
    public List<Patron> findAll() {
        return new ArrayList<>(patronsById.values());
    }
}

