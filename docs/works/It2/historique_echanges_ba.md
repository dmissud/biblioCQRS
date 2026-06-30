# Historique des Échanges - Itération 2 (Cadrage BA "Emprunt")

Ce document retrace la conversation et les décisions prises lors de notre atelier de cadrage pour la fonctionnalité
d'emprunt, mené selon une approche d'ingénierie des exigences (IREB).

## Étape 1 : Demande initiale

* **Product Owner :** "Je souhaite cadrer la feature d'emprunt."
* **Business Analyst (BA) :** Mise en place du rôle de BA. Proposition d'un cadre d'élicitation séparant l'espace du
  problème (métier) de l'espace de la solution (système).

## Étape 2 : Élicitation des Règles Métier

* **Product Owner :** Fournit le scénario nominal. L'usager a un livre, le documentaliste enregistre l'emprunt (ID
  exemplaire, ID adhérent).
    * *Règle 1 :* Durée de l'emprunt de 3 semaines (gérée par le système).
    * *Règle 2 :* Quota max de 5 emprunts. Au-delà, l'emprunt est bloqué.
    * *Règle 3 :* Tolérance de retard. Si l'usager a 1 livre en retard, l'emprunt est autorisé. S'il a strictement plus
      d'un retard (2 ou plus), l'emprunt est bloqué.
    * *Décision système :* Le système gère lui-même les adhérents (nom, prénom) et calcule automatiquement la date
      limite sans modification manuelle.

## Étape 3 : Formalisation et Découverte d'Incohérences

* **BA :** Traduction des échanges et formalisation dans 3 documents distincts au fil de l'eau :
    1. Un artefact **`implementation_plan.md`** temporaire (Cahier des charges) pour validation.
    2. La **`documentation_fonctionnelle.md`** (mise à jour avec l'Arbre des objectifs, Cas d'utilisation, et
       Scénarios).
    3. Un fichier **`story_mapping.md`** (pour regrouper les Epics et les User Stories).
* **Product Owner :** "Est-ce que ta documentation fait ressortir que actuellement les exemplaires n'ont pas d'ID
  explicite ?"
* **BA :** Reconnaissance de la lacune. Le système actuel ne permettait pas de distinguer quel exemplaire physique
  précis était emprunté.
    * *Action :* Ajout d'un point de vigilance dans le Plan d'Élicitation.
* **Product Owner :** Demande la création d'une User Story (US) spécifique pour combler cette lacune.
    * *Action :* Création de la `US-CAT-03` : Identifier un exemplaire par un ID explicite (code-barre ou RFID) dans le
      `story_mapping.md`.

## Étape 4 : Restructuration Documentaire

* **Product Owner :** Demande de décomposer la documentation, jugée trop monolithique, en plusieurs fichiers, et de
  présenter le backlog sous forme de tableau.
* **BA :** Suppression des anciens fichiers globaux (`documentation_fonctionnelle.md` et `story_mapping.md`) au profit
  d'une approche modulaire. Découpage du savoir dans un nouveau dossier `docs/requirements/` comprenant 5 fichiers
  spécialisés :
    1. `01_vision_contexte.md` (Périmètre et acteurs)
    2. `02_glossaire.md` (Langage omniprésent / Ubiquitous Language)
    3. `03_scenarios_regles.md` (Règles métier et Use Cases)
    4. `04_plan_elicitation.md` (Incohérences et ateliers à venir)
    5. `05_backlog.md` (Tableau des US priorisées).

## Étape 5 : Affinage de la Vision et du Contexte

* **Product Owner :** Remonte trois points d'amélioration cruciaux concernant le domaine du problème :
    1. La carte des parties prenantes manque de relations (hiérarchie, satisfaction).
    2. Il manque les attentes, les risques et le ROI de chaque partie prenante.
    3. Le diagramme de contexte confond la Bibliothèque (système réel) avec le Système Informatique BiblioCQRS étudié.
* **BA :** Prise en compte immédiate. Mise à jour complète du document `01_vision_contexte.md` :
    * Remplacement du mindmap par un diagramme relationnel des PP (Direction -> Bibliothécaire -> Usager).
    * Création d'une matrice croisant Attentes, Risques et ROI.
    * Révision du diagramme de contexte pour délimiter strictement le *Système Métier* (la réalité physique) du
      *Périmètre de la Solution* (le logiciel BiblioCQRS).

## Étape 6 : Traçabilité Arbre des Objectifs <-> Backlog

* **Product Owner :** Fait remarquer une rupture de traçabilité : l'arbre des objectifs n'intégrait pas la notion d'Epic
  qui était pourtant présente dans le backlog. Recommande la bonne pratique liant Objectif Entreprise -> Epic -> Use
  Case.
* **BA :** Prise en compte et mise à jour de `03_scenarios_regles.md`. Les branches principales du mindmap ont été
  renommées pour correspondre exactement aux Epics (Gestion du Catalogue, Gestion des Adhérents, Gestion des Prêts),
  offrant ainsi une parfaite continuité visuelle jusqu'au niveau "Cas d'Utilisation".

## Étape 7 : Règle d'écriture des User Stories (Personas)

* **Product Owner :** Signale une erreur méthodologique dans le backlog : le "Système" ne peut pas être le persona (
  acteur) d'une User Story, car il ne retire aucune valeur métier. L'US doit se placer du point de vue de l'acteur
  opérationnel.
* **BA :** Correction immédiate de `05_backlog.md`. Les US de blocage (Quota et Retards) ont été réécrites sous l'angle
  du **Documentaliste** qui *veut être empêché* de faire l'action, *afin de* garantir l'équité d'accès aux ressources (
  pour le quota) et la bonne circulation (pour les retards).

## Étape 8 : Industrialisation via la création d'un Skill

* **Product Owner :** Demande comment pérenniser toutes ces règles (pas de persona "Système", traçabilité IREB,
  historique automatique) pour ne plus avoir à les répéter à l'avenir.
* **BA :** Proposition et exécution du processus de création de "Skill". Génération du fichier `SKILL.md` (nommé
  `business-analyst-ireb`) installé globalement sur la machine. Le comportement et les règles d'or de notre cadrage sont
  désormais un standard encapsulé et réutilisable à la demande.

## Étape 9 : Transition vers la Réalisation (TechLead & TDD Double Loop)

* **Product Owner :** Déclare la fin de la phase BA. Demande d'endosser le rôle de TechLead Senior (Architecture
  CQRS/Hexagonale, DDD, Clean Code, DRY, TDA). Impose la méthodologie stricte "BDD -> TDD par scénario (YAGNI)".
* **BA / TechLead :** Création du skill global `techlead-bdd-tdd`. Constat que la couche Domaine de US-CAT-01 et
  US-CAT-02 est déjà couverte par le BDD (`reference_ouvrage.feature`) et que les tests passent. La décision est prise
  d'appliquer le cycle TDD strict (test Junit en premier) sur la couche Infrastructure (Adaptateurs) pour boucler la
  tranche verticale.

## Étape 10 : Précision Sémantique et Granularité (Business Case vs Use Case)

* **Product Owner :** Rejette l'utilisation du verbe "Gérer" (jugé trop "fourre-tout") dans la documentation des Use
  Cases. Introduit également la nuance fondamentale entre le processus global d'un bout à l'autre (*Business Case*,
  ex: "Opérer un prêt") et l'interaction unitaire d'un persona avec le système à un instant T (*Use Case*, ex: "Prendre
  en compte un emprunt"). Précise enfin qu'une User Story (US) est éphémère (pour planifier), tandis que le Use Case (
  UC) est la documentation pérenne du résultat.
* **BA :** Application immédiate sur la matrice CRUD (`01_use_cases_crud.md`). Remplacement des verbes flous par des
  verbes d'action métier précis. Décomposition du domaine des prêts en deux Use Cases temporels distincts (Prendre en
  compte un emprunt / Effectuer une restitution). Enfin, mise à jour du skill `business-analyst-ireb` pour graver
  définitivement cette rigueur sémantique et de modélisation dans le marbre.

## Conclusion de l'atelier

L'itération a permis de passer d'une idée métier à un ensemble documentaire professionnel et structuré. La distinction
rigoureuse entre l'espace du problème (la bibliothèque réelle, ses acteurs) et l'espace de la solution (le logiciel) est
désormais parfaitement modélisée, sécurisant ainsi l'implémentation logicielle à venir.
