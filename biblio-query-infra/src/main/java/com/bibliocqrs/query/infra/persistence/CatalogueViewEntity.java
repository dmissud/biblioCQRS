package com.bibliocqrs.query.infra.persistence;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "catalogue_view")
public class CatalogueViewEntity {

    @Id
    private String isbn;
    private String titre;
    private String auteur;
    private int nombreExemplaires;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "catalogue_view_codes_barres", joinColumns = @JoinColumn(name = "ouvrage_isbn"))
    @Column(name = "code_barre")
    private List<String> codesBarres = new ArrayList<>();

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

    public List<String> getCodesBarres() {
        return codesBarres;
    }

    public void ajouterExemplaire(String codeBarre) {
        this.nombreExemplaires++;
        if (codeBarre != null && !codeBarre.isBlank()) {
            this.codesBarres.add(codeBarre);
        }
    }
}
