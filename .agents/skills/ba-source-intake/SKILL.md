---
name: ba-source-intake
description: À utiliser quand le BA a déposé des documents dans `inbox/` (« j'ai déposé des documents », « range les sources »), ou quand il rapporte oralement un besoin ou une demande à consigner (« le client m'a dit que… », « note cette demande »). Déplace chaque document vers `sources/`, lui attribue un `SRC-nnn`, enregistre les déclarations du BA comme sources, et crée ou met à jour l'index des sources et l'index racine.
---

# Accueil des sources : ranger et indexer

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
Un identifiant TAPÉ par le BA dans sa demande (`US 8`, `us-008`, `US8`) est lu sous sa forme canonique (`US-008`,
`SRC-003`, `GOAL-02`, `UC-<DOMAINE>-nn`) : seule la FORME est normalisée (casse, séparateur, zéros initiaux), jamais le
numéro, et la forme lue est annoncée (« je lis US-008 »). Une forme qui ne se ramène pas à préfixe + numéro est signalée
et le BA est interrogé. Un identifiant déjà ÉCRIT dans un fichier et mal formé est signalé, jamais corrigé.

Provenance : toute affirmation consignée porte l'un de ces marqueurs, en ligne. Une question ou une lacune n'est pas une
affirmation : elle ne porte aucun marqueur et cite où elle a été observée, `(vu dans SRC-nnn)` ou `(contrôle de l'IA)`.
- `[source SRC-003 « passage cité »]` : citée d'une source, avec son passage
- `[validée 2026-10-29]` : reformulée puis confirmée par le BA, avec la date
- `[hypothèse]` : déduite, non confirmée. Une hypothèse ne passe pas le DoR d'une US.
Une déclaration du BA dans la conversation est une source : proposer de l'enregistrer comme `SRC-nnn` (date et texte), avec l'accord du BA.

Ne jamais inventer : une information manquante devient une question posée au BA, ou une `[hypothèse]` marquée.
Ne jamais combler un vide par un contenu plausible. Le BA est l'auteur : signaler un défaut et poser une question,
sans réécrire à sa place.

Le projet décide : si le projet définit un DoR, une échelle de priorité ou un gabarit, il prime sur le défaut proposé ici.

« Invoquer un skill » signifie lire son `SKILL.md` et en appliquer les instructions, quel que soit l'outil utilisé.

Si `<REQ_DIR>` ou un pré-requis est absent, le dire (voir `## Pré-requis absents`) et ne rien créer d'inventé.

## Pré-requis absents

- `<REQ_DIR>` absent : le dire au BA. Le créer (avec `inbox/` et `sources/`) seulement s'il le confirme.
- `<REQ_DIR>/inbox/` absent ou vide, et aucune déclaration du BA : le dire (« rien à ranger : `inbox/` est vide ») et demander où sont les documents ou ce qu'il veut déclarer. Ne rien créer, ne rien indexer.
- `<REQ_DIR>/index.md` absent : le créer (étape 8) avec les trois sections, vides si besoin. Ne pas y inventer de domaine ni de travail en cours.
- `<REQ_DIR>/sources/index.md` absent : le créer. S'il y a déjà des fichiers `SRC-nnn-*` dans `sources/` sans ligne dans l'index, les signaler au BA ; ne pas les indexer d'office (titre et date inconnus), mais leur numéro est pris.

## Entrées / Sorties

- Entrées : les fichiers de `<REQ_DIR>/inbox/` ; ou une déclaration du BA dans la conversation ; `<REQ_DIR>/sources/` et `<REQ_DIR>/index.md` s'ils existent.
- Sorties :
  - `<REQ_DIR>/sources/SRC-nnn-<slug>.<ext>` : le document déplacé (et non copié) depuis `inbox/`
  - `<REQ_DIR>/sources/SRC-nnn-declaration.md` : pour une déclaration du BA
  - `<REQ_DIR>/sources/index.md` : tableau `ID | Type | Date | Titre | Fichier`
  - `<REQ_DIR>/index.md` : créé s'il est absent, sinon seule la section `## Sources` est mise à jour

## Déroulement

1. Vérifier `## Pré-requis absents`. Lister `<REQ_DIR>/inbox/` (fichiers seulement ; ignorer les fichiers cachés ; signaler au BA tout sous-dossier, sans le traiter) et lire `<REQ_DIR>/sources/index.md`. Dire au BA ce qui va être traité : documents trouvés, ou déclaration.
2. Date : la date d'enregistrement du jour, sauf si le BA fournit la date du document. Ne pas la déduire du contenu.
3. Déterminer le prochain numéro : prendre le plus grand `nnn` parmi les lignes de `sources/index.md` ET les noms de fichiers `SRC-nnn-*` présents dans `sources/`, puis ajouter 1. Un numéro n'est jamais réutilisé, même si son fichier a disparu de l'index. Numéroter sur 3 chiffres (`SRC-007`). Les documents se traitent dans l'ordre alphabétique des noms, un numéro chacun.
4. Pour chaque document de `inbox/` (en utilisant la date décidée à l'étape 2) :
   1. Si c'est un fichier texte (`.md`, `.txt`, `.csv`, `.json`), le lire pour en tirer un titre. Si le titre n'apparaît pas clairement, demander au BA ; à défaut utiliser le nom du fichier. Ne rien résumer, ne rien citer à ce stade.
   2. Si ce n'est pas du texte (`.pdf`, image, tableur, archive) ou si le fichier est illisible : ne pas deviner son contenu. Titre = nom du fichier ; l'indiquer dans la colonne Titre par « (contenu non lu) ». Ne jamais affirmer ni citer son contenu ; proposer au BA de le décrire (ce sera alors une déclaration qui porte `Concerne : SRC-nnn`, voir étape 5).
   3. Construire le slug : nom du fichier sans extension, en minuscules, sans accent, les espaces et signes remplacés par des tirets. Garder l'extension d'origine.
   4. Si un fichier de même nom cible existe déjà dans `sources/`, ne rien écraser : s'arrêter sur ce document et poser la question au BA.
   5. Déplacer le fichier vers `sources/SRC-nnn-<slug>.<ext>` (déplacement : il ne reste plus rien dans `inbox/`). Ne jamais modifier le contenu du document.
5. Pour une déclaration du BA : si le texte n'est pas encore donné mot pour mot, le demander. Créer `sources/SRC-nnn-declaration.md` avec :
   - `# SRC-nnn : Déclaration du BA du <date>` : ce titre, avec la date d'enregistrement (AAAA-MM-JJ), est aussi celui de la ligne d'index et de la ligne de l'index racine ;
   - `Date : <date>` ;
   - `Concerne : SRC-nnn` (numéro du document décrit), seulement si la déclaration décrit une source déjà enregistrée, par exemple un document « contenu non lu » ;
   - `## Texte verbatim` : les mots du BA, tels quels, sans correction ni ajout ;
   - `## Reformulation` : une reformulation proposée. Demander au BA de la confirmer ou de la corriger. Tant qu'il n'a pas répondu, écrire `[hypothèse]` ; après sa confirmation explicite seulement, remplacer par `[validée <date du jour>]`. S'il corrige au lieu de confirmer, écrire sa version corrigée, qui reste `[hypothèse]` jusqu'à ce qu'il confirme cette version corrigée. S'il ne veut pas reformuler, écrire « aucune reformulation ».
6. Ajouter à `sources/index.md` une ligne par source, sauf si une ligne portant déjà cet identifiant SRC existe (ne jamais dupliquer) (créer l'en-tête du tableau si le fichier est neuf) : `| SRC-nnn | document ou déclaration du BA | date | titre | nom du fichier ou — |`. Pour une déclaration, la colonne Fichier contient `—` (le texte est dans `SRC-nnn-declaration.md`).
7. Ne pas supprimer ni renommer les lignes existantes de l'index.
8. Mettre à jour `<REQ_DIR>/index.md` : s'il est absent, le créer avec les sections `## Domaines`, `## Sources`, `## Travaux en cours`. Sous `## Sources`, ajouter, sauf si une ligne avec cet identifiant SRC existe déjà, une ligne `- SRC-nnn : <titre> (<type>, <date>)` par source enregistrée, avec un seul groupe entre parenthèses ; pour un document non lu, ajouter un troisième élément : `- SRC-005 : rapport.pdf (document, 2026-10-29, contenu non lu)`. La ligne ne porte aucun chemin de fichier : le tableau `sources/index.md` fait foi pour la colonne Fichier, sans toucher aux autres sections ni aux lignes existantes.
9. Annoncer ce qui a été fait : sources enregistrées (numéro, titre, type), documents « contenu non lu », fichiers et sous-dossiers laissés dans `inbox/` et pourquoi, questions en attente (dont toute reformulation de déclaration à confirmer ou corriger). Proposer ensuite `ba-elicitation` ; le BA confirme avant tout enchaînement.

## Exemple : information manquante

`inbox/` contient `rapport.pdf`. Le plus grand numéro existant est `SRC-004`.

Le skill déplace le fichier vers `sources/SRC-005-rapport.pdf`, ajoute `| SRC-005 | document | 2026-10-29 | rapport.pdf (contenu non lu) | SRC-005-rapport.pdf |`, puis dit : « J'ai enregistré SRC-005 mais je n'ai pas lu son contenu : je ne peux rien en citer. Pouvez-vous m'en décrire l'essentiel ? Ce sera enregistré comme une déclaration de votre part. » Aucun passage n'est cité ni résumé.
