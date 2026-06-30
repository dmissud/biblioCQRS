# Use Cases et Matrice CRUD

Ce document trace l'impact des cas d'usage (Use Cases) définis lors des ateliers BA (Business Analysis) sur les entités
informationnelles du système. Il permet de s'assurer que notre conception couvre tous les besoins sans orphelins de
données.

## 1. Répertoire des Cas d'Usage (Use Cases)

Les cas d'usage (Use Cases) documentent les fonctionnalités pérennes du système, à ne pas confondre avec les User
Stories (US) qui sont des éléments éphémères de planification. Plusieurs US concourent généralement à la réalisation
d'un Use Case.

### Domaine Catalogue

* **UC-CAT-01 : Référencer un ouvrage** (Acteur : Bibliothécaire)
    * *Ce Use Case regroupe l'implémentation initiale issue de US-CAT-01 (Créer la référence), US-CAT-02 (Ajouter un
      exemplaire physique) et US-CAT-03 (Assigner RFID).*

### Domaine Prêts (Futur)

* *UC-PRT-01 : Prendre en compte un emprunt* (Acteur : Documentaliste)
* *UC-PRT-02 : Effectuer une restitution d'ouvrage* (Acteur : Documentaliste)

## 2. Matrice de Traçabilité CRUD

| Entité Informationnelle | Cas d'Usage (UC)                        |     C (Create)     |     R (Read)      |      U (Update)       | D (Delete) |
|:------------------------|:----------------------------------------|:------------------:|:-----------------:|:---------------------:|:----------:|
| **Ouvrage**             | UC-CAT-01 (Référencer un ouvrage)       |    X (Création)    |  X (Validation)   | X (Ajout exemplaire)  |            |
|                         | *UC-PRT-01 (Prendre en compte emprunt)* |                    |         X         |                       |            |
| **Exemplaire**          | UC-CAT-01 (Référencer un ouvrage)       | X (Ajout physique) |                   |                       |            |
|                         | *UC-PRT-01 / UC-PRT-02*                 |                    |         X         | X (Changement statut) |            |
| **Adhérent**            | *UC-PRT-01 (Prendre en compte emprunt)* |                    | X (Vérif. Quotas) |                       |            |
| **Emprunt**             | *UC-PRT-01 (Prendre en compte emprunt)* |     X (Créer)      |                   |                       |            |
|                         | *UC-PRT-02 (Effectuer restitution)*     |                    |         X         |     X (Clôturer)      |            |

*(Note : Dans une architecture CQRS asynchrone, les opérations de création/modification "C/U/D" sont des Commands,
tandis que les opérations de lecture pure "R" pour les IHM appartiennent au domaine Query).*
