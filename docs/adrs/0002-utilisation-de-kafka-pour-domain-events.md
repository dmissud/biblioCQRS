# ADR 0002 : Utilisation d'Apache Kafka pour les Domain Events

**Date:** 2026-06-29
**Statut:** Accepté

## Contexte

Dans une architecture CQRS, la partie Command (écriture) doit informer la partie Query (lecture) des changements d'état
sans se coupler de manière synchrone.
Nos agrégats émettent des "Domain Events" (ex: `OuvrageReferenceEvent`, `ExemplaireAjouteEvent`). Ces événements doivent
être propagés de manière fiable et pouvoir être rejoués en cas d'erreur ou pour la construction de nouveaux modèles de
lecture.

## Décision

Nous avons décidé d'utiliser **Apache Kafka** comme bus d'événements asynchrone entre `biblio-command-*` et
`biblio-query-*`.
L'interface `KafkaEventPublisher` (dans l'adaptateur de messagerie infra) intercepte les événements tirés de l'agrégat
au moment de la sauvegarde (`pullDomainEvents`) et les publie sur des Topics Kafka dédiés.

## Conséquences

### Positives

* **Découplage temporel** : Le composant de lecture peut être indisponible sans impacter la disponibilité de la
  commande.
* **Scalabilité** : Kafka permet le partitionnement et garantit l'ordre des événements par clé (ex: l'ISBN de l'
  Ouvrage).
* **Rejouabilité (Event Sourcing léger)** : Les événements sont persistés dans Kafka avec une rétention configurable,
  permettant de reconstruire les bases de données Query depuis zéro si nécessaire.

### Négatives

* **Complexité opérationnelle** : Nécessite de maintenir un cluster Kafka (ou un service managé).
* **Pas de transaction distribuée native** : Si l'enregistrement en base (PostgreSQL) réussit mais que la publication
  Kafka échoue, l'état devient inconsistant. Une implémentation du pattern *Outbox* sera nécessaire si ce risque se
  matérialise en production.
