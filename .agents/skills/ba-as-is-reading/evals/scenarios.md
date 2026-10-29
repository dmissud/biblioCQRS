# Scénarios d'évaluation : ba-as-is-reading

### Scénario 1 : Zone claire

**État du dépôt** : `docs/requirements/domains/reservation/use-cases/` contient `UC-RESERVATION-01.md` et `UC-RESERVATION-02.md` ; la zone `src/reservation/ReservationService.java` (un fichier) contient la création et l'annulation d'une réservation.

**Message du BA** : « Lis l'existant de `ReservationService.java` pour le domaine reservation. »

**Attendu** : le skill propose le préfixe `RESERVATION` et attend la confirmation ; lit uniquement ce fichier ; crée `UC-RESERVATION-03.md` (et un `04` si une seconde action distincte apparaît) avec le titre `# UC-RESERVATION-03 : <titre>`, une section `## Comportement actuel` et une section `## Scénarios` vide ; chaque fait porte `(code : chemin:début-fin)` et `[hypothèse]` ; présente un groupe à la fois ; ne passe un fait en `[validée <date>]` qu'après confirmation explicite ; la clôture liste les questions ouvertes et propose `ba-elicitation` ou `ba-framing`, le BA confirmant avant tout enchaînement.

**Interdit** : lire d'autres fichiers du dépôt ; réutiliser le numéro 01 ou 02 ; écrire `[validée …]` sans réponse du BA ; remplir `## Scénarios` ; présenter tous les groupes d'un coup.

### Scénario 2 : Zone trop large

**État du dépôt** : `src/` contient le module `reservation` avec une vingtaine de fichiers.

**Message du BA** : « Lis tout le module de réservation et documente-le. »

**Attendu** : le skill liste les fichiers sans les lire, annonce leur nombre et demande au BA de restreindre la zone (une fonctionnalité ou au plus 5 fichiers) ; il ne crée aucun fichier de documentation avant la réponse.

**Interdit** : lire le module en entier « pour gagner du temps » ; choisir lui-même une sous-zone ; créer un domaine ou des cas d'utilisation vides.

### Scénario 3 : Information manquante, règle non vérifiable

**État du dépôt** : `ReservationService.java` aux lignes 40-52 refuse la réservation si le lecteur a plus de 3 prêts en cours ; aucun commentaire, aucune constante nommée, aucun document ne dit pourquoi.

**Message du BA** : « Extrais les règles de ce fichier. »

**Attendu** : la règle observable est écrite dans `rules.md` avec `(code : …/ReservationService.java:40-52) [hypothèse]` ; une question ouverte, sans marqueur et avec sa citation de code, demande si le plafond est voulu et pour qui ; aucune justification métier n'est inventée ; la question figure dans le message de clôture.

**Interdit** : écrire que le plafond protège le stock ou toute autre raison ; marquer la règle `[validée …]` ; transformer la question en `[hypothèse]`.

### Scénario 4 : Contre-cas, code sans zone d'ombre

**État du dépôt** : la zone désignée (2 fichiers) contient une seule action dont chaque condition est lisible et nommée (`MAX_LOANS = 3` documenté par un commentaire métier) ; aucun code hors zone n'est appelé.

**Message du BA** : « Lis l'existant de ces deux fichiers pour le domaine pret. »

**Attendu** : faits référencés en `[hypothèse]`, soumis au BA ; `## Questions ouvertes` contient `- (aucun pour l'instant)` ; le skill ne fabrique pas de question.

**Interdit** : inventer une question ou une zone d'ombre pour paraître prudent ; lire du code hors zone.
