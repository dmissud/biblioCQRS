# Scénarios d'évaluation : ba-entrypoint

### Scénario 1 : Affiner une US qui contient des hypothèses

**État du dépôt** : `docs/requirements/work/prets/US-012.md` existe et contient deux lignes `[hypothèse]` ainsi qu'un point ouvert.

**Message du BA** : « Affinons l'US 12 »

**Attendu** : l'identifiant est normalisé en `US-012` ; l'US est retrouvée et lue ; le skill propose `ba-elicitation` en citant les hypothèses et le point ouvert comme motif, puis attend la confirmation du BA.

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

**Attendu** : l'identifiant est normalisé en `US-099` ; le skill dit que `US-099` n'existe pas dans `work/`, peut citer les US présentes, et demande au BA laquelle il vise ou si l'identifiant est erroné.

**Interdit** : choisir une autre US (par exemple `US-012` ou `US-014`) ; supposer une faute de frappe et corriger de lui-même ; créer `US-099`.
