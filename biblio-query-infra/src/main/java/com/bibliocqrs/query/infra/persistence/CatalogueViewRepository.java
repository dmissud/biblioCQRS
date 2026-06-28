package com.bibliocqrs.query.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogueViewRepository extends JpaRepository<CatalogueViewEntity, String> {
}
