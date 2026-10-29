# Scénarios d'évaluation : ba-backlog

### Scénario 1 : Création d'un backlog

**État du dépôt** : `work/reservation/framing.md` avec `- GOAL-01 : permettre la réservation d'un livre emprunté [validée 2026-10-20]` ; `work/reservation/elicitation.md` avec `- [fonctions] le lecteur réserve un livre emprunté [source SRC-001 « réserver un livre emprunté »]` ; `shared/actors.md` contient le rôle `lecteur` ; `domains/reservation/` existe ; aucun `US-nnn` dans `work/`.

**Message du BA** : « Construis le backlog de la réservation. »

**Attendu** : le skill propose une carte (activités dans l'ordre), un Epic citant `GOAL-01`, puis `US-001` en récit `En tant que lecteur, je veux … afin de …`, domaine `reservation`, source copiée avec son marqueur, une classe MoSCoW avec un critère nommé en une ligne, le tout `[hypothèse]` ; il demande la confirmation, n'écrit rien avant, puis écrit `work/reservation/backlog.md` (titre `### US-001 : <titre>`) et met à jour `## Travaux en cours` de `index.md` ; il annonce `ba-story-spec` et `ba-quality-review` comme suites possibles, le BA confirmant avant tout enchaînement.

**Interdit** : écrire des scénarios Gherkin ou des exigences détaillées ; marquer une priorité `[validée …]` avant la réponse du BA ; inventer un rôle absent de `actors.md` et des sources ; écrire le fichier avant la confirmation ; imposer le niveau Feature sur un petit Epic.

### Scénario 2 : Ajout d'une US quand `US-007` existe dans un autre Epic

**État du dépôt** : `work/retards/backlog.md` contient `### US-007 : Notifier le lecteur d'un retard` ; `work/reservation/backlog.md` contient `US-001` à `US-003` ; `GOAL-01` existe dans `framing.md` de la réservation.

**Message du BA** : « Ajoute une US de réservation : annuler une réservation, pour GOAL-01. »

**Attendu** : la nouvelle US reçoit `US-008` (plus grand numéro de tout `work/`, plus 1) ; les US existantes ne sont ni réécrites ni renumérotées ; elle est ajoutée après confirmation, au format `### US-008 : <titre>` avec ses sept lignes, et le comptage de `index.md` est mis à jour.

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

### Scénario 7 : US trop grande (Petite non réussie)

**État du dépôt** : `elicitation.md` contient un besoin « le lecteur réserve, modifie, annule et consulte l'historique de ses réservations » ; l'US rédigée reprend ces quatre actions dans un seul récit.

**Message du BA** : « Construis le backlog de la réservation. »

**Attendu** : au contrôle INVEST, la lettre S n'est pas réussie (plusieurs actions distinctes) ; le skill pose UNE question proposant de découper en quatre US et attend ; la ligne est notée `- INVEST : ✔ ✔ ✔ ✔ ? ✔` tant que le BA n'a pas répondu ; le BA décide (découpe ou conservation, la lettre devient `✔` ou `✖`).

**Interdit** : découper l'US sans réponse du BA ; réécrire le récit à sa place ; écrire `✔` sur S sans réponse ; poser plusieurs questions à la fois.

### Scénario 8 : US qui énonce une solution (Négociable non réussie)

**État du dépôt** : une US proposée dit « je veux cliquer sur le bouton rouge qui insère une ligne dans la table réservation afin de réserver un livre ».

**Message du BA** : « Ajoute cette US au backlog. »

**Attendu** : la lettre N n'est pas réussie ; le skill cite les mots en cause (bouton, table) et demande au BA s'il veut reformuler le besoin ; les autres lettres sont évaluées ; la ligne `- INVEST :` porte `?` en position N jusqu'à la réponse.

**Interdit** : reformuler le récit sans le BA ; ignorer le défaut ; refuser d'ajouter l'US ; marquer `✖` avant d'avoir posé la question.

### Scénario 9 : Contre-cas, US conforme à INVEST

**État du dépôt** : une US proposée « En tant que lecteur, je veux réserver un livre emprunté afin d'être prévenu de son retour » ; aucune dépendance ; une seule action ; résultat observable nommable.

**Message du BA** : « Ajoute cette US. »

**Attendu** : les six tests réussissent, le skill l'affiche (`US-nnn : ✔ ✔ ✔ ✔ ✔ ✔`), ne pose aucune question INVEST et écrit `- INVEST : ✔ ✔ ✔ ✔ ✔ ✔` après confirmation.

**Interdit** : inventer une dépendance ou un défaut pour avoir une question à poser.

### Scénario 10 : US existante dont la ligne INVEST contient `?`

**État du dépôt** : `work/reservation/backlog.md` contient `US-002` avec `- INVEST : ✔ ✔ ✔ ? ✔ ✔` et `US-003` avec `- INVEST : ✔ ✔ ✔ ✔ ✔ ✔` ; le BA ne demande aucune nouvelle US.

**Message du BA** : « Reprends le backlog de la réservation, il reste une question INVEST. »

**Attendu** : seule `US-002` est reprise par le contrôle INVEST ; le skill pose UNE question sur E (l'inconnu qui bloque l'estimation) ; après la réponse, il met à jour uniquement la ligne `- INVEST :` de `US-002` (`✔` ou `✖` selon le BA) ; `US-003` et le reste du fichier ne changent pas.

**Interdit** : retester `US-003` ; réécrire le récit de `US-002` ; modifier une autre ligne ; laisser `?` après une réponse du BA.

### Scénario 11 : numérotation après nettoyage de `work/`

**État du dépôt** : `work/` ne contient que `work/retards/backlog.md` (`US-001`) ; `domains/reservation/requirements.md` contient une ligne `(origine : US-014, 2026-09-30)`.

**Message du BA** : « Ajoute une US au backlog des retards. »

**Attendu** : la nouvelle US reçoit `US-015` (le plus grand numéro trouvé dans `work/` et dans les origines de `requirements.md`, plus 1).

**Interdit** : attribuer `US-002` ; réutiliser `US-014`.

### Scénario 12 : nom de domaine donné sous une autre forme

**État du dépôt** : `domains/` contient `reservation/` ; aucune autre contrainte.

**Message du BA** : « Ajoute une US pour la Réservation des salles, domaine « Réservation ». »

**Attendu** : le skill propose la forme du dossier `reservation` (minuscules, sans accent) et demande confirmation avant de l'écrire dans `- Domaine(s) :`.

**Interdit** : écrire `Réservation` tel quel ; changer de forme sans demander.
