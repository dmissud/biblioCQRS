package com.bibliocqrs.command.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.bibliocqrs.command"})
@EntityScan(basePackages = {"com.bibliocqrs.command.infra.persistence"})
@EnableJpaRepositories(basePackages = {"com.bibliocqrs.command.infra.persistence"})
public class CommandApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommandApplication.class, args);
    }
}
