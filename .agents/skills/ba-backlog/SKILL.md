---
name: ba-backlog
description: À utiliser quand le BA veut découper un besoin cadré en Epics et en user stories (« construis le backlog », « découpe en US », « fais le story mapping », « priorise avec MoSCoW », « ajoute une US à cet epic », « on passe au backlog »). Produit `work/<epic>/backlog.md` : Epics, Features facultatives, US `US-nnn` avec domaine(s), objectifs `GOAL-nn`, sources, priorité MoSCoW et critère ; met à jour `index.md`.
---

# Construction du backlog

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
- Nom d'epic non donné : le demander (minuscules, tirets, sans accent), ne jamais l'inventer.
- `work/<epic>/framing.md` absent : le dire et proposer `ba-framing` (le BA confirme avant tout enchaînement). Sans cadrage, aucun `GOAL-nn` n'existe : ne pas en écrire ; si le BA veut continuer, il fournit lui-même les objectifs, qui restent `[hypothèse]`.
- `work/<epic>/elicitation.md` absent : le dire et proposer `ba-elicitation` ; si le BA préfère continuer, ses réponses sont les seules entrées, toutes `[hypothèse]`.
- `shared/actors.md` absent ou rôle absent de ses lignes et des sources : demander le rôle au BA, ne jamais l'inventer.
- `domains/` absent ou vide : demander au BA le nom du domaine ; un nom confirmé par le BA est accepté, jamais un nom deviné.
- `shared/glossary.md` absent : le dire et continuer, sans créer de glossaire.

## Entrées / Sorties

- Entrées : `work/<epic>/framing.md` (buts : lignes `- GOAL-nn : <objectif> <marqueur>` sous `## Arbre des buts`) ; `work/<epic>/elicitation.md` ; `shared/actors.md` ; `shared/glossary.md` ; les noms de dossiers de `domains/` ; tout `work/*/` pour la numérotation ; `work/<epic>/backlog.md` s'il existe.
- Sortie principale : `<REQ_DIR>/work/<epic>/backlog.md`, intitulé `# Backlog : <epic>`, avec exactement les sections `## Epics`, `## Features` (présente seulement si le niveau Feature est retenu), `## Carte`, `## User stories`, `## Questions ouvertes`.
  - Epic, une ligne : `- EPIC : <titre> | buts : GOAL-nn[, GOAL-nn] <marqueur>`.
  - Feature, une ligne : `- FEATURE : <titre> | epic : <titre de l'Epic> <marqueur>`.
  - Carte (story mapping), une ligne par activité du parcours, dans l'ordre du parcours : `- <activité> : US-nnn[, US-nnn]` (les US d'une activité sont rangées de la plus à la moins prioritaire).
  - US : un titre `### US-nnn : <titre>` (c'est ce titre que `ba-story-spec` et `ba-entrypoint` cherchent) suivi de sept lignes, dans cet ordre :
    `- Récit : En tant que <rôle>, je veux <action> afin de <valeur> <marqueur>`
    `- Rattachement : epic : <titre de l'Epic> ; feature : <titre ou —>`
    `- Domaine(s) : <nom>[, <nom>] <marqueur>` (deux noms : ajouter `multi-domaine`)
    `- Buts : GOAL-nn[, GOAL-nn]`
    `- Sources : <marqueur de source copié, un par SRC-nnn>` (sans source : `— (aucune)`)
    `- Priorité : <Must, Should, Could ou Won't> | critère : <une ligne> [hypothèse]` (marqueur `[validée <date>]` seulement après la décision du BA)
    `- INVEST : <I N V E S T avec ✔ ✖ ou ?>` (six symboles dans l'ordre I, N, V, E, S, T, séparés par des espaces, par exemple `- INVEST : ✔ ✔ ✔ ? ✖ ✔` ; `✔` test réussi, `✖` test non réussi et question posée au BA, US conservée telle quelle sur sa réponse, `?` question posée et pas encore répondue ; aucun marqueur de provenance sur cette ligne, c'est un contrôle de l'IA)
- Sortie secondaire : `<REQ_DIR>/index.md`, section `## Travaux en cours`, une ligne par epic : `- <epic> : backlog (<n> US, <date>)`, où `<date>` est la date du jour (AAAA-MM-JJ). Ici « epic » (le dossier `work/<epic>/`) n'est pas un « Epic » (une ligne `- EPIC :` de `backlog.md`).
- Marqueurs : récit, domaine(s), rattachement et priorité sont des assertions et portent un marqueur ; une ligne copiée de `framing.md` ou `elicitation.md` garde le sien ; les lignes de `## Questions ouvertes` citent `(vu dans SRC-nnn)` ou `(contrôle de l'IA)` et n'ont pas de marqueur.

## Critères de priorité

MoSCoW : `Must` (sans elle le besoin n'est pas servi), `Should` (importante, contournable un temps), `Could` (utile, sans conséquence si absente), `Won't` (écartée pour cette livraison, consignée). La classe se justifie par UN critère nommé, écrit en une ligne avec les mots du BA ou de la source : valeur (ce que l'acteur y gagne), risque (ce qui arrive sans elle ou si elle échoue), coût ou difficulté, urgence (une échéance), dépendance (une autre US ne peut pas exister sans elle). Une classe sans critère est interdite. L'IA propose, le BA décide.

## Déroulement

Chaque contenu est proposé, jamais écrit comme acquis : rien n'est écrit dans un fichier avant l'étape 14. Une question à la fois.
1. Vérifier `## Pré-requis absents`. Obtenir le nom de l'epic. Si `work/<epic>/backlog.md` existe, le lire : on le complète (ajout d'US ou d'Epic) sans supprimer ni réécrire ses lignes ni dupliquer un titre. Relever les US existantes de l'Epic dont la ligne `- INVEST :` contient `?` ou manque : l'étape 11 les reprend, elles seules.
2. Lire `framing.md`, `elicitation.md`, `actors.md`, `glossary.md` et lister les dossiers de `domains/`. Relever les buts (`GOAL-nn`) et, dans `elicitation.md`, les besoins avec leur marqueur.
3. Numérotation : relever tous les `US-nnn` de tous les fichiers de `work/` (titres `### US-nnn` et mentions), prendre le plus grand `nnn`, ajouter 1 pour la première nouvelle US (`US-001` si aucun) ; ne jamais réutiliser un numéro, même d'une US supprimée ou d'un autre epic. Un identifiant mal formé (`US-8`, `US-A`) ou un titre `### US-nnn` en double dans un ou plusieurs fichiers est signalé au BA, jamais corrigé ni ignoré en silence.
4. Niveau Feature : l'utiliser si le BA le demande, à tout moment du déroulement. Sinon : Epic puis US directement (le seuil de l'étape 8 peut le proposer plus tard).
5. Story mapping : proposer les activités du parcours de l'acteur, dans l'ordre, tirées des besoins de l'élicitation (une activité sans besoin cité est une question, pas une ligne).
6. Proposer les Epics : regrouper les activités par but (`GOAL-nn`) ; chaque Epic cite ses buts. Si le niveau Feature est retenu, proposer ses Features.
7. Rédiger chaque US en format récit. Le rôle vient de `actors.md` ou d'une source ; le domaine vient de `domains/` ou du BA ; la source reprend le marqueur de la ligne d'élicitation d'où elle vient. Une US sans source garde son récit `[hypothèse]`. Une US qui touche deux domaines est gardée, signalée `multi-domaine`, et le BA décide (une question) s'il la découpe ; la découpe est son choix.
8. Seuil Feature, après la rédaction des US : compter, pour chaque Epic, les US déjà présentes dans `backlog.md` plus les US proposées aux étapes 6 et 7. Si un Epic dépasse 8 US et que le niveau Feature n'est pas déjà retenu, poser UNE question « Cet Epic dépasse 8 US : voulez-vous les regrouper en Features ? », ne rien faire d'autre tant que le BA n'a pas répondu, puis suivre sa réponse (oui : retourner aux étapes 6 et 7 pour proposer les Features et le rattachement des US, puis reprendre à l'étape 9, sans poser la question de l'étape 9 avant la fin du regroupement ; non : passer directement à l'étape 9). Sous le seuil, ne rien proposer.
9. Une US sans `GOAL` : ne lui donner aucun but par défaut. Poser la question « Quel objectif cette US sert-elle ? » (et lister les buts existants), attendre, et laisser l'US hors du backlog écrit tant qu'il n'y a pas de réponse (elle va en `## Questions ouvertes`). Même règle pour un rôle ou un domaine inconnu.
10. Prioriser : pour chaque US, proposer une classe MoSCoW et son critère unique (section « Critères de priorité »), `[hypothèse]`.
11. Contrôle INVEST, pour chaque US proposée (hors US laissées en questions ouvertes à l'étape 9) et pour chaque US existante relevée à l'étape 1 (ligne `- INVEST :` avec `?` ou absente ; les autres US existantes ne sont pas retestées), un test par lettre ; les US issues d'une découpe acceptée par le BA passent aussi par ce contrôle :
    - I Indépendante : aucune autre US ne doit être livrée avant elle ; sinon nommer la dépendance et la demander au BA.
    - N Négociable : le récit décrit un besoin, pas une solution (écran, table, technologie, bouton) ; sinon citer le mot en cause.
    - V Valorisable : le « afin de » énonce une valeur pour un acteur nommé.
    - E Estimable : rien d'inconnu ne bloque le dimensionnement par le BA ou l'équipe ; sinon nommer l'inconnu.
    - S Petite : livrable en un sprint ; plus de 5 scénarios attendus ou plusieurs actions distinctes dans le récit : proposer de la découper, le BA décide.
    - T Testable : un résultat observable peut être nommé ; sinon demander lequel.
    Une lettre non réussie devient UNE question au BA (une à la fois, US par US), jamais une réécriture du récit ni une découpe faite en silence. Noter `?` tant que le BA n'a pas répondu ; après sa réponse, le BA corrige le récit (la ligne corrigée redevient `[hypothèse]`) et la lettre devient `✔`, ou il garde l'US telle quelle et la lettre devient `✖`. Montrer le résultat par US sous la forme `US-nnn : I N V E S T` avec ✔, ✖ ou ?. Pour une US existante, seule sa ligne `- INVEST :` change (ajoutée si elle manquait) ; son récit n'est pas modifié ici : si le BA veut le changer, il le fait lui-même et la lettre reste `?`, avec une ligne dans `## Questions ouvertes`. Si tout est réussi, le dire et ne poser aucune question.
12. Contrôle de traçabilité : chaque `GOAL-nn` de `framing.md` a-t-il au moins une US ? chaque US a-t-elle un but ? Un but sans US est une question (`(contrôle de l'IA)`). Si tout est couvert, le dire et ne rien ajouter.
13. Confirmation : présenter Epics, Features, carte, US avec priorités et résultat INVEST. Demander « Confirmez-vous, ou corrigez-vous, US par US, notamment les priorités ? ». Seulement après un « oui » explicite, mettre `[validée <date du jour>]` sur les lignes confirmées ; une ligne corrigée redevient `[hypothèse]` jusqu'à nouvelle confirmation.
14. Écrire `work/<epic>/backlog.md` au format de `## Entrées / Sorties`, puis mettre à jour `index.md` (section `## Travaux en cours` : ajouter la ligne de l'epic si aucune ligne `- <epic> :` n'existe, sinon mettre à jour son comptage ; créer la section si elle manque ; ne pas toucher aux autres lignes). Pour les US existantes retestées, ne mettre à jour que leur ligne `- INVEST :`. Relire : chaque US a son titre `### US-nnn : <titre>`, ses sept lignes (dont `- INVEST :`), un `GOAL`, un critère de priorité ; aucun numéro dupliqué.
15. Annoncer : le chemin écrit, le nombre d'Epics et d'US, les numéros attribués, les US multi-domaines, les buts sans US, les questions ouvertes et les lignes `[hypothèse]` restantes. Proposer `ba-story-spec` pour détailler une US (elle signalera les hypothèses d'abord) ou `ba-quality-review` pour contrôler le backlog ; le BA décide et confirme avant tout enchaînement. Aucun scénario Gherkin ni exigence détaillée n'est écrite ici.

## Exemple : information manquante

Le BA dit : « Ajoute au backlog une US pour exporter la liste des retards ». `framing.md` contient `GOAL-01` (réservation) et `GOAL-02` (suivi des prêts), aucun ne parle d'export.

Le skill ne choisit aucun but. Il demande : « Quel objectif cette US sert-elle ? Objectifs existants : GOAL-01 (réservation), GOAL-02 (suivi des prêts), ou un nouvel objectif à cadrer avec `ba-framing`. » Il attend la réponse ; en attendant, l'US n'est pas écrite dans `## User stories` et `## Questions ouvertes` contient : `- Quel objectif sert l'export de la liste des retards ? (contrôle de l'IA)`.

### Scénario 10 : US existante dont la ligne INVEST contient `?`

**État du dépôt** : `work/reservation/backlog.md` contient `US-002` avec `- INVEST : ✔ ✔ ✔ ? ✔ ✔` et `US-003` avec `- INVEST : ✔ ✔ ✔ ✔ ✔ ✔` ; le BA ne demande aucune nouvelle US.

**Message du BA** : « Reprends le backlog de la réservation, il reste une question INVEST. »

**Attendu** : seule `US-002` est reprise par le contrôle INVEST ; le skill pose UNE question sur E (l'inconnu qui bloque l'estimation) ; après la réponse, il met à jour uniquement la ligne `- INVEST :` de `US-002` (`✔` ou `✖` selon le BA) ; `US-003` et le reste du fichier ne changent pas.

**Interdit** : retester `US-003` ; réécrire le récit de `US-002` ; modifier une autre ligne ; laisser `?` après une réponse du BA.
