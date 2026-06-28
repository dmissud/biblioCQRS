package com.bibliocqrs.query.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.bibliocqrs.query"})
@EntityScan(basePackages = {"com.bibliocqrs.query.infra.persistence"})
@EnableJpaRepositories(basePackages = {"com.bibliocqrs.query.infra.persistence"})
public class QueryApplication {
    public static void main(String[] args) {
        SpringApplication.run(QueryApplication.class, args);
    }
}
