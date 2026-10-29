---
name: ba-entrypoint
description: À utiliser quand le BA formule une demande en langage naturel et qu'il faut choisir le bon skill, par exemple « affinons l'US 12 », « j'ai déposé des documents, travaillons dessus », « affinons le besoin X en US », ou « par où commencer ». Lit l'état du travail et propose le prochain skill avec le motif ; ne produit aucun fichier.
---

# Point d'entrée : choisir le prochain skill

## Conventions

Chemins : la racine de la documentation est `<REQ_DIR>` (défaut `docs/requirements`). Arborescence :
- `<REQ_DIR>/index.md` : index racine (sections `## Domaines`, `## Sources`, `## Travaux en cours`)
- `<REQ_DIR>/inbox/` : documents bruts déposés, à exploiter
- `<REQ_DIR>/sources/` : documents exploités, jamais supprimés, indexés par `SRC-nnn`
- `<REQ_DIR>/domains/<domaine>/` : documentation durable, avec `use-cases/`, `rules.md`, `objects.md`, `requirements.md`
- `<REQ_DIR>/shared/` : `vision.md`, `context.md`, `actors.md`, `glossary.md`
- `<REQ_DIR>/work/<epic>/` : zone temporaire (demande, Epic, US en cours), supprimée une fois implémentée

Identifiants : `SRC-nnn` (source), `GOAL-nn` (objectif), `US-nnn` (user story), `UC-<DOMAINE>-nn` (cas d'utilisation ;
DOMAINE en majuscules, sans espace). Le tag Gherkin d'un scénario est `@UC-<DOMAINE>-nn`, sensible à la casse.
Un identifiant mal saisi est une erreur à signaler, jamais une correspondance approximative.

Provenance : toute information consignée porte l'un de ces marqueurs, en ligne.
- `[source SRC-003 « passage cité »]` : citée d'une source, avec son passage
- `[validée 2026-10-29]` : reformulée puis confirmée par le BA, avec la date
- `[hypothèse]` : déduite, non confirmée. Une hypothèse ne passe pas le DoR d'une US.
Une déclaration du BA dans la conversation est une source : l'enregistrer comme `SRC-nnn` avec la date et le texte.

Ne jamais inventer : une information manquante devient une question posée au BA, ou une `[hypothèse]` marquée.
Ne jamais combler un vide par un contenu plausible. Le BA est l'auteur : signaler un défaut et poser une question,
sans réécrire à sa place.

Le projet décide : si le projet définit un DoR, une échelle de priorité ou un gabarit, il prime sur le défaut proposé ici.

« Invoquer un skill » signifie lire son `SKILL.md` et en appliquer les instructions, quel que soit l'outil utilisé.

Si `<REQ_DIR>` ou un pré-requis est absent, le dire (voir `## Pré-requis absents`) et ne rien créer d'inventé.

## Pré-requis absents

- `<REQ_DIR>` ou `<REQ_DIR>/index.md` absent : le dire au BA (« la documentation n'est pas initialisée »), proposer l'accueil des sources (`ba-source-intake`) s'il veut déposer des documents, et ne rien créer.
- `<REQ_DIR>/work/` absent ou vide : le dire ; une US ne peut alors pas être retrouvée.
- Ce skill ne crée, ne modifie et ne supprime aucun fichier.

## Entrées / Sorties

- Entrées : la phrase du BA ; `<REQ_DIR>/index.md` (sections `## Domaines`, `## Sources`, `## Travaux en cours`) ; `<REQ_DIR>/work/<epic>/` ; au besoin `<REQ_DIR>/inbox/`.
- Sorties : aucun fichier. Une proposition en une ou deux phrases : le skill suivant et le motif. Le BA confirme avant tout enchaînement.

## Déroulement

1. Lire `<REQ_DIR>/index.md`, puis lister `<REQ_DIR>/work/`. Si un pré-requis manque, appliquer `## Pré-requis absents`.
2. Classer la demande du BA dans l'un des trois cas ci-dessous. Si aucun ne convient, demander au BA ce qu'il veut faire ; ne pas deviner.
3. Cas A, « Affinons l'US X » :
   1. Normaliser l'identifiant : ne changer que la forme, en `US-` suivi de trois chiffres (`US-12`, `US 12`, `us-012`, `US12` donnent `US-012`). Un numéro différent n'est jamais une correspondance.
   2. Chercher `US-012` dans `<REQ_DIR>/work/<epic>/` (nom de fichier et contenu).
   3. Introuvable : le dire (« US-012 n'existe pas dans `work/` »), lister les US présentes si le BA le souhaite, et demander laquelle il vise. Ne choisir aucune autre US.
   4. Trouvée : lire l'US. Si elle contient `[hypothèse]`, un point ouvert ou une provenance manquante, proposer `ba-elicitation` et citer ce qui motive ce choix. Sinon proposer `ba-story-spec`, puis `ba-quality-review` ensuite.
4. Cas B, « J'ai déposé des documents » : lister `<REQ_DIR>/inbox/`. S'il contient des documents, proposer `ba-source-intake`, puis `ba-elicitation` ensuite. S'il est vide ou absent, le dire et demander où se trouvent les documents.
5. Cas C, « Affinons le besoin X en US » :
   1. Chercher X dans `<REQ_DIR>/index.md`, `domains/`, `shared/` et `sources/`.
   2. Trouvé : proposer `ba-elicitation` pour le confirmer et le compléter, puis `ba-backlog`, puis `ba-story-spec`.
   3. Absent : le dire, puis proposer d'enregistrer la description du BA comme déclaration (source `SRC-nnn`, avec date et texte) via `ba-source-intake`, puis `ba-elicitation`, `ba-backlog`, `ba-story-spec`. Ne pas inventer le contenu de X.
6. Annoncer la proposition : « Je propose `<skill>` parce que `<motif>`. On y va ? » Attendre la réponse du BA ; s'il accepte, invoquer le skill.
7. Le BA peut toujours imposer un autre skill par son nom : l'appliquer.

## Exemple : information manquante

Le BA dit : « Affinons l'US 99. » L'identifiant normalisé est `US-099`. Aucun fichier de `<REQ_DIR>/work/` ne la contient.

Réponse : « US-099 n'existe pas dans `work/`. Les US présentes sont US-012 et US-014. Laquelle voulez-vous affiner, ou l'identifiant est-il erroné ? » Aucune autre US n'est choisie, aucun fichier n'est créé.
