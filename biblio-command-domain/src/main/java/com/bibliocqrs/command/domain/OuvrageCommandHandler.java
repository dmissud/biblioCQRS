package com.bibliocqrs.command.domain;

public class OuvrageCommandHandler {
    private final OuvrageRepository repository;

    public OuvrageCommandHandler(OuvrageRepository repository) {
        this.repository = repository;
    }

    public void handle(ReferencerOuvrageCommand command) {
        if (repository.findByIsbn(command.isbn()).isPresent()) {
            throw new IllegalArgumentException("Un ouvrage avec cet ISBN existe déjà.");
        }
        Ouvrage ouvrage = new Ouvrage(command.isbn(), command.titre(), command.auteur());
        repository.save(ouvrage);
    }

    public void handle(AjouterExemplaireCommand command) {
        Ouvrage ouvrage = repository.findByIsbn(command.isbnOuvrage())
            .orElseThrow(() -> new IllegalArgumentException("Ouvrage non trouvé."));
        
        LieuStockage lieu = new LieuStockage(command.salle(), command.etagere(), command.position());
        ouvrage.ajouterExemplaire(lieu);
        
        repository.save(ouvrage);
    }
}
