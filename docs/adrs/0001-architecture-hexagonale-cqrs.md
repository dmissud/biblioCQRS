# ADR 0001 : Adoption de l'Architecture Hexagonale et du pattern CQRS

**Date:** 2026-06-29
**Statut:** Accepté

## Contexte

Le système de gestion de bibliothèque (biblioCQRS) doit gérer des règles métier complexes (emprunts, pénalités, gestion
fine des stocks et du catalogue) avec plusieurs types d'acteurs (Documentalistes, Bibliothécaires, Adhérents).
Les modèles de données utilisés pour la mise à jour (Command) sont très différents de ceux utilisés pour la
consultation (Query), notamment pour la recherche temps réel dans le catalogue.
De plus, nous souhaitons que le cœur métier soit testable de façon isolée via BDD (Cucumber) sans dépendre de la base de
données ou des API web.

## Décision

Nous avons décidé d'adopter :

1. **L'Architecture Hexagonale (Ports and Adapters)** : Le domaine (le cœur métier) n'a aucune dépendance technique. Les
   adaptateurs (Web, Persistance, Messaging) dépendent du domaine.
2. **Le pattern CQRS (Command Query Responsibility Segregation)** :
    - Séparation stricte des modules d'écriture (`biblio-command-*`) et de lecture (`biblio-query-*`).
    - Le modèle d'écriture est modélisé sous forme d'**Agrégats** stricts (au sens DDD).
    - Le modèle de lecture utilise des projections dénormalisées taillées pour les vues utilisateurs.

## Conséquences

### Positives

* **Testabilité maximale** : Les scénarios BDD valident le domaine de commande instantanément sans monter le contexte
  Spring.
* **Indépendance technique** : Possibilité de changer PostgreSQL pour une autre base sans impacter le domaine.
* **Évolutivité** : Les lectures et les écritures peuvent scaler indépendamment.

### Négatives

* **Complexité initiale** : Nombre de classes plus important (Command, CommandHandler, Domain Event, Adapters).
* **Consistance éventuelle (Eventual Consistency)** : Le délai de synchronisation entre la base d'écriture et la base de
  lecture requiert d'adapter l'UX (ex: utilisation du code HTTP 202 Accepted).
