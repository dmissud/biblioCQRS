package com.bibliocqrs.command.infra.persistence;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ouvrages")
public class OuvrageEntity {

    @Id
    private String isbn;
    private String titre;
    private String auteur;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "ouvrage_isbn")
    private List<ExemplaireEntity> exemplaires = new ArrayList<>();

    protected OuvrageEntity() {}

    public OuvrageEntity(String isbn, String titre, String auteur) {
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
    }

    public String getIsbn() { return isbn; }
    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public List<ExemplaireEntity> getExemplaires() { return exemplaires; }
}
