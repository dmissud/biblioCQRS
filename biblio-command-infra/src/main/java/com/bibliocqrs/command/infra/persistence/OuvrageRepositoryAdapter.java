package com.bibliocqrs.command.infra.persistence;

import com.bibliocqrs.command.domain.Ouvrage;
import com.bibliocqrs.command.domain.OuvrageRepository;
import com.bibliocqrs.command.domain.Exemplaire;
import com.bibliocqrs.command.domain.LieuStockage;
import com.bibliocqrs.command.infra.messaging.KafkaEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OuvrageRepositoryAdapter implements OuvrageRepository {

    private final OuvrageJpaRepository jpaRepository;
    private final KafkaEventPublisher eventPublisher;

    public OuvrageRepositoryAdapter(OuvrageJpaRepository jpaRepository, KafkaEventPublisher eventPublisher) {
        this.jpaRepository = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void save(Ouvrage ouvrage) {
        OuvrageEntity entity = jpaRepository.findById(ouvrage.getIsbn())
                .orElseGet(() -> new OuvrageEntity(ouvrage.getIsbn(), ouvrage.getTitre(), ouvrage.getAuteur()));
        
        for (Exemplaire ex : ouvrage.getExemplaires()) {
            boolean exists = entity.getExemplaires().stream()
                    .anyMatch(e -> e.getId().equals(ex.getId()));
            if (!exists) {
                entity.getExemplaires().add(new ExemplaireEntity(
                        ex.getId(),
                        ex.getCodeBarre(),
                        ex.getLieuStockage().salle(),
                        ex.getLieuStockage().etagere(),
                        ex.getLieuStockage().position()
                ));
            }
        }

        jpaRepository.save(entity);

        // Publication des événements
        ouvrage.pullDomainEvents().forEach(eventPublisher::publish);
    }

    @Override
    public Optional<Ouvrage> findByIsbn(String isbn) {
        return jpaRepository.findById(isbn).map(entity -> {
            Ouvrage ouvrage = Ouvrage.reconstituer(entity.getIsbn(), entity.getTitre(), entity.getAuteur());
            ouvrage.pullDomainEvents(); // Clear creation event
            
            for (ExemplaireEntity ex : entity.getExemplaires()) {
                LieuStockage lieu = new LieuStockage(ex.getSalle(), ex.getEtagere(), ex.getPosition());
                Exemplaire exemplaire = Exemplaire.reconstituer(ex.getId(), ex.getCodeBarre(), lieu);
                ouvrage.reconstituerExemplaire(exemplaire);
            }
            ouvrage.pullDomainEvents(); // Clear adding events
            
            return ouvrage;
        });
    }
}
