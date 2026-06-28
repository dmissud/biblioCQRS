package com.bibliocqrs.core.events;

import java.time.Instant;
import java.util.UUID;

public record OuvrageReferenceEvent(
        UUID eventId,
        Instant occurredOn,
        String isbn,
        String titre,
        String auteur
) implements DomainEvent {
    public OuvrageReferenceEvent(String isbn, String titre, String auteur) {
        this(UUID.randomUUID(), Instant.now(), isbn, titre, auteur);
    }
}
