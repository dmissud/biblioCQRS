# Historique du Projet BiblioCQRS (Prototype 1)

Ce document retrace l'historique de nos échanges et les étapes de réalisation du premier prototype fonctionnel de **BiblioCQRS**.

## 1. Initialisation et Choix d'Architecture
Nous avons commencé par définir les fondations techniques et architecturales du projet. Les choix se sont portés sur :
*   **Architecture Hexagonale (Ports et Adaptateurs)** : Pour isoler strictement la logique métier (Domaine) des détails techniques (Infrastructure).
*   **CQRS (Command Query Responsibility Segregation)** : Pour séparer les opérations d'écriture (Commandes) des opérations de lecture (Requêtes).
*   **Event-Driven (Pilotage par Événements)** : Utilisation d'**Apache Kafka** pour synchroniser le modèle de lecture à partir des événements produits par le modèle de commande.
*   **TDD/BDD** : Une approche pilotée par les tests et le comportement, utilisant **Cucumber**.

## 2. Développement du Modèle de Commande (Command Side)
*   **Conception Métier (BDD)** : Nous avons écrit nos premiers scénarios fonctionnels avec Cucumber : 
    *   Référencer un nouvel ouvrage.
    *   Refuser un ouvrage déjà existant (protection contre les doublons).
    *   Ajouter un exemplaire physique (avec salle, étagère, position) à un ouvrage existant.
*   **Implémentation du Domaine** : Création de l'agrégat `Ouvrage` qui expose le comportement métier et accumule les "Domain Events" (`OuvrageReferenceEvent`, `ExemplaireAjouteEvent`).
*   **Adaptateurs et Infrastructure** : 
    *   Mise en place de **PostgreSQL** via JPA/Hibernate pour sauvegarder l'état.
    *   Développement d'un point d'entrée REST pour recevoir les commandes.
*   **Résolution d'anomalies** : Nous avons résolu un problème où le chargement d'un ouvrage depuis la base de données déclenchait à nouveau des événements de création. La solution a été d'introduire une méthode de fabrique statique `reconstituer()` dans le domaine.

## 3. Développement du Modèle de Lecture (Query Side)
*   **Core Events** : Extraction des événements dans un module partagé `biblio-core-events` pour un couplage lâche.
*   **Infrastructure de Projection** : Mise en place d'un service d'écoute Kafka (`CatalogueEventProjector`) chargé de consommer les événements et de mettre à jour une table `catalogue_view`.
*   **Résolution d'anomalies (Kafka)** : Nous avons corrigé des erreurs de désérialisation polymorphique (Spring Kafka) en passant d'une méthode de listener multiple à un listener au niveau de la classe avec des `@KafkaHandler`, ainsi que la gestion de l'ID de groupe dynamique (`group-id`).

## 4. Tests d'Intégration et Validation de Bout en Bout
*   Nous avons démarré l'infrastructure avec Docker (Postgres, Zookeeper, Kafka).
*   À l'aide de requêtes `curl`, nous avons validé le flux complet :
    1.  Envoi d'une commande (API sur le port `8082`).
    2.  Sauvegarde en base de données de la Commande.
    3.  Publication d'un événement dans Kafka.
    4.  Consommation de l'événement par le service Query (port `8083`).
    5.  Mise à jour de la base de données de Vue.
    6.  Vérification de la disponibilité des données formatées via l'API Query.

## 5. Documentation
*   Tout au long du projet, une règle d'or a été définie : **Mettre à jour la documentation en continu**.
*   Création et maintien du fichier `babok_documentation.md` pour l'analyse fonctionnelle (BABOK).
*   Création du fichier `architecture_documentation.md` pour acter les choix techniques et l'architecture logicielle.

## 6. Préparation du Front-End (Angular)
*   Initialisation d'un projet **Angular 17+** (Standalone).
*   Configuration d'un proxy (`proxy.conf.json`) pour dialoguer fluidement avec les API Command (`8082`) et Query (`8083`) sans problèmes de CORS.
*   Structuration du routage et des composants autour de trois "facettes" identifiées dans l'architecture :
    1.  **Catalogue (`/catalogue`)** : Consultation en direct.
    2.  **Référencement (`/referencer`)** : Interface fonctionnelle.
    3.  **Test Scénario (`/test`)** : Interface technique pour l'injection automatisée.

## Prochaine Étape
Implémentation visuelle et logique des composants Front-End (Angular + Angular Material) pour finaliser le premier prototype de bout en bout de **BiblioCQRS**.
