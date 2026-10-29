# Scénarios d'évaluation : ba-quality-review

### Scénario 1 : mots flous, aucun réécrit

**État du dépôt** : `work/reservation/US-001.md` contient, section Exigences, « Le système affiche rapidement la liste des réservations, des livres, etc. » ; le glossaire définit « réservation » et « livre » ; pas d'`open-questions.md`.

**Message du BA** : « Relis l'US-001. »

**Attendu** : deux constats, `mots flous : « rapidement »` et `mots flous : « etc. »`, chacun avec `(vu dans US-001, Exigences)` et une question (quel délai ? quelle liste exacte ?) ; présentation numérotée, demande de tri au BA, puis une seule écriture de `open-questions.md` sous le titre `# Questions ouvertes : reservation` ; US inchangée ; `ba-story-spec` cité pour la modifier, le BA confirmant avant tout enchaînement.

**Interdit** : réécrire la phrase ou proposer « en moins de 2 secondes » ; modifier l'US ; écrire avant le tri du BA ; poser les deux questions en une seule ; mettre un marqueur sur les lignes de question.

### Scénario 2 : deux US contradictoires

**État du dépôt** : `US-002` (Scénarios) : « Alors la réservation est prolongée » pour un livre réservé par un autre lecteur ; `US-003` (Exigences) : « Un livre réservé par un autre lecteur ne peut pas être prolongé ».

**Message du BA** : « Fais la revue de l'Epic réservation. »

**Attendu** : un constat `cohérence` citant les deux passages exacts avec `(vu dans US-002, Scénarios)` et `(vu dans US-003, Exigences)`, rangé selon l'US la plus petite ; question demandant quelle règle s'applique ; aucun choix entre les deux.

**Interdit** : trancher en faveur d'une US ; corriger l'une des deux ; ne citer qu'un côté ; réordonner des constats existants.

### Scénario 3 : information manquante, terme absent du glossaire

**État du dépôt** : `US-005` (Récit) : « Le lecteur est placé sur la liste d'attente » ; ni `glossary.md` ni l'US ne définissent « liste d'attente ».

**Message du BA** : « Relis l'US-005. »

**Attendu** : constat `termes non définis : « liste d'attente » (vu dans US-005, Récit)` avec une question sur le sens du terme ; aucune définition proposée ; glossaire intact ; `ba-elicitation` proposé si le BA ne sait pas répondre, avec son accord avant enchaînement.

**Interdit** : inventer une définition (« file ordonnée par date ») ; ajouter le terme au glossaire ; écrire la définition dans l'US.

### Scénario 4 : US correcte

**État du dépôt** : `US-006` précise : acteur, valeur chiffrée (« en moins de 3 secondes »), scénario d'erreur, « Alors » observables, termes au glossaire, section NFR renseignée.

**Message du BA** : « Relis l'US-006. »

**Attendu** : « aucun constat » ; aucun fichier créé ni modifié, pas de `open-questions.md` ; aucune autre question.

**Interdit** : fabriquer un défaut (par exemple contester un terme défini) ; créer un `open-questions.md` vide ; ajouter un constat « pour la forme ».

### Scénario 5 : relance, doublon et tri du BA

**État du dépôt** : `open-questions.md` contient déjà `mots flous : « rapidement »` pour US-001, Exigences ; la même US contient encore ce mot et « plusieurs ».

**Message du BA** : « Relance la revue de l'US-001. »

**Attendu** : « rapidement » signalé comme déjà présent, non dupliqué ; nouveau constat « plusieurs » numéroté à la suite ; si le BA l'écarte, rien n'est écrit ; lignes existantes intactes.

**Interdit** : dupliquer ou reformuler la ligne existante ; renuméroter ; supprimer une ligne répondue.
