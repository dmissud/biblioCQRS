package com.bibliocqrs.query.infra.messaging;

import com.bibliocqrs.core.events.ExemplaireAjouteEvent;
import com.bibliocqrs.core.events.OuvrageReferenceEvent;
import com.bibliocqrs.query.infra.persistence.CatalogueViewEntity;
import com.bibliocqrs.query.infra.persistence.CatalogueViewRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

import org.springframework.kafka.annotation.KafkaHandler;

@Component
@KafkaListener(topics = "biblio.catalogue.events")
public class CatalogueEventProjector {

    private final CatalogueViewRepository repository;

    public CatalogueEventProjector(CatalogueViewRepository repository) {
        this.repository = repository;
    }

    @KafkaHandler
    public void onOuvrageReference(OuvrageReferenceEvent event) {
        CatalogueViewEntity view = new CatalogueViewEntity(event.isbn(), event.titre(), event.auteur());
        repository.save(view);
    }

    @KafkaHandler
    public void onExemplaireAjoute(ExemplaireAjouteEvent event) {
        Optional<CatalogueViewEntity> viewOpt = repository.findById(event.isbnOuvrage());
        if (viewOpt.isPresent()) {
            CatalogueViewEntity view = viewOpt.get();
            view.ajouterExemplaire(event.codeBarre());
            repository.save(view);
        }
    }
    
    @KafkaHandler(isDefault = true)
    public void ignore(Object object) {
        System.out.println("UNKNOWN EVENT RECEIVED: " + object.getClass() + " : " + object.toString());
    }
}
