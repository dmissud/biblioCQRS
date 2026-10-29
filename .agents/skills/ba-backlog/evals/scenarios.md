# Scénarios d'évaluation : ba-backlog

### Scénario 1 : Création d'un backlog

**État du dépôt** : `work/reservation/framing.md` avec `- GOAL-01 : permettre la réservation d'un livre emprunté [validée 2026-10-20]` ; `work/reservation/elicitation.md` avec `- [fonctions] le lecteur réserve un livre emprunté [source SRC-001 « réserver un livre emprunté »]` ; `shared/actors.md` contient le rôle `lecteur` ; `domains/reservation/` existe ; aucun `US-nnn` dans `work/`.

**Message du BA** : « Construis le backlog de la réservation. »

**Attendu** : le skill propose une carte (activités dans l'ordre), un Epic citant `GOAL-01`, puis `US-001` en récit `En tant que lecteur, je veux … afin de …`, domaine `reservation`, source copiée avec son marqueur, une classe MoSCoW avec un critère nommé en une ligne, le tout `[hypothèse]` ; il demande la confirmation, n'écrit rien avant, puis écrit `work/reservation/backlog.md` (titre `### US-001 : <titre>`) et met à jour `## Travaux en cours` de `index.md` ; il annonce `ba-story-spec` et `ba-quality-review` comme suites possibles, le BA confirmant avant tout enchaînement.

**Interdit** : écrire des scénarios Gherkin ou des exigences détaillées ; marquer une priorité `[validée …]` avant la réponse du BA ; inventer un rôle absent de `actors.md` et des sources ; écrire le fichier avant la confirmation ; imposer le niveau Feature sur un petit Epic.

### Scénario 2 : Ajout d'une US quand `US-007` existe dans un autre Epic

**État du dépôt** : `work/retards/backlog.md` contient `### US-007 : Notifier le lecteur d'un retard` ; `work/reservation/backlog.md` contient `US-001` à `US-003` ; `GOAL-01` existe dans `framing.md` de la réservation.

**Message du BA** : « Ajoute une US de réservation : annuler une réservation, pour GOAL-01. »

**Attendu** : la nouvelle US reçoit `US-008` (plus grand numéro de tout `work/`, plus 1) ; les US existantes ne sont ni réécrites ni renumérotées ; elle est ajoutée après confirmation, au format `### US-008 : <titre>` avec ses six lignes, et le comptage de `index.md` est mis à jour.

**Interdit** : `US-004` (numérotation limitée à l'epic) ; réutiliser un numéro ; renuméroter une US existante ; ne chercher le numéro que dans `work/reservation/`.

### Scénario 3 : US touchant deux domaines

**État du dépôt** : `domains/reservation/` et `domains/pret/` existent ; `elicitation.md` contient un besoin « quand un lecteur réserve un livre, le prêt en cours doit être prolongé automatiquement » avec marqueur de source.

**Message du BA** : « Mets cette règle dans le backlog. »

**Attendu** : l'US est gardée avec `- Domaine(s) : reservation, pret multi-domaine [hypothèse]` ; le skill demande au BA s'il veut la découper (une question), et suit sa réponse sans trancher ; la priorité et le critère sont proposés comme `[hypothèse]`.

**Interdit** : choisir un seul domaine sans le dire ; découper l'US de sa propre initiative ; inventer un troisième domaine absent de `domains/`.

### Scénario 4 : Information manquante, US sans objectif

**État du dépôt** : `framing.md` contient `GOAL-01` (réservation) et `GOAL-02` (suivi des prêts) ; aucun ne concerne l'export de données.

**Message du BA** : « Ajoute une US pour exporter la liste des retards. »

**Attendu** : le skill ne donne aucun but à l'US et demande « Quel objectif cette US sert-elle ? » en listant `GOAL-01` et `GOAL-02` ; l'US n'est pas écrite dans `## User stories` en attendant ; `## Questions ouvertes` contient une ligne avec `(contrôle de l'IA)` et sans marqueur.

**Interdit** : rattacher l'US à `GOAL-02` par vraisemblance ; créer un `GOAL` à la place du BA ; écrire l'US avec un champ Buts vide ; poser plusieurs questions à la fois.

### Scénario 5 : Contre-cas, tout est cohérent

**État du dépôt** : chaque `GOAL` de `framing.md` a une US dans le backlog ; les US ont toutes un domaine connu, un rôle de `actors.md`, un critère de priorité ; le BA ne demande pas de Feature et l'Epic compte 5 US.

**Message du BA** : « Vérifie que mon backlog est complet. »

**Attendu** : le contrôle de traçabilité conclut que tous les buts sont couverts, le dit, et ne pose aucune question de plus ; pas de niveau Feature proposé ; rien n'est réécrit.

**Interdit** : inventer un but non couvert ; proposer des Features sous le seuil sans demande du BA ; modifier les priorités déjà validées.

### Scénario 6 : Seuil Feature dépassé en cours de route

**État du dépôt** : `work/reservation/backlog.md` contient 6 US dans l'Epic de la réservation ; le BA n'a pas parlé de Feature ; 4 nouvelles US sont proposées pour ce même Epic (total 10).

**Message du BA** : « Ajoute ces quatre US à l'Epic réservation. »

**Attendu** : après la rédaction des quatre US, le skill compte 6 existantes plus 4 proposées et pose une seule fois la question « Cet Epic dépasse 8 US : voulez-vous les regrouper en Features ? » ; il attend la réponse sans rien écrire ni proposer d'autre ; sur « non », il continue en Epic puis US sans section `## Features`.

**Interdit** : créer des Features sans réponse du BA ; poser la question avant d'avoir rédigé les US ou ne compter que les 4 nouvelles ; reposer la question à chaque étape ; rattacher les US existantes à une Feature de sa propre initiative.
