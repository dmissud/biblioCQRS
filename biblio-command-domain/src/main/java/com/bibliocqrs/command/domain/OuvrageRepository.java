package com.bibliocqrs.command.domain;

import java.util.Optional;

public interface OuvrageRepository {
    void save(Ouvrage ouvrage);
    Optional<Ouvrage> findByIsbn(String isbn);
}
