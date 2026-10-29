---
name: ba-story-spec
description: À utiliser quand le BA veut détailler UNE user story du backlog avant son développement (« affinons l'US 12 », « spécifie cette US », « écris les scénarios de l'US-004 », « est-elle prête pour le développement ? »). Fait d'abord l'état des lieux de l'US (hypothèses, points ouverts, provenance manquante, écart au DoR), pose les questions au BA, puis écrit `work/<epic>/US-nnn.md` : récit, exigences, scénarios Gherkin nominaux et d'erreur taggés `@UC-…`, exigences non fonctionnelles et DoR vérifié.
---

# Spécifier une user story

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

- `<REQ_DIR>` absent, ou aucun `work/*/backlog.md` : le dire, proposer `ba-backlog` (ou `ba-entrypoint`), ne rien créer.
- Aucune ligne `### US-nnn : <titre>` portant exactement l'identifiant demandé dans un `backlog.md` (une simple mention ailleurs ne compte pas) : le dire et demander l'identifiant au BA. Plusieurs backlogs avec ce titre : signaler le conflit, demander lequel garder.
- Identifiant d'US tapé par le BA : normalisé comme dans les conventions (`US 8` donne `US-008`, forme lue annoncée) ; forme irréductible (`US-A`) : la signaler et interroger le BA. Identifiant trouvé écrit dans un fichier et mal formé : le signaler, jamais le corriger.
- Ni sources citées ni élicitation pour cette US : l'état des lieux le consigne (provenance manquante) ; ne rien déduire. Aucun cas d'utilisation dans `domains/<d>/use-cases/` : le cas visé est « à créer ».

## Entrées / Sorties

- Entrées : l'US dans `work/<epic>/backlog.md` (titre `### US-nnn : <titre>` et ses sept lignes : Récit, Rattachement, Domaine(s), Buts, Sources, Priorité, INVEST) ; `framing.md`, `elicitation.md`, `open-questions.md` (constats de `ba-quality-review`), les `sources/` citées ; `domains/<d>/use-cases/`, `rules.md`, `objects.md` (formats de `ba-as-is-reading`) ; `shared/actors.md`, `glossary.md`, `context.md` ; le DoR du projet s'il existe.
- Sortie : `<REQ_DIR>/work/<epic>/US-nnn.md`, intitulé `# US-nnn : <titre>`, avec exactement les sections, dans cet ordre : `## État des lieux`, `## Récit`, `## Exigences`, `## Scénarios`, `## Exigences non fonctionnelles`, `## DoR`, `## Questions ouvertes`.
  - État des lieux, cinq lignes : `- Date : <date>` ; `- Hypothèses : <n>` ; `- Points ouverts : <n>` ; `- Provenance manquante : <n>` ; `- Écart au DoR : <critères non satisfaits, ou —>` ; en dessous, une sous-ligne par hypothèse, point ou manque, avec sa citation.
  - Récit : la ligne `- Récit` copiée du backlog, avec son marqueur, puis la ligne `- Domaine(s) : <d1[, d2]>` copiée de la ligne Domaine(s) du backlog (noms de domaines jamais inventés). `<DOMAINE>` de `UC-<DOMAINE>-nn` est la forme en majuscules du nom du dossier de domaine (dossier `reservation`, identifiant `UC-RESERVATION-01`).
  - Exigences : une ligne par exigence, en forme normalisée (voir `## Rédaction des exigences`), suivie de son marqueur : `- [<condition>, ]l'utilisateur <rôle> doit pouvoir <action> <objet> <complément> <marqueur>` ou `- [<condition>, ]le système <nom> doit <action> <objet> <complément> <marqueur>` ; pour une US multi-domaine, chaque ligne d'exigence se termine par `(domaine : <d>)` avant son marqueur (US mono-domaine : omis) ; puis, seulement si le DoR applicable a un critère de dépendances (étape 12), la ligne `- Dépendances : <US-nnn, système ou « aucune »> <marqueur>` (`[validée <date>]` après la réponse explicite du BA et sa confirmation, sinon `[hypothèse]`).
  - Scénarios : d'abord `Cas d'utilisation visé : UC-<DOMAINE>-nn (existant | à créer)` (pour « à créer », le numéro est le plus grand existant du domaine plus 1 au moment de la spécification, et `ba-sync` le revérifie à la création : une collision est son écart), puis un bloc de code `gherkin` : première ligne `# language: <code>` (`fr` par défaut), `Fonctionnalité : <titre de l'US>`, puis pour chaque scénario une ligne de commentaire `# Provenance : <marqueur>`, une ligne de tag `@UC-<DOMAINE>-nn`, `Scénario : <titre>` et ses étapes. Nominal d'abord, puis erreurs et limites.
  - Exigences non fonctionnelles : une ligne par caractéristique ISO/IEC 25010 retenue, `- <caractéristique> : <cible, avec nombre et unité> <marqueur>` ou `- <caractéristique> : aucun <marqueur>` (aucun explicite du BA) ; une caractéristique sans réponse n'a pas de ligne et va en `## Questions ouvertes`. Si le BA dit qu'aucune ne s'applique : une seule ligne `- Aucune exigence non fonctionnelle <marqueur>`.
  - DoR : `Référentiel : DoR du projet (<chemin>)` ou `Référentiel : liste par défaut (non obligatoire)`, puis une ligne par critère, `- [ ] <critère>` ou `- [x] <critère> (vérifié <date>)`.
  - Questions ouvertes : lignes sans marqueur, avec `(vu dans SRC-nnn)` ou `(contrôle de l'IA)`.
- Ce skill ne crée ni ne modifie aucun fichier de cas d'utilisation : il nomme le cas visé. Le fichier du cas d'utilisation et les fichiers `.feature` sont mis à jour par `ba-sync`, que l'agent du développeur utilise à l'implémentation. Les titres de scénarios écrits dans l'US restent inchangés quand `ba-sync` crée le `.feature` et la liste `::`. US multi-domaine : une ligne `Cas d'utilisation visé :` par domaine, et chaque scénario porte le tag du cas de son propre domaine.

## Rédaction des exigences

Forme : `<condition>, ` facultative, introduite par « si », « quand » ou « tant que » (première lettre en majuscule en début de ligne), puis « l'utilisateur <rôle> doit pouvoir <action> <objet> <complément> » ou « le système <nom> doit <action> <objet> <complément> ». « doit » = obligation, « devrait » = recommandation. Le rôle vient de `shared/actors.md` ou d'une source, le nom du système des sources ou du glossaire : jamais inventés. Exemple : `- Quand le livre est déjà réservé, le système réservation doit refuser une seconde réservation [source SRC-001 « une seule réservation par livre »]`. US multi-domaine : `- Quand le livre est déjà réservé, le système réservation doit refuser une seconde réservation (domaine : reservation) [source SRC-001 « une seule réservation par livre »]` (la ligne `- Dépendances :` n'est pas une exigence et n'a pas ce suffixe).
Règles : un verbe par exigence ; pas de passif qui cache l'acteur ; pas de mot flou (voir le critère 1 de `ba-quality-review`) ; pas de quantificateur sans quantité (« plusieurs », « certains ») ; pas de « et/ou » reliant deux exigences ; un verbe à la place d'une nominalisation (« valider » et non « effectuer la validation ») ; une valeur ou limite s'écrit avec nombre et unité ; « tous », « aucun », « jamais » rendus explicites (tous quoi ?). Une exigence qui ne peut pas s'écrire dans cette forme faute d'information devient une question au BA, jamais une exigence inventée ni réécrite à sa place.

Caractéristiques ISO/IEC 25010 (liste de contrôle des exigences non fonctionnelles) : adéquation fonctionnelle (le produit fait ce qui est attendu, complètement et correctement) ; efficacité de performance (temps de réponse, débit, ressources) ; compatibilité (cohabitation et échange avec d'autres systèmes) ; utilisabilité (facilité d'apprentissage et d'usage, accessibilité) ; fiabilité (disponibilité, tolérance aux pannes, reprise) ; sécurité (confidentialité, intégrité, authentification, traçabilité) ; maintenabilité (facilité de modifier et de tester) ; portabilité (installation et adaptation à d'autres environnements).

## DoR par défaut (proposée, non obligatoire ; le DoR du projet, s'il existe, prime)

1. L'acteur est identifié. 2. La valeur attendue est énoncée. 3. Les sources sont citées. 4. Aucune hypothèse ne subsiste. 5. Des scénarios couvrent le cas nominal et les cas d'erreur. 6. Chaque caractéristique non fonctionnelle retenue a une cible validée ou un « aucun » explicite. 7. Les dépendances sont connues. 8. Le BA a donné son accord.

## Déroulement

Rien n'est écrit dans un fichier avant l'étape 17. Chaque contenu est proposé `[hypothèse]` jusqu'à la réponse du BA. Une question à la fois.
1. Vérifier `## Pré-requis absents`. Obtenir l'identifiant de l'US, la retrouver dans `backlog.md`, lire ses sept lignes. Si `work/<epic>/US-nnn.md` existe, le lire : on le complète, ses lignes confirmées ne changent que sur réponse explicite du BA (étape 5).
2. Lire `framing.md`, `elicitation.md`, `open-questions.md`, les `SRC-nnn` cités par la ligne Sources, les cas d'utilisation, `rules.md` et `objects.md` du ou des domaines, `actors.md`, `glossary.md`.
3. DoR : chercher un fichier dont le nom contient `dor` (sans tenir compte de la casse) ou une section « Definition of Ready » / « DoR » dans `<REQ_DIR>` et la documentation du projet. Une seule correspondance : l'utiliser. Aucune ou plusieurs, ou doute : demander « Où se trouve le DoR du projet, s'il y en a un ? » et attendre. Sans DoR du projet, proposer la liste par défaut en précisant qu'elle n'est pas obligatoire.
4. État des lieux : relever les hypothèses (lignes `[hypothèse]` de l'US et des documents qu'elle reprend), les points ouverts (questions des documents lus), les affirmations sans marqueur ni source, chaque constat de `open-questions.md` dont la citation dit `(vu dans US-nnn, …)` pour CETTE US (point ouvert), et l'écart au DoR. Dans la ligne `- INVEST :` du backlog, un `?` ou une ligne `- INVEST :` absente est un point ouvert (compté dans `Points ouverts`, à demander au BA ou à renvoyer vers `ba-backlog`, que le BA confirme avant tout enchaînement) ; un `✖` est une décision close du BA (échec constaté, US conservée telle quelle) : l'afficher dans l'état des lieux sous la forme `INVEST ✖ (<lettre>) : décidé par le BA`, sans le compter comme ouvert ni le redemander. Le présenter au BA ; s'il n'y a rien, le dire et passer à l'étape 6.
5. Traiter les points un par un, du plus bloquant au moins : poser UNE question, attendre. Un besoin jamais élicité : proposer `ba-elicitation` (le BA décide et confirme avant tout enchaînement). Une réponse du BA est reformulée, puis confirmée (`[validée <date>]`) ; proposer de l'enregistrer comme source via `ba-source-intake`, avec son accord. Chaque constat de `open-questions.md` repris à l'étape 4 est demandé au BA, un à la fois ; sa réponse peut modifier la ligne d'US concernée, y compris une ligne déjà confirmée : la réponse explicite du BA fait autorité, et la ligne modifiée redevient `[hypothèse]` jusqu'à sa confirmation (étape 14). Ce skill ne modifie jamais `open-questions.md`. Un point sans réponse reste dans `## Questions ouvertes` ; aucune exigence ni scénario n'est rédigé dessus. Si le BA demande d'avancer malgré des hypothèses, le contenu reste `[hypothèse]` et le DoR reste décoché.
6. Cas d'utilisation visé : lister les cas existants du domaine, demander « Quel cas d'utilisation cette US modifie-t-elle, ou faut-il en créer un ? ». Nouveau : numéro = plus grand `nn` du domaine plus 1 (`01` si aucun), `<DOMAINE>` = nom du dossier en majuscules ; `ba-sync` revérifie ce numéro à la création ; le BA confirme cas et domaine, rien n'est inventé. US multi-domaine : un cas par domaine, demandé séparément.
7. Langue des scénarios : la lire dans `context.md` ou la documentation du projet ; sinon utiliser `fr` avec `# language: fr` et le dire au BA, qui peut corriger.
8. Proposer le récit et la ligne `- Domaine(s) :` (copiés du backlog, récit avec son marqueur).
9. Proposer les exigences, une par ligne, en forme normalisée et selon les règles de `## Rédaction des exigences` : une affirmation reprise d'une source (`[source SRC-nnn « passage »]`), d'une règle de `rules.md` (marqueur d'origine) ou d'une déduction (`[hypothèse]`). Une règle de gestion absente des sources est une question au BA (étape 5), jamais une exigence. US multi-domaine : terminer chaque ligne par `(domaine : <d>)`.
10. Proposer les scénarios : au moins un nominal et, pour chaque exigence qui peut échouer, un scénario d'erreur ou de limite ; chacun porte son `# Provenance :` et le tag du cas visé. Aucune valeur ni règle qui ne soit dans les documents lus ; sinon question.
11. Exigences non fonctionnelles : d'abord demander « Parmi les huit caractéristiques ISO/IEC 25010 (adéquation fonctionnelle, efficacité de performance, compatibilité, utilisabilité, fiabilité, sécurité, maintenabilité, portabilité), lesquelles concernent cette US, ou aucune ? » et attendre. Puis, une caractéristique retenue à la fois : demander « Quelle cible pour <caractéristique> (avec nombre et unité), ou aucune ? » ; une cible donnée devient une ligne (reformulée, `[hypothèse]` jusqu'à l'étape 14), « aucune » devient `: aucun`, une cible citée dans une source garde son marqueur de source. Sans réponse : pas de ligne, une question dans `## Questions ouvertes`, aucune cible inventée.
12. Dépendances, seulement si le DoR applicable (celui du projet, sinon la liste par défaut) contient un critère de dépendances : demander « De quelles autres US ou systèmes cette US dépend-elle, ou aucune ? » ; la réponse devient la ligne `- Dépendances :` de `## Exigences`. Sinon, sauter cette étape : aucune question, aucune ligne `- Dépendances :`.
13. Préparer le DoR : tous les critères décochés.
14. Confirmation : présenter récit, exigences, scénarios, NFR, dépendances, section par section, et demander « Confirmez-vous, ou corrigez-vous, section par section ? ». Après un « oui » explicite (y compris pour la ligne `- Dépendances :`), mettre `[validée <date du jour>]` sur les lignes confirmées ; une ligne corrigée redevient `[hypothèse]`.
15. Vérifier chaque critère du DoR retenu contre le contenu réel (hypothèse restante, scénario d'erreur présent, source citée, etc.). Cocher `[x]` avec `(vérifié <date>)` seulement ce qui est vérifié ; « dépendances connues » n'est coché que si la ligne `- Dépendances :` est `[validée]` (une réponse « aucune » validée compte) ; le critère « accord du BA » exige son accord explicite que l'US est prête, à demander (« Jugez-vous cette US prête ? »). Le reste est décoché et inscrit dans `Écart au DoR`.
16. Mettre à jour l'état des lieux (comptes de l'étape 4 recalculés).
17. Écrire `work/<epic>/US-nnn.md` au format de `## Entrées / Sorties`. Relire : sections et ordre, ligne `- Domaine(s) :` identique à celle du backlog, `(domaine : <d>)` sur chaque exigence d'une US multi-domaine et seulement alors, ligne `- Dépendances :` présente si et seulement si l'étape 12 a eu lieu, chaque exigence conforme à l'une des deux formes (un seul verbe, « doit » ou « devrait », rôle présent dans `actors.md` ou une source), une ligne par caractéristique retenue, tags `@UC-…` identiques à `Cas d'utilisation visé` (comparés domaine par domaine pour une US multi-domaine), marqueur sur chaque ligne d'affirmation, aucune case cochée sans vérification.
18. Annoncer : chemin écrit, cas visé, nombre de scénarios, critères DoR cochés et non cochés, questions ouvertes et lignes `[hypothèse]` restantes. Recommander `ba-quality-review` pour contrôler l'US ; `ba-sync` sera utilisé par l'agent du développeur à l'implémentation. Le BA décide et confirme avant tout enchaînement.

## Exemple : information manquante

Le BA dit : « Spécifie l'US-004 (annuler une réservation) ». Ni la ligne Sources ni `rules.md` ne disent dans quel délai une annulation reste possible.

Le skill ne rédige aucun scénario sur le délai. Il demande : « Dans quel délai le lecteur peut-il annuler sa réservation ? ». Sans réponse, `## Questions ouvertes` contient `- Quel est le délai d'annulation d'une réservation ? (contrôle de l'IA)`, l'état des lieux compte un point ouvert, et le critère « aucune hypothèse » du DoR reste décoché.
