package com.bibliocqrs.query.infra.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "catalogue_view")
public class CatalogueViewEntity {

    @Id
    private String isbn;
    private String titre;
    private String auteur;
    private int nombreExemplaires;

    protected CatalogueViewEntity() {}

    public CatalogueViewEntity(String isbn, String titre, String auteur) {
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        this.nombreExemplaires = 0;
    }

    public String getIsbn() { return isbn; }
    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public int getNombreExemplaires() { return nombreExemplaires; }

    public void incrementerExemplaires() {
        this.nombreExemplaires++;
    }
}
