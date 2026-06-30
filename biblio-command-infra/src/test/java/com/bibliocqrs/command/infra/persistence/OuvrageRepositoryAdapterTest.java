package com.bibliocqrs.command.infra.persistence;

import com.bibliocqrs.command.domain.LieuStockage;
import com.bibliocqrs.command.domain.Ouvrage;
import com.bibliocqrs.command.infra.messaging.KafkaEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@DataJpaTest
public class OuvrageRepositoryAdapterTest {

    @Autowired
    private OuvrageJpaRepository jpaRepository;

    private KafkaEventPublisher mockEventPublisher;
    private OuvrageRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        mockEventPublisher = mock(KafkaEventPublisher.class);
        adapter = new OuvrageRepositoryAdapter(jpaRepository, mockEventPublisher);
    }

    @Test
    void save_shouldPersistOuvrageAndPublishEvents() {
        // Arrange
        Ouvrage ouvrage = new Ouvrage("978-0134685991", "Effective Java", "Joshua Bloch");
        ouvrage.ajouterExemplaire("CB-001", new LieuStockage("A1", "E1", "P1"));

        // Act
        adapter.save(ouvrage);

        // Assert
        Optional<OuvrageEntity> savedEntity = jpaRepository.findById("978-0134685991");
        assertThat(savedEntity).isPresent();
        assertThat(savedEntity.get().getTitre()).isEqualTo("Effective Java");
        assertThat(savedEntity.get().getExemplaires()).hasSize(1);

        // Verify events were published: 1 OuvrageReferenceEvent + 1 ExemplaireAjouteEvent
        verify(mockEventPublisher, times(2)).publish(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void findByIsbn_shouldReconstituteOuvrageWithNoPendingEvents() {
        // Arrange
        OuvrageEntity entity = new OuvrageEntity("978-0134685991", "Effective Java", "Joshua Bloch");
        entity.getExemplaires().add(new ExemplaireEntity(UUID.randomUUID(), "CB-002", "A1", "E1", "P1"));
        jpaRepository.save(entity);

        // Act
        Optional<Ouvrage> found = adapter.findByIsbn("978-0134685991");

        // Assert
        assertThat(found).isPresent();
        Ouvrage ouvrage = found.get();
        assertThat(ouvrage.getIsbn()).isEqualTo("978-0134685991");
        assertThat(ouvrage.getExemplaires()).hasSize(1);

        // Ensure no pending events from the reconstitution process
        assertThat(ouvrage.pullDomainEvents()).isEmpty();
    }
}
