# Règle d'Orchestration Globale : Pipeline IREB, DDD, BA & Atelier Itératif

Ce fichier définit le comportement obligatoire de l'assistant lors de toute demande de création, d'évolution ou
d'analyse fonctionnelle d'un projet logiciel.

## Objectif du Pipeline

Garantir une traçabilité rigoureuse et sans rupture, depuis l'objectif métier initial jusqu'au code source, en
s'appuyant sur la base documentaire (`docs/knowledge-base/`), des ateliers itératifs (*Workshop Mode*), une modélisation
orientée Domain-Driven Design (DDD), et une séparation stricte entre le **Problem Space** et le **Solution Space**.

---

## Séquencement Obligatoire (Workflow en 5 Phases)

L'assistant doit impérativement respecter l'ordre séquentiel des phases ci-dessous. Il est **strictement interdit** de
sauter une étape ou d'aborder la phase d'implémentation technique avant la validation formelle de l'étape précédente.

### Phase 0 : Intrants & Ateliers (Knowledge Base)

- **Déclencheur** : Dépôt de documents sources, notes ou comptes-rendus dans `docs/knowledge-base/`.
- **Action de l'agent** : Analyser les inputs documentaires. Si des zones d'ombre subsistent, l'agent déclenche un
  **Atelier Itératif (Workshop Mode)** en proposant des questions structurées (5 Pourquoi, 5 W) pour co-construire le
  besoin avec l'utilisateur.

### Phase 1 : Cadrage du Problème (`ireb-problem-analysis`)

- **Déclencheur** : Expression d'un besoin brut ou validation des ateliers de la Phase 0.
- **Action de l'agent** : Invoquer le skill `ireb-problem-analysis`.
- **Livrable attendu** : `docs/requirements/01-problem-statement.md` (Lean Canvas, Arbre des buts, Grille de maturité).
- **Quality Gate 1** : L'agent présente la synthèse et demande : *"Le Problem Statement et les objectifs (Goals)
  sont-ils validés pour passer à la modélisation DDD ?"* -> **Attendre la validation humaine.**

### Phase 1.5 : Modélisation du Domaine & DDD (`ireb-domain-modeling`)

- **Déclencheur** : Validation de la Phase 1.
- **Action de l'agent** : Invoquer le skill `ireb-domain-modeling`.
- **Livrable attendu** : `docs/requirements/01.5-domain-model.md` (Diagramme de contexte, Bounded Contexts, Exigences
  Structurelles).
- **Quality Gate 1.5** : L'agent présente le modèle de domaine et demande : *"Les Bounded Contexts et les exigences
  structurelles sont-ils validés pour construire le backlog agile ?"* -> **Attendre la validation humaine.**

### Phase 2 : Construction du Backlog (`ireb-backlog-builder`)

- **Déclencheur** : Validation de la Phase 1.5.
- **Action de l'agent** : Invoquer le skill `ireb-backlog-builder`.
- **Livrable attendu** : `docs/requirements/02-backlog.md` (Epics, User Stories INVEST ancrées dans les Bounded
  Contexts, priorisation MoSCoW, Matrice de traçabilité).
- **Quality Gate 2** : L'agent présente la liste des US priorisées et demande : *"Quelle User Story souhaitez-vous
  raffiner en premier pour l'implémentation ?"* -> **Attendre le choix de l'utilisateur.**

### Phase 3 : Raffinement de la Story (`ireb-story-refinement`)

- **Déclencheur** : Sélection d'une User Story par l'utilisateur.
- **Action de l'agent** : Invoquer le skill `ireb-story-refinement`.
- **Livrable attendu** : `docs/requirements/stories/<US-ID>.md` (Syntaxe normalisée Exigence Utilisateur/Système, Règles
  SOPHIST, Critères d'acceptation Gherkin BDD, NFR ISO 25010, DoR).
- **Quality Gate 3 (Definition of Ready - DoR)** : L'agent vérifie que la Story est prête et demande : *"La
  spécification détaillée de cette User Story est-elle validée pour lancer le développement ?"* -> **Attendre le feu
  vert.**

### Phase 4 : Implémentation & Code

- **Déclencheur** : Validation de la DoR (Phase 3).
- **Action de l'agent** : Passage en mode écriture de code / TDD en s'appuyant strictement sur les scénarios Gherkin et
  les exigences structurelles/comportementales/fonctionnelles.