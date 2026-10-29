# Scénarios d'évaluation : ba-entrypoint

### Scénario 1 : Affiner une US qui contient des hypothèses

**État du dépôt** : `docs/requirements/work/prets/US-012.md` existe et contient deux lignes `[hypothèse]` ainsi qu'un point ouvert.

**Message du BA** : « Affinons l'US 12 »

**Attendu** : l'identifiant est lu `US-012` et la forme lue est annoncée ; l'US est retrouvée et lue ; le skill propose `ba-elicitation` en citant les hypothèses et le point ouvert comme motif, puis attend la confirmation du BA.

**Interdit** : proposer directement `ba-story-spec` ; supposer l'US prête ; créer ou modifier un fichier.

### Scénario 2 : Documents déposés

**État du dépôt** : `docs/requirements/inbox/` contient deux documents non encore indexés.

**Message du BA** : « J'ai déposé des documents, travaillons dessus »

**Attendu** : le skill constate les documents dans `inbox/`, propose `ba-source-intake` puis `ba-elicitation`, avec le motif, et attend la confirmation.

**Interdit** : proposer `ba-story-spec` ou `ba-backlog` ; déplacer ou indexer lui-même les documents.

### Scénario 3 : Besoin absent de la documentation

**État du dépôt** : `docs/requirements/` existe ; ni `index.md`, ni `domains/`, ni `sources/` ne mentionnent « export des prêts en retard ».

**Message du BA** : « Affinons le besoin "export des prêts en retard" en US »

**Attendu** : le skill cherche le besoin, constate qu'il est absent et le dit ; il propose d'enregistrer la description du BA comme déclaration (`SRC-nnn`, avec date et texte) via `ba-source-intake`, puis `ba-elicitation`, `ba-backlog`, `ba-story-spec`.

**Interdit** : inventer le contenu ou le périmètre du besoin ; passer directement à la rédaction d'US ; écrire un fichier lui-même.

### Scénario 4 : Information manquante

**État du dépôt** : `docs/requirements/work/prets/` contient `US-012.md` et `US-014.md` ; aucune `US-099`.

**Message du BA** : « Affinons l'US 99 »

**Attendu** : l'identifiant est normalisé en `US-099` et la normalisation est annoncée (« je lis US-099 ») ; le skill dit que `US-099` n'existe pas dans `work/`, peut citer les US présentes, et demande au BA laquelle il vise ou si l'identifiant est erroné.

**Interdit** : choisir une autre US (par exemple `US-012` ou `US-014`) ; supposer une faute de frappe et corriger de lui-même ; créer `US-099`.

### Scénario 5 : Identifiant non normalisable

**État du dépôt** : `docs/requirements/work/prets/` contient `US-012.md` et `US-123.md`.

**Message du BA** : « Affinons l'US 12a »

**Attendu** : l'identifiant n'a pas la forme `US` + 1 à 3 chiffres ; le skill ne normalise rien, signale un identifiant mal saisi et demande au BA laquelle il vise.

**Interdit** : lire `12a` comme `US-012` ; choisir `US-012` ou `US-123` ; créer un fichier.

### Scénario 6 : Simple mention dans une autre US

**État du dépôt** : `docs/requirements/work/prets/US-014.md` écrit « dépend de US-012 » ; aucun fichier ni titre `US-012`.

**Message du BA** : « Affinons l'US 12 »

**Attendu** : `US-012` est annoncée comme lecture de l'identifiant ; la mention dans `US-014` ne compte pas ; le skill dit que `US-012` est introuvable et demande au BA.

**Interdit** : traiter `US-014` comme la cible ; inventer `US-012` à partir de la mention.

### Scénario 7 : US spécifiée sans point ouvert, pas de boucle vers l'élicitation

**État du dépôt** : `work/prets/backlog.md` contient `### US-015` avec `- Récit : … [validée 2026-10-20]`, `- Rattachement : epic : Prêts ; feature : —`, `- Sources : — (aucune)`, `- Priorité : Must | critère : valeur [hypothèse]` ; aucun `US-015.md` ; aucun constat pour `US-015` dans `open-questions.md`.

**Message du BA** : « Affinons l'US 15 »

**Attendu** : `US-015` lue ; seul le bloc du backlog existe et n'a aucun point ouvert (Rattachement, Sources et Priorité ne comptent pas) : le skill propose `ba-story-spec` ; contre-cas : si `US-015.md` existe avec une question ouverte, `ba-elicitation` est proposé en la citant.

**Interdit** : proposer `ba-elicitation` à cause de `Sources : — (aucune)` ou de la priorité `[hypothèse]` ; ignorer `US-015.md` quand il existe.

### Scénario 8 : « Par où commencer »

**État du dépôt** : documentation initialisée, `work/` vide.

**Message du BA** : « Par où commencer ? »

**Attendu** : le skill demande « Avez-vous des documents à déposer, un besoin à décrire ou une US à affiner ? », attend, puis route selon la réponse ; aucun fichier créé.

**Interdit** : proposer un skill sans poser la question ; choisir une US.

### Scénario 9 : US spécifiée complète, sections non assertives

**État du dépôt** : `work/prets/US-016.md` existe : `## Récit` et `- Domaine(s) : pret [validée 2026-10-25]` marqués, exigences et exigences non fonctionnelles marquées `[validée …]`, `## État des lieux`, `## DoR`, `Cas d'utilisation visé : UC-PRET-01 (existant)` et un bloc Gherkin sans marqueur ; `open-questions.md` contient un constat `(vu dans US-016, Exigences)` dont le passage cité n'apparaît plus dans `US-016.md`.

**Message du BA** : « Affinons l'US 16 »

**Attendu** : aucune route vers `ba-elicitation` (sections non assertives ignorées, constat résolu non compté) ; le skill propose `ba-quality-review` (ou `ba-story-spec` s'il reste des points) ; contre-cas : une exigence sans marqueur, ou un constat dont le passage est encore présent, mène à `ba-elicitation`.

**Interdit** : proposer `ba-elicitation` parce que le bloc Gherkin, le DoR ou l'état des lieux n'ont pas de marqueur ; compter le constat résolu ; supprimer sa ligne.
