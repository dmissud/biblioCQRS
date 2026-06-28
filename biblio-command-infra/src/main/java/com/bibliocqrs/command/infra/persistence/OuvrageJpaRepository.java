package com.bibliocqrs.command.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OuvrageJpaRepository extends JpaRepository<OuvrageEntity, String> {
}
