package com.bibliocqrs.command.infra;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.bibliocqrs.command.infra.persistence")
@EntityScan(basePackages = "com.bibliocqrs.command.infra.persistence")
public class DummyInfraApplication {
}
