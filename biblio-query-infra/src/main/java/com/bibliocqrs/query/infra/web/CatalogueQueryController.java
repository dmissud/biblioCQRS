package com.bibliocqrs.query.infra.web;

import com.bibliocqrs.query.infra.persistence.CatalogueViewEntity;
import com.bibliocqrs.query.infra.persistence.CatalogueViewRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalogue")
public class CatalogueQueryController {

    private final CatalogueViewRepository repository;

    public CatalogueQueryController(CatalogueViewRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CatalogueViewEntity> getCatalogue() {
        return repository.findAll();
    }
}
