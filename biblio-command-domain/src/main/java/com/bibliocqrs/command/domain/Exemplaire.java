package com.bibliocqrs.command.domain;

import java.util.UUID;

public class Exemplaire {
    private final UUID id;
    private final LieuStockage lieuStockage;

    public Exemplaire(LieuStockage lieuStockage) {
        this.id = UUID.randomUUID();
        this.lieuStockage = lieuStockage;
    }

    public UUID getId() {
        return id;
    }

    public LieuStockage getLieuStockage() {
        return lieuStockage;
    }
}
