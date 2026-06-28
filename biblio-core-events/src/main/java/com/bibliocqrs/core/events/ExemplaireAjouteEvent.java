package com.bibliocqrs.core.events;

import java.time.Instant;
import java.util.UUID;

public record ExemplaireAjouteEvent(
        UUID eventId,
        Instant occurredOn,
        String isbnOuvrage,
        UUID exemplaireId,
        String salle,
        String etagere,
        String position
) implements DomainEvent {
    public ExemplaireAjouteEvent(String isbnOuvrage, UUID exemplaireId, String salle, String etagere, String position) {
        this(UUID.randomUUID(), Instant.now(), isbnOuvrage, exemplaireId, salle, etagere, position);
    }
}
