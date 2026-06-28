package com.bibliocqrs.command.infra.messaging;

import com.bibliocqrs.core.events.DomainEvent;
import com.bibliocqrs.core.events.EventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "biblio.catalogue.events";

    public KafkaEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(DomainEvent event) {
        kafkaTemplate.send(TOPIC, event.eventId().toString(), event);
    }
}
