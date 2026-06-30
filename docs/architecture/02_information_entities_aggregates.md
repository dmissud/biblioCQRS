# Entités Informationnelles et Agrégats

Ce document présente la modélisation conceptuelle du domaine métier, découpé selon les principes du Domain-Driven
Design (DDD).

## 1. Modèle Conceptuel (Vue d'ensemble)

Les entités informationnelles représentent les concepts clés manipulés par le système métier (indépendamment de la façon
dont ils sont stockés).

* **Ouvrage** : Représente la définition abstraite d'un livre ou d'un document (caractérisé de façon unique par un
  ISBN). C'est l'entité de référence du Catalogue.
* **Exemplaire** : Représente la concrétisation physique d'un Ouvrage, avec une localisation précise dans la
  bibliothèque (Salle, Étagère, Position). Il ne peut exister sans un Ouvrage de référence.
* **Adhérent** *(Domaine Futur)* : Représente un utilisateur autorisé à emprunter des ouvrages, soumis à des quotas et
  des règles de pénalité.
* **Emprunt** *(Domaine Futur)* : Représente l'association temporelle entre un Adhérent et un Exemplaire physique.

## 2. Découpage en Agrégats (DDD)

Pour garantir la cohérence transactionnelle en écriture (Command), le système est découpé en **Agrégats**. Chaque
agrégat possède une entité racine ("Aggregate Root").

### Agrégat : `Ouvrage` (Catalogue)

L'agrégat `Ouvrage` est l'entité racine (Aggregate Root) pour toutes les opérations du catalogue.

* **Frontière de l'agrégat** : Inclut l'entité `Ouvrage` et la liste des objets valeurs (ou entités filles)
  `Exemplaire`.
* **Règles de Cohérence (Invariants)** :
    * Un Ouvrage doit avoir un ISBN unique.
    * On ne peut ajouter un exemplaire physique que si l'Ouvrage abstrait a été préalablement référencé.
* **Comportement BDD Mappé** : Scénarios couverts dans `reference_ouvrage.feature`.

### Agrégat : `Emprunt` (À venir)

*(Cet agrégat sera détaillé lors de l'implémentation de la User Story US-PRT-01).*

Il contiendra probablement la logique métier garantissant les quotas d'emprunt par adhérent et la disponibilité des
exemplaires.
