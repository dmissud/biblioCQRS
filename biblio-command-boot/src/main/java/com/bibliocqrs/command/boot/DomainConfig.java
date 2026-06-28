package com.bibliocqrs.command.boot;

import com.bibliocqrs.command.domain.OuvrageCommandHandler;
import com.bibliocqrs.command.domain.OuvrageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public OuvrageCommandHandler ouvrageCommandHandler(OuvrageRepository repository) {
        return new OuvrageCommandHandler(repository);
    }
}
