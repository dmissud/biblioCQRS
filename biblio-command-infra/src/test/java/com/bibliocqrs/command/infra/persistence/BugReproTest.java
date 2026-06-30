package com.bibliocqrs.command.infra.persistence;

import com.bibliocqrs.command.domain.LieuStockage;
import com.bibliocqrs.command.domain.Ouvrage;
import com.bibliocqrs.command.infra.messaging.KafkaEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@DataJpaTest
public class BugReproTest {

    @Autowired
    private OuvrageJpaRepository jpaRepository;

    @Test
    public void testAddTwoExemplaires() {
        KafkaEventPublisher mockEventPublisher = mock(KafkaEventPublisher.class);
        OuvrageRepositoryAdapter adapter = new OuvrageRepositoryAdapter(jpaRepository, mockEventPublisher);

        // 1st add
        Ouvrage ouvrage = new Ouvrage("978-X", "Titre", "Auteur");
        ouvrage.ajouterExemplaire("CB-1", new LieuStockage("S", "E", "P"));
        adapter.save(ouvrage);

        // 2nd add
        Ouvrage found = adapter.findByIsbn("978-X").orElseThrow();
        found.ajouterExemplaire("CB-2", new LieuStockage("S", "E", "P2"));
        adapter.save(found);

        jpaRepository.flush(); // To trigger db constraints
    }
}
