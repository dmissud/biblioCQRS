# Guide du Développeur : biblioCQRS

Bienvenue sur le projet biblioCQRS ! Ce projet respecte des standards de qualité stricts.

## 1. Méthodologie : Double Loop TDD (BDD -> TDD)

Tout nouveau développement fonctionnel doit suivre la méthodologie "Double Loop TDD" encadrée par le TechLead.

### Boucle Externe : BDD (Comportement)

1. **Rédaction du Scénario (Gherkin)** : Écrivez un scénario dans `src/test/resources/features/` décrivant l'usage
   fonctionnel.
2. **Implémentation des Steps** : Implémentez les Step Definitions (`*Steps.java`). À ce stade, le test échoue (Rouge)
   car le code de production n'existe pas.
3. *Note : Ne testez que le cœur du domaine avec Cucumber (pas de Spring Boot, pas de base de données réelle).*

### Boucle Interne : TDD (Design & Code)

Une fois le test BDD rouge, on bascule en TDD classique (TDD strict) pour réaliser le code manquant :

1. **Red** : Écrire un test unitaire (JUnit) pour le comportement d'une classe spécifique (ex: l'Agrégat).
2. **Green** : Écrire le code de production minimum pour faire passer ce test.
3. **Refactor** : Nettoyer le code (Design Patterns, Clean Code, DRY).

On répète la boucle interne jusqu'à ce que le scénario BDD passe au Vert.

## 2. Architecture Hexagonale & Principes

* **Clean Architecture** : Le package `domain` ne doit **jamais** importer de dépendances de `infra` ou de frameworks
  techniques (pas de Spring, pas de JPA).
* **TDA (Tell, Don't Ask)** : Ne demandez pas l'état d'un objet pour prendre une décision. Demandez à l'objet de faire
  l'action lui-même.
* **YAGNI (You Aren't Gonna Need It)** : Ne codez que ce qui est requis par le scénario courant. Pas de conception
  anticipée inutile.

## 3. Tests d'Infrastructure

La couche infrastructure (`biblio-command-infra`) doit être couverte par des tests d'intégration avec JUnit et Spring
Boot (`@WebMvcTest`, `@DataJpaTest`).
Ces tests vérifient le bon mappage technique (JSON -> Objet, Objet -> Base de données).

## 4. Documentation Vivante (Living Documentation)

Les spécifications Gherkin (Cucumber) agissent comme la documentation vivante du système. Elles sont la source de vérité
du comportement métier.
