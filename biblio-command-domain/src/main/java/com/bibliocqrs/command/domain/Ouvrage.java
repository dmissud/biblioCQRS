package com.bibliocqrs.command.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bibliocqrs.core.events.DomainEvent;
import com.bibliocqrs.core.events.OuvrageReferenceEvent;
import com.bibliocqrs.core.events.ExemplaireAjouteEvent;

public class Ouvrage {
    private final String isbn;
    private final String titre;
    private final String auteur;
    private final List<Exemplaire> exemplaires = new ArrayList<>();
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    private Ouvrage(String isbn, String titre, String auteur, boolean isNew) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("L'ISBN est obligatoire");
        }
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        if (isNew) {
            this.domainEvents.add(new OuvrageReferenceEvent(isbn, titre, auteur));
        }
    }

    public Ouvrage(String isbn, String titre, String auteur) {
        this(isbn, titre, auteur, true);
    }

    public static Ouvrage reconstituer(String isbn, String titre, String auteur) {
        return new Ouvrage(isbn, titre, auteur, false);
    }

    public void ajouterExemplaire(LieuStockage lieu) {
        Exemplaire exemplaire = new Exemplaire(lieu);
        this.exemplaires.add(exemplaire);
        this.domainEvents.add(new ExemplaireAjouteEvent(
                this.isbn,
                exemplaire.getId(),
                lieu.salle(),
                lieu.etagere(),
                lieu.position()
        ));
    }

    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return events;
    }

    public String getIsbn() { return isbn; }
    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    
    public List<Exemplaire> getExemplaires() {
        return Collections.unmodifiableList(exemplaires);
    }
}
