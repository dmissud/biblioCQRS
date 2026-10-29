# Scénarios d'évaluation : ba-source-intake

### Scénario 1 : Deux documents déposés

**État du dépôt** : `docs/requirements/inbox/` contient `Cahier des charges.md` et `notes.txt` ; `sources/index.md` a `SRC-001` et `SRC-002`.

**Message du BA** : « J'ai déposé des documents, range-les »

**Attendu** : les deux fichiers sont déplacés vers `sources/SRC-003-cahier-des-charges.md` et `sources/SRC-004-notes.txt` ; `sources/index.md` reçoit deux lignes ; la section `## Sources` de `index.md` est mise à jour ; `inbox/` est vide ; le skill annonce les deux sources et propose `ba-elicitation`.

**Interdit** : copier au lieu de déplacer ; réutiliser un numéro ; modifier le contenu des documents ; toucher aux sections `## Domaines` et `## Travaux en cours`.

### Scénario 2 : Déclaration orale du BA

**État du dépôt** : `docs/requirements/` existe, `sources/index.md` a `SRC-001` à `SRC-005`.

**Message du BA** : « Le client m'a dit : "il faut relancer les lecteurs dont le prêt a plus de 30 jours de retard". Note-le. »

**Attendu** : création de `sources/SRC-006-declaration.md` avec la date, le texte verbatim exact, et une reformulation marquée `[hypothèse]` tant que le BA n'a pas confirmé ; ligne de type « déclaration du BA » dans l'index (fichier `—`) ; `[validée <date>]` seulement après confirmation explicite.

**Interdit** : écrire `[validée …]` sans confirmation ; corriger ou compléter le texte verbatim ; déplacer un fichier de `inbox/`.

### Scénario 3 : Numéro présent dans `sources/` mais absent de l'index

**État du dépôt** : `sources/index.md` s'arrête à `SRC-002` ; `sources/` contient aussi `SRC-007-ancien.pdf` ; `inbox/` contient `contrat.md`.

**Message du BA** : « Range le nouveau document »

**Attendu** : le skill signale `SRC-007-ancien.pdf` non indexé, prend le prochain numéro `SRC-008` pour `contrat.md`, et n'indexe pas `SRC-007` de lui-même.

**Interdit** : attribuer `SRC-003` ; attribuer `SRC-007` ; inventer titre ou date pour `SRC-007`.

### Scénario 4 : Information manquante, pdf illisible

**État du dépôt** : `inbox/` contient `scan.pdf` ; plus grand numéro existant `SRC-004`.

**Message du BA** : « J'ai déposé un document, qu'est-ce qu'il dit ? »

**Attendu** : `scan.pdf` est déplacé vers `sources/SRC-005-scan.pdf` et enregistré avec la mention « contenu non lu » ; le skill dit qu'il n'a pas lu le contenu, ne cite ni ne résume rien, et demande au BA de décrire l'essentiel (qui sera une déclaration).

**Interdit** : inventer ou deviner le contenu ; produire une citation `[source SRC-005 « … »]` ; laisser le fichier dans `inbox/`.

### Scénario 5 : `inbox/` vide

**État du dépôt** : `docs/requirements/inbox/` existe et est vide.

**Message du BA** : « J'ai déposé des documents »

**Attendu** : le skill dit que `inbox/` est vide, demande où sont les documents ou ce que le BA veut déclarer.

**Interdit** : créer une source ou une ligne d'index ; modifier `index.md` ; supposer des documents ailleurs.
