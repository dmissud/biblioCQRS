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
Un identifiant mal saisi est une erreur à signaler, jamais une correspondance approximative.

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
- Identifiant mal formé (`US-8`, `US-A`, `us-008`) : le signaler au BA, ne jamais le corriger ni deviner.
- Ni sources citées ni élicitation pour cette US : l'état des lieux le consigne (provenance manquante) ; ne rien déduire. Aucun cas d'utilisation dans `domains/<d>/use-cases/` : le cas visé est « à créer ».

## Entrées / Sorties

- Entrées : l'US dans `work/<epic>/backlog.md` (titre `### US-nnn : <titre>` et ses six lignes : Récit, Rattachement, Domaine(s), Buts, Sources, Priorité) ; `framing.md`, `elicitation.md`, les `sources/` citées ; `domains/<d>/use-cases/`, `rules.md`, `objects.md` (formats de `ba-as-is-reading`) ; `shared/actors.md`, `glossary.md`, `context.md` ; le DoR du projet s'il existe.
- Sortie : `<REQ_DIR>/work/<epic>/US-nnn.md`, intitulé `# US-nnn : <titre>`, avec exactement les sections, dans cet ordre : `## État des lieux`, `## Récit`, `## Exigences`, `## Scénarios`, `## Exigences non fonctionnelles`, `## DoR`, `## Questions ouvertes`.
  - État des lieux, cinq lignes : `- Date : <date>` ; `- Hypothèses : <n>` ; `- Points ouverts : <n>` ; `- Provenance manquante : <n>` ; `- Écart au DoR : <critères non satisfaits, ou —>` ; en dessous, une sous-ligne par hypothèse, point ou manque, avec sa citation.
  - Récit : la ligne `- Récit` copiée du backlog, avec son marqueur.
  - Exigences : une ligne `- <énoncé> <marqueur>` par exigence ; puis, seulement si le DoR applicable a un critère de dépendances (étape 12), la ligne `- Dépendances : <US-nnn, système ou « aucune »> <marqueur>` (`[validée <date>]` après la réponse explicite du BA et sa confirmation, sinon `[hypothèse]`).
  - Scénarios : d'abord `Cas d'utilisation visé : UC-<DOMAINE>-nn (existant | à créer)`, puis un bloc de code `gherkin` : première ligne `# language: <code>` (`fr` par défaut), `Fonctionnalité : <titre de l'US>`, puis pour chaque scénario une ligne de commentaire `# Provenance : <marqueur>`, une ligne de tag `@UC-<DOMAINE>-nn`, `Scénario : <titre>` et ses étapes. Nominal d'abord, puis erreurs et limites.
  - Exigences non fonctionnelles : une ligne `- <catégorie> : <exigence> <marqueur>`, ou `- Aucune exigence non fonctionnelle <marqueur>` quand le BA l'a dit.
  - DoR : `Référentiel : DoR du projet (<chemin>)` ou `Référentiel : liste par défaut (non obligatoire)`, puis une ligne par critère, `- [ ] <critère>` ou `- [x] <critère> (vérifié <date>)`.
  - Questions ouvertes : lignes sans marqueur, avec `(vu dans SRC-nnn)` ou `(contrôle de l'IA)`.
- Ce skill ne crée ni ne modifie aucun fichier de cas d'utilisation : il nomme le cas visé. Le fichier du cas d'utilisation et les fichiers `.feature` sont mis à jour par `ba-sync`, que l'agent du développeur utilise à l'implémentation.

## DoR par défaut (proposée, non obligatoire ; le DoR du projet, s'il existe, prime)

1. L'acteur est identifié. 2. La valeur attendue est énoncée. 3. Les sources sont citées. 4. Aucune hypothèse ne subsiste. 5. Des scénarios couvrent le cas nominal et les cas d'erreur. 6. Les besoins non fonctionnels sont traités ou déclarés « aucun ». 7. Les dépendances sont connues. 8. Le BA a donné son accord.

## Déroulement

Rien n'est écrit dans un fichier avant l'étape 17. Chaque contenu est proposé `[hypothèse]` jusqu'à la réponse du BA. Une question à la fois.
1. Vérifier `## Pré-requis absents`. Obtenir l'identifiant de l'US, la retrouver dans `backlog.md`, lire ses six lignes. Si `work/<epic>/US-nnn.md` existe, le lire : on le complète sans réécrire ses lignes confirmées.
2. Lire `framing.md`, `elicitation.md`, les `SRC-nnn` cités par la ligne Sources, les cas d'utilisation, `rules.md` et `objects.md` du ou des domaines, `actors.md`, `glossary.md`.
3. DoR : chercher un fichier dont le nom contient `dor` (sans tenir compte de la casse) ou une section « Definition of Ready » / « DoR » dans `<REQ_DIR>` et la documentation du projet. Une seule correspondance : l'utiliser. Aucune ou plusieurs, ou doute : demander « Où se trouve le DoR du projet, s'il y en a un ? » et attendre. Sans DoR du projet, proposer la liste par défaut en précisant qu'elle n'est pas obligatoire.
4. État des lieux : relever les hypothèses (lignes `[hypothèse]` de l'US et des documents qu'elle reprend), les points ouverts (questions des documents lus), les affirmations sans marqueur ni source, et l'écart au DoR. Le présenter au BA ; s'il n'y a rien, le dire et passer à l'étape 6.
5. Traiter les points un par un, du plus bloquant au moins : poser UNE question, attendre. Un besoin jamais élicité : proposer `ba-elicitation` (le BA décide et confirme avant tout enchaînement). Une réponse du BA est reformulée, puis confirmée (`[validée <date>]`) ; proposer de l'enregistrer comme source via `ba-source-intake`, avec son accord. Un point sans réponse reste dans `## Questions ouvertes` ; aucune exigence ni scénario n'est rédigé dessus. Si le BA demande d'avancer malgré des hypothèses, le contenu reste `[hypothèse]` et le DoR reste décoché.
6. Cas d'utilisation visé : lister les cas existants du domaine, demander « Quel cas d'utilisation cette US modifie-t-elle, ou faut-il en créer un ? ». Nouveau : numéro = plus grand `nn` du domaine plus 1 (`01` si aucun), `<DOMAINE>` = nom du dossier en majuscules ; le BA confirme cas et domaine, rien n'est inventé. US multi-domaine : un cas par domaine, demandé séparément.
7. Langue des scénarios : la lire dans `context.md` ou la documentation du projet ; sinon utiliser `fr` avec `# language: fr` et le dire au BA, qui peut corriger.
8. Proposer le récit (copié du backlog avec son marqueur).
9. Proposer les exigences : une affirmation par ligne, reprise d'une source (`[source SRC-nnn « passage »]`), d'une règle de `rules.md` (marqueur d'origine) ou d'une déduction (`[hypothèse]`). Une règle de gestion absente des sources est une question au BA (étape 5), jamais une exigence.
10. Proposer les scénarios : au moins un nominal et, pour chaque exigence qui peut échouer, un scénario d'erreur ou de limite ; chacun porte son `# Provenance :` et le tag du cas visé. Aucune valeur ni règle qui ne soit dans les documents lus ; sinon question.
11. Exigences non fonctionnelles : demander « Quelles exigences non fonctionnelles s'appliquent (performance, sécurité, accessibilité, traçabilité…), ou aucune ? » ; ne proposer que celles qui sont citées dans les sources.
12. Dépendances, seulement si le DoR applicable (celui du projet, sinon la liste par défaut) contient un critère de dépendances : demander « De quelles autres US ou systèmes cette US dépend-elle, ou aucune ? » ; la réponse devient la ligne `- Dépendances :` de `## Exigences`. Sinon, sauter cette étape : aucune question, aucune ligne `- Dépendances :`.
13. Préparer le DoR : tous les critères décochés.
14. Confirmation : présenter récit, exigences, scénarios, NFR, dépendances, section par section, et demander « Confirmez-vous, ou corrigez-vous, section par section ? ». Après un « oui » explicite (y compris pour la ligne `- Dépendances :`), mettre `[validée <date du jour>]` sur les lignes confirmées ; une ligne corrigée redevient `[hypothèse]`.
15. Vérifier chaque critère du DoR retenu contre le contenu réel (hypothèse restante, scénario d'erreur présent, source citée, etc.). Cocher `[x]` avec `(vérifié <date>)` seulement ce qui est vérifié ; « dépendances connues » n'est coché que si la ligne `- Dépendances :` est `[validée]` (une réponse « aucune » validée compte) ; le critère « accord du BA » exige son accord explicite que l'US est prête, à demander (« Jugez-vous cette US prête ? »). Le reste est décoché et inscrit dans `Écart au DoR`.
16. Mettre à jour l'état des lieux (comptes de l'étape 4 recalculés).
17. Écrire `work/<epic>/US-nnn.md` au format de `## Entrées / Sorties`. Relire : sections et ordre, ligne `- Dépendances :` présente si et seulement si l'étape 12 a eu lieu, tags `@UC-…` identiques à `Cas d'utilisation visé`, marqueur sur chaque ligne d'affirmation, aucune case cochée sans vérification.
18. Annoncer : chemin écrit, cas visé, nombre de scénarios, critères DoR cochés et non cochés, questions ouvertes et lignes `[hypothèse]` restantes. Recommander `ba-quality-review` pour contrôler l'US ; `ba-sync` sera utilisé par l'agent du développeur à l'implémentation. Le BA décide et confirme avant tout enchaînement.

## Exemple : information manquante

Le BA dit : « Spécifie l'US-004 (annuler une réservation) ». Ni la ligne Sources ni `rules.md` ne disent dans quel délai une annulation reste possible.

Le skill ne rédige aucun scénario sur le délai. Il demande : « Dans quel délai le lecteur peut-il annuler sa réservation ? ». Sans réponse, `## Questions ouvertes` contient `- Quel est le délai d'annulation d'une réservation ? (contrôle de l'IA)`, l'état des lieux compte un point ouvert, et le critère « aucune hypothèse » du DoR reste décoché.
