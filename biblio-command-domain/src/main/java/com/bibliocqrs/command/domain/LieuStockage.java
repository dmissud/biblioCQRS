package com.bibliocqrs.command.domain;

public record LieuStockage(String salle, String etagere, String position) {
    public LieuStockage {
        if (salle == null || salle.isBlank()) throw new IllegalArgumentException("La salle est obligatoire");
        if (etagere == null || etagere.isBlank()) throw new IllegalArgumentException("L'étagère est obligatoire");
        if (position == null || position.isBlank()) throw new IllegalArgumentException("La position est obligatoire");
    }
}
