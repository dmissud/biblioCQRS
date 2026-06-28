package com.bibliocqrs.core.events;

public interface EventPublisher {
    void publish(DomainEvent event);
}
