# C4 Model : biblioCQRS

Ce document utilise la notation C4 pour décrire l'architecture logicielle du système, rendue avec Mermaid.

## Niveau 1 : System Context

```mermaid
C4Context
    title System Context diagram for biblioCQRS

    Person(documentaliste, "Documentaliste", "Gère les emprunts et accueille le public.")
    Person(bibliothecaire, "Bibliothécaire", "Gère le catalogue et les stocks physiques.")
    Person(adherent, "Adhérent", "Emprunte des livres et consulte le catalogue.")

    System(biblio, "biblioCQRS", "Système central de gestion de la bibliothèque.")

    Rel(bibliothecaire, biblio, "Référence des ouvrages, ajoute des exemplaires", "HTTPS/REST")
    Rel(documentaliste, biblio, "Enregistre/Retourne des emprunts", "HTTPS/REST")
    Rel(adherent, biblio, "Recherche dans le catalogue", "HTTPS/REST")
```

## Niveau 2 : Container

```mermaid
C4Container
    title Container diagram for biblioCQRS

    Person(bibliothecaire, "Bibliothécaire")
    Person(documentaliste, "Documentaliste")

    System_Boundary(biblio, "biblioCQRS") {
        Container(api_command, "API Command (biblio-command-boot)", "Spring Boot, Java 21", "Traite les intentions d'écriture, valide la logique métier (Agrégats).")
        Container(api_query, "API Query (biblio-query-boot)", "Spring Boot, Java 21", "Fournit des modèles de lecture optimisés pour l'affichage.")
        
        ContainerDb(db_command, "PostgreSQL Command", "PostgreSQL", "Stocke l'état courant des agrégats.")
        ContainerDb(db_query, "PostgreSQL Query", "PostgreSQL", "Stocke les projections dénormalisées des vues.")
        
        ContainerQueue(kafka, "Apache Kafka", "Kafka", "Bus de messages (Domain Events).")
    }

    Rel(bibliothecaire, api_command, "Commandes", "JSON/HTTPS")
    Rel(documentaliste, api_command, "Commandes", "JSON/HTTPS")
    Rel(documentaliste, api_query, "Consultations", "JSON/HTTPS")
    
    Rel(api_command, db_command, "Lit/Ecrit les agrégats", "JDBC/JPA")
    Rel(api_command, kafka, "Publie Domain Events", "Kafka Producer")
    
    Rel(api_query, kafka, "Consomme Domain Events", "Kafka Consumer")
    Rel(api_query, db_query, "Met à jour les projections", "JDBC/JPA")
    Rel(api_query, db_query, "Lit les vues", "JDBC/JPA")
```

## Niveau 3 : Component (API Command - Zoom sur le Domaine Ouvrage)

```mermaid
C4Component
    title Component diagram for API Command (biblio-command-*)

    Container_Boundary(api_command, "API Command") {
        Component(controller, "OuvrageCommandController", "Spring REST Controller", "Accepte les requêtes (202 Accepted) et instancie les Commands.")
        Component(handler, "OuvrageCommandHandler", "Domain Service", "Reconstitue l'agrégat, invoque la logique métier et persiste.")
        Component(aggregate, "Agrégat Ouvrage", "Domain Entity", "Encapsule les invariants métier et génère les Domain Events.")
        
        Component(repository, "OuvrageRepositoryAdapter", "Infrastructure Adapter", "Implémente l'interface domaine pour sauvegarder via JPA.")
        Component(publisher, "KafkaEventPublisher", "Infrastructure Adapter", "Publie les événements récupérés de l'agrégat.")
    }

    Rel(controller, handler, "Envoie Command")
    Rel(handler, repository, "findByIsbn()")
    Rel(handler, aggregate, "Invoque méthodes métier")
    Rel(handler, repository, "save(Ouvrage)")
    Rel(repository, publisher, "publish(DomainEvent)")
```
