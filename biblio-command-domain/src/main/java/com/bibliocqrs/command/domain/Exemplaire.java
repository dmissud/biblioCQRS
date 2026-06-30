package com.bibliocqrs.command.domain;

import java.util.UUID;

public class Exemplaire {
    private final UUID id;
    private final String codeBarre;
    private final LieuStockage lieuStockage;

    public Exemplaire(String codeBarre, LieuStockage lieuStockage) {
        this(UUID.randomUUID(), codeBarre, lieuStockage);
    }

    private Exemplaire(UUID id, String codeBarre, LieuStockage lieuStockage) {
        if (codeBarre == null || codeBarre.isBlank()) {
            throw new IllegalArgumentException("Le code-barres est obligatoire");
        }
        this.id = id;
        this.codeBarre = codeBarre;
        this.lieuStockage = lieuStockage;
    }

    public static Exemplaire reconstituer(UUID id, String codeBarre, LieuStockage lieuStockage) {
        return new Exemplaire(id, codeBarre, lieuStockage);
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public UUID getId() {
        return id;
    }

    public LieuStockage getLieuStockage() {
        return lieuStockage;
    }
}
