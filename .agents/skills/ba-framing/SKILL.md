---
name: ba-framing
description: À utiliser quand le BA veut cadrer un besoin avant de le découper en Epics et US (« cadrons ce besoin », « quel est le vrai problème ? », « quels sont les objectifs ? », « on est assez mûrs pour passer au backlog ? », « fais le Lean Canvas »). Pose le problème, la vision, les objectifs (arbre des buts avec `GOAL-nn` reliés à leurs sources), les hypothèses risquées (Lean Canvas), le contexte, les acteurs, le glossaire et la grille de maturité ; écrit `work/<epic>/framing.md` et complète `shared/`.
---

# Cadrage : problème, vision, objectifs

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

- `<REQ_DIR>` absent : le dire au BA et s'arrêter ; ne rien créer sans sa confirmation.
- Nom d'epic non donné : demander un nom court (minuscules, tirets, sans accent). Ne jamais l'inventer.
- `work/<epic>/elicitation.md` absent et aucune source `SRC-nnn` utile : le dire, proposer `ba-elicitation` (le BA confirme avant tout enchaînement) ; si le BA préfère continuer, ses réponses sont les seules entrées, toutes `[hypothèse]`.
- Aucun objectif exprimé nulle part (ni dans l'élicitation, ni dans les sources, ni dans `shared/vision.md`, ni par le BA) : le demander (étape 3). Ne jamais écrire un `GOAL` de sa propre initiative.
- `shared/vision.md`, `context.md`, `actors.md`, `glossary.md` absents : le dire, et ne les créer qu'avec l'accord du BA, à l'étape 15, avec leur titre fixe (voir Sorties).

## Entrées / Sorties

- Entrées : la demande du BA ; `work/<epic>/elicitation.md` ; les sources `SRC-nnn` pertinentes ; les fichiers de `shared/` ; tout `work/*/` pour la numérotation des `GOAL`.
- Sortie principale : `<REQ_DIR>/work/<epic>/framing.md`, intitulé `# Cadrage : <epic>`, avec exactement les sections `## Problème`, `## Vision`, `## Lean Canvas`, `## Arbre des buts`, `## Hypothèses risquées`, `## Contexte et acteurs`, `## Grille de maturité`, `## Questions ouvertes`.
- Sorties partagées (durables : ajouter des lignes, ne jamais supprimer ni réécrire une ligne existante) :
  - `shared/vision.md`, titre `# Vision`, sections `## Vision` et `## Objectifs` ;
  - `shared/context.md`, titre `# Contexte`, sections `## Diagramme` (bloc Mermaid) et `## Systèmes externes et flux` ;
  - `shared/actors.md`, titre `# Acteurs`, lignes `- <rôle> : <intérêt ou responsabilité> <marqueur>` ;
  - `shared/glossary.md`, titre `# Glossaire`, lignes `- **<terme>** : <définition> <marqueur>`.
- Marqueurs : les affirmations (problème, vision, buts, cases du canvas, acteurs, termes, niveaux de la grille) portent un marqueur ; une ligne reprise d'`elicitation.md` ou d'un fichier partagé garde le sien ; une question cite `(vu dans SRC-nnn)` ou `(contrôle de l'IA)` ; une case vide du canvas est `— (à documenter)`, sans marqueur.

## Grille de maturité

Quatre niveaux, définis ici : `Absent` (rien d'écrit), `Esquissé` (des éléments présents, mais pas tout le critère de `Clair`), `Clair` (critère de l'axe rempli, voir ci-dessous), `Solide` (`Clair` ET confirmé par le BA). Cinq axes ; les sous-axes sont des questions guides, jamais notées.
- Description du problème. `Clair` quand l'acteur touché, le préjudice et au moins une preuve (une source ou un chiffre) sont écrits. Guides : fréquence ? que se passe-t-il si rien ne change ?
- Description des besoins métier. `Clair` quand chaque besoin a un acteur et un résultat attendu. Guides : quelle mesure de réussite ?
- Besoins non fonctionnels. `Clair` quand chaque qualité pertinente a une cible chiffrée ou un « aucun » explicite. Guides : volumes, délais, disponibilité, sécurité, exploitation, accessibilité.
- Hypothèses et contraintes. `Clair` quand les hypothèses risquées sont listées, chacune avec sa façon d'être vérifiée. Guides : échéance, budget, réglementation, existant imposé ?
- Description de la solution (axe inversé : les mêmes critères se lisent à l'envers, `Clair` ou `Solide` = ALERTE). `Absent` = rien d'imposé (sain) ; `Esquissé` = une idée de moyen mentionnée (à surveiller) ; `Clair` ou `Solide` = moyen précis ou conçu avant que le problème soit clair : le signaler au BA et renvoyer vers les questions objectif / moyen / mesure / risque de `ba-elicitation`.
Un niveau est une proposition `[hypothèse]` justifiée par une citation ; le BA le confirme ou le corrige.

## Déroulement

Chaque contenu est proposé au BA, jamais écrit comme acquis. Poser une question à la fois.
1. Vérifier `## Pré-requis absents`. Obtenir le nom de l'epic. Si `work/<epic>/framing.md` existe, le lire : on le complète sans supprimer ni réécrire ses lignes ni dupliquer une ligne.
2. Lire `elicitation.md`, les sources concernées et `shared/` (`vision.md`, `actors.md`, `glossary.md`, `context.md`). Calculer le prochain numéro de but : relever tous les `GOAL-nn` de `work/` (tous les fichiers) et de `shared/vision.md`, prendre le plus grand `nn`, ajouter 1 (`GOAL-01` si aucun). Un identifiant mal formé (`GOAL-1`, `GOAL-A`) est signalé au BA, jamais corrigé ni ignoré en silence.
3. Chercher un objectif exprimé (élicitation `## Besoins`, sources, `shared/vision.md`). S'il n'y en a aucun : demander « Quel résultat attendez-vous de ce besoin, et pour qui ? », attendre, et ne rien écrire dans l'arbre des buts avant la réponse. La réponse est une déclaration : proposer `ba-source-intake` pour l'enregistrer (avec l'accord du BA) ; d'ici là elle est `[hypothèse]`.
4. Problème : proposer un énoncé en une ou deux phrases à partir des seuls éléments lus, avec marqueur. Si la demande décrit une solution, mener une boucle « une question à la fois », dans cet ordre, en attendant chaque réponse avant la suivante : (a) objectif, (b) moyen, (c) mesure, (d) risque (une question chacun) ; puis, si le besoin sous-jacent reste flou, (e) les 5 Pourquoi, un « pourquoi » à la fois (remonter de la solution à l'intention) ; puis (f) les 5 W, un par un (qui, quoi, où, quand, pourquoi). S'arrêter dès que le besoin sous-jacent est clair. Le besoin sous-jacent est proposé `[hypothèse]` ; la demande d'origine est conservée.
5. Vision : proposer une phrase (pour qui, quel changement, quel bénéfice). Sans matière suffisante, poser la question au lieu de la rédiger.
6. Arbre des buts : un but stratégique répond à « pourquoi ? », ses sous-buts à « comment ? ». Écrire chaque but `- GOAL-nn : <objectif> <marqueur>` ; les sous-buts sont indentés sous leur but parent. Le marqueur cite la source du but (copié de l'élicitation si la ligne vient de là). Un but sans source est une proposition `[hypothèse]` que le BA doit valider. Numéroter à partir du numéro calculé à l'étape 2.
7. Lean Canvas : neuf cases, chacune une ligne `- **<case>** : <contenu> <marqueur>` : Problème, Segments de clientèle, Proposition de valeur, Solution, Canaux, Sources de revenus, Structure de coûts, Indicateurs clés, Avantage concurrentiel. Une case sans information reste `— (à documenter)` : ne jamais la remplir par du plausible. La case Solution ne reprend que ce que le BA ou une source a dit.
8. Hypothèses risquées, première question : lister les hypothèses de l'élicitation et celles du canvas, et demander au BA lesquelles sont risquées (si elles étaient fausses, le besoin tombe). Attendre la réponse.
9. Hypothèses risquées, seconde question : pour chaque hypothèse retenue, demander comment la vérifier. Écrire `- <hypothèse> : risque <élevé, moyen ou faible selon le BA> ; vérification : <réponse du BA ou « à définir »> <marqueur>`.
10. Contexte et acteurs : proposer les acteurs (sources, élicitation, `actors.md` : exploitation et administration comprises) et les systèmes externes avec leurs flux. Les proposer pour `shared/actors.md` (lignes absentes seulement, ajoutées à l'étape 15 après confirmation) ; si un rôle existe avec un autre intérêt, ne pas toucher à la ligne et poser une question. Dans `## Contexte et acteurs` de `framing.md`, un diagramme Mermaid `flowchart LR` : le système au centre, acteurs et systèmes externes autour, flux étiquetés. Si `shared/context.md` a déjà un diagramme, ne pas le réécrire : proposer d'ajouter un bloc `### Ajout <epic> <date>` (diagramme et flux de cet epic) à l'étape d'écriture (étape 15), après confirmation.
11. Glossaire : relever les termes métier récurrents ou ambigus et proposer `- **terme** : définition <marqueur>`. Un même terme qui a deux sens selon la partie du système est noté deux fois avec `(contexte : <nom>)` : c'est le langage commun par contexte. Proposer pour `shared/glossary.md` (ajout à l'étape 15 après confirmation) si le terme n'y est pas ; s'il y est avec une autre définition, laisser la ligne et poser la question.
12. Confirmation du contenu : présenter à la suite problème, vision, buts, canvas, hypothèses risquées, acteurs, termes, puis le diagramme de contexte, les systèmes externes et leurs flux (une confirmation pour chacun de ces trois derniers). Tout contenu à écrire à l'étape 15 est listé ici. Demander « Confirmez-vous, ou corrigez-vous, ligne par ligne ou en bloc ? ». Seulement après un « oui » explicite, remplacer `[hypothèse]` par `[validée <date du jour>]` pour les lignes confirmées ; une ligne corrigée redevient `[hypothèse]` jusqu'à nouvelle confirmation.
13. Notation de la grille, après l'étape 12 : pour chaque axe, appliquer le critère de la section « Grille de maturité » et proposer un niveau avec sa citation. Écrire le tableau sous `## Grille de maturité` : en-tête `| Axe | Niveau | Justification | Statut |`, séparateur `|---|---|---|---|`, une ligne par axe, colonne Statut `[hypothèse]`. Signaler toute alerte sur l'axe solution.
14. Confirmation des niveaux : demander « Confirmez-vous ces niveaux, ou les corrigez-vous ? ». Après le « oui », mettre `[validée <date du jour>]` en Statut ; un niveau confirmé qui remplit le critère de `Clair` devient `Solide`. Un niveau corrigé reste `[hypothèse]` jusqu'à nouvelle confirmation.
15. Écrire `work/<epic>/framing.md` (créer le dossier au besoin) : les `GOAL` sont dans `## Arbre des buts`, ligne `- GOAL-nn : <objectif> <marqueur>`. Puis ajouter à `shared/` : vision dans `vision.md` `## Vision`, buts dans `## Objectifs` avec ce même format de ligne, acteurs, termes, flux ; dans `context.md`, `## Diagramme` et `## Systèmes externes et flux` confirmés à l'étape 12 (ou, si un diagramme existe déjà, le bloc `### Ajout <epic> <date>`, rien d'existant n'étant réécrit) ; créer un fichier absent seulement avec l'accord du BA. Lister les questions restantes dans `## Questions ouvertes`. Relire : chaque assertion a son marqueur, chaque question sa citation, aucune case de canvas ni aucun but inventé.
16. Annoncer : les chemins écrits, le nombre de buts, les cases vides du canvas, les alertes de la grille, la liste numérotée des questions ouvertes et des lignes `[hypothèse]` à confirmer. Règle de passage : proposer `ba-backlog` quand Problème et Besoins métier sont au moins `Clair` et qu'il n'y a pas d'alerte sur l'axe solution ; sinon proposer `ba-elicitation` en nommant les axes faibles. Le BA décide (le projet peut remplacer cette règle) et confirme avant tout enchaînement.

## Exemple : information manquante

Le BA dit : « Cadrons le besoin de réservation » ; `elicitation.md` liste des besoins mais aucun résultat attendu, aucune source ne cite un objectif, `shared/vision.md` est vide.

Le skill ne rédige aucun `GOAL`. Il demande : « Quel résultat attendez-vous de la réservation, et pour qui ? ». Le canvas laisse `- **Sources de revenus** : — (à documenter)`. `## Questions ouvertes` : `- Quel indicateur mesurera la réussite ? (contrôle de l'IA)`.
