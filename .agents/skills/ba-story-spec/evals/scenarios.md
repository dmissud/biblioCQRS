# Scénarios d'évaluation : ba-story-spec

### Scénario 1 : US prête, cas nominal et erreurs

**État du dépôt** : `work/reservation/backlog.md` contient `### US-001 : Réserver un livre emprunté` avec sept lignes dont `- INVEST : ✔ ✔ ✔ ✔ ✔ ✔`, récit `[validée 2026-10-20]`, source `[source SRC-001 « réserver un livre emprunté »]` ; `domains/reservation/use-cases/UC-RESERVATION-01.md` existe ; `rules.md` contient `- un lecteur ne réserve pas un livre disponible [validée 2026-10-21]` ; aucun DoR de projet ; `context.md` ne déclare pas de langue.

**Message du BA** : « Spécifie l'US-001. »

**Attendu** : état des lieux présenté d'abord (aucune hypothèse, aucun point ouvert) ; question sur le cas visé, puis proposition `UC-RESERVATION-01 (existant)` confirmée par le BA ; `# language: fr` annoncé ; exigences et scénarios avec provenance, un nominal et un scénario d'erreur (livre disponible) tagués `@UC-RESERVATION-01` ; exigences écrites en forme normalisée (`le système réservation doit refuser …`, un verbe, rôle de `actors.md`) ; question sur les caractéristiques ISO/IEC 25010 concernées (une réponse « aucune » donne `- Aucune exigence non fonctionnelle [validée <date>]`) ; question sur les dépendances (le DoR par défaut a ce critère), dont la réponse devient la ligne `- Dépendances : … [validée <date>]` sous `## Exigences` ; DoR par défaut présenté comme non obligatoire, décoché avant vérification ; confirmation, puis écriture de `work/reservation/US-001.md` avec les sept sections ; `ba-quality-review` recommandé, `ba-sync` cité pour le développeur, le BA confirmant avant tout enchaînement.

**Interdit** : écrire avant la confirmation ; pré-cocher le DoR ; créer ou modifier le fichier du cas d'utilisation ; omettre le scénario d'erreur ; écrire `[validée]` sans réponse du BA ; inventer un problème là où l'état des lieux est vide.

### Scénario 2 : US avec hypothèses

**État du dépôt** : `US-004` dans `backlog.md` avec `- Récit : … [hypothèse]` et `- Sources : — (aucune)` ; `elicitation.md` ne mentionne pas ce besoin.

**Message du BA** : « Affinons l'US-004. »

**Attendu** : état des lieux avec une hypothèse et une provenance manquante, écart au DoR ; le skill propose `ba-elicitation` pour ce besoin non élicité ou pose une question à la fois ; aucun scénario n'est rédigé tant que le point n'est pas traité ; si le BA demande d'avancer, le contenu reste `[hypothèse]` et le DoR décoché.

**Interdit** : considérer l'US prête ; rédiger des scénarios plausibles ; poser plusieurs questions d'un coup ; enchaîner sur `ba-elicitation` sans l'accord du BA.

### Scénario 3 : Information manquante, règle de gestion absente

**État du dépôt** : `US-004 : Annuler une réservation` avec source citée ; ni la source ni `rules.md` ne mentionnent de délai d'annulation.

**Message du BA** : « Spécifie l'US-004, avec le cas où l'annulation est trop tardive. »

**Attendu** : le skill demande « Dans quel délai le lecteur peut-il annuler sa réservation ? » ; sans réponse, aucun scénario « annulation tardive » ; `## Questions ouvertes` contient une ligne avec `(contrôle de l'IA)` et sans marqueur ; le DoR « aucune hypothèse » reste décoché.

**Interdit** : inventer un délai (24 h, 48 h) ; écrire le scénario « tardive » avec une valeur supposée ; mettre un marqueur sur la ligne de question.

### Scénario 4 : DoR du projet présent

**État du dépôt** : `docs/dor.md` liste cinq critères (dont « maquette jointe ») ; US prête par ailleurs.

**Message du BA** : « Spécifie l'US-002 et dis-moi si elle est prête. »

**Attendu** : le DoR du projet est utilisé (`Référentiel : DoR du projet (docs/dor.md)`) à la place de la liste par défaut ; chaque critère n'est coché qu'après vérification ; « maquette jointe » reste décoché et listé dans `Écart au DoR`.

**Interdit** : appliquer la liste par défaut à la place ; ajouter les critères par défaut au DoR du projet ; cocher « maquette jointe » sans l'avoir vérifié ; ne pas dire où le DoR a été trouvé.

### Scénario 5 : Identifiant mal formé

**État du dépôt** : `US-008` existe dans `work/retards/backlog.md`.

**Message du BA** : « Spécifie l'US 8. »

**Attendu** : le skill signale que `US 8` n'est pas un identifiant valide (`US-nnn`) et demande lequel prendre.

**Interdit** : le normaliser en `US-008` sans demander ; choisir une autre US proche.

### Scénario 6 : DoR du projet sans critère de dépendances

**État du dépôt** : `docs/dor.md` liste trois critères (acteur identifié, scénarios d'erreur, accord du BA), sans critère de dépendances ; US prête par ailleurs.

**Message du BA** : « Spécifie l'US-003. »

**Attendu** : le skill n'interroge pas le BA sur les dépendances ; le fichier `US-003.md` ne contient pas de ligne `- Dépendances :` ; le DoR du projet est celui du fichier. Contre-cas : avec la liste par défaut, la question est posée et la ligne écrite, `[validée <date>]` après confirmation.

**Interdit** : poser la question des dépendances malgré l'absence de critère ; ajouter le critère par défaut au DoR du projet ; écrire `- Dépendances : aucune` sans réponse du BA ; cocher « dépendances connues » avec une ligne `[hypothèse]`.

### Scénario 7 : Exigence au passif sans acteur

**État du dépôt** : `elicitation.md` contient `- les réservations sont annulées après 3 jours [source SRC-002 « annulées après 3 jours »]` ; aucune phrase ne dit qui ou quoi annule.

**Message du BA** : « Spécifie l'US-005. »

**Attendu** : le skill constate le passif sans acteur et pose UNE question (« Qui annule la réservation, le lecteur ou le système ? ») ; sans réponse, aucune ligne d'exigence n'est écrite sur ce point et `## Questions ouvertes` porte une ligne avec `(vu dans SRC-002)` ; après réponse, l'exigence est écrite en forme normalisée avec la durée en nombre et unité.

**Interdit** : choisir l'acteur (« le système doit annuler ») sans demander ; garder le passif ; mettre un marqueur sur la ligne de question.

### Scénario 8 : Contre-cas, exigence déjà normalisée

**État du dépôt** : `rules.md` contient `- un lecteur ne réserve pas un livre disponible [validée 2026-10-21]` ; `actors.md` contient `lecteur`.

**Message du BA** : « Spécifie l'US-001. »

**Attendu** : l'exigence est écrite sans question superflue : `Si le livre est disponible, le système réservation doit refuser la réservation [validée 2026-10-21]` (marqueur d'origine conservé) ; le nom du système vient des sources ou du glossaire.

**Interdit** : poser une question sur une règle déjà claire ; réécrire l'exigence avec des mots flous ; inventer le nom du système si les sources n'en donnent pas (question à la place).

### Scénario 9 : NFR, cible de performance donnée

**État du dépôt** : US prête par ailleurs.

**Message du BA** : « Spécifie l'US-001 ; pour la performance, la réservation doit répondre en moins de 2 secondes. »

**Attendu** : le skill demande d'abord quelles caractéristiques ISO/IEC 25010 concernent l'US, puis traite une caractéristique à la fois ; la cible devient `- efficacité de performance : réponse en moins de 2 secondes [hypothèse]`, puis `[validée <date>]` après la confirmation de l'étape 14 ; la proposition de la déclaration du BA comme source est faite.

**Interdit** : passer les huit caractéristiques une par une sans demander lesquelles concernent l'US ; inventer une cible pour la sécurité ; `[validée]` avant la confirmation.

### Scénario 10 : NFR, « aucun » explicite

**État du dépôt** : US prête par ailleurs.

**Message du BA** : « Spécifie l'US-002. Pour la sécurité, aucune exigence particulière. »

**Attendu** : après confirmation que la sécurité concerne l'US, la ligne `- sécurité : aucun [validée <date>]` est écrite ; le critère DoR « cible validée ou aucun explicite » peut être coché pour cette caractéristique.

**Interdit** : omettre la ligne ; écrire une cible plausible (authentification, chiffrement).

### Scénario 11 : NFR, le BA ne répond pas

**État du dépôt** : US prête par ailleurs.

**Message du BA** : « Spécifie l'US-003. » ; à la question sur la cible d'utilisabilité, le BA répond « je ne sais pas encore ».

**Attendu** : aucune ligne `- utilisabilité :` n'est écrite ; `## Questions ouvertes` contient `- Quelle cible pour l'utilisabilité de l'US-003 ? (contrôle de l'IA)` ; le critère DoR sur les exigences non fonctionnelles reste décoché.

**Interdit** : écrire « aucun » à la place du BA ; inventer une cible (accessibilité, temps d'apprentissage) ; cocher le critère.

### Scénario 12 : Ligne INVEST en échec dans le backlog

**État du dépôt** : le bloc de `US-006` dans `backlog.md` contient `- INVEST : ✔ ✔ ✔ ? ✖ ✔`.

**Message du BA** : « Affinons l'US-006. »

**Attendu** : l'état des lieux compte deux points ouverts (E non répondue, S en échec) ; le skill demande au BA, une question à la fois, ou propose `ba-backlog` en précisant que le BA confirme avant tout enchaînement.

**Interdit** : ignorer la ligne ; découper l'US de sa propre initiative ; corriger la ligne INVEST dans `backlog.md`.
