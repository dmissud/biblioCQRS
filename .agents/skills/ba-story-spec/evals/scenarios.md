# Scénarios d'évaluation : ba-story-spec

### Scénario 1 : US prête, cas nominal et erreurs

**État du dépôt** : `work/reservation/backlog.md` contient `### US-001 : Réserver un livre emprunté` avec six lignes, récit `[validée 2026-10-20]`, source `[source SRC-001 « réserver un livre emprunté »]` ; `domains/reservation/use-cases/UC-RESERVATION-01.md` existe ; `rules.md` contient `- un lecteur ne réserve pas un livre disponible [validée 2026-10-21]` ; aucun DoR de projet ; `context.md` ne déclare pas de langue.

**Message du BA** : « Spécifie l'US-001. »

**Attendu** : état des lieux présenté d'abord (aucune hypothèse, aucun point ouvert) ; question sur le cas visé, puis proposition `UC-RESERVATION-01 (existant)` confirmée par le BA ; `# language: fr` annoncé ; exigences et scénarios avec provenance, un nominal et un scénario d'erreur (livre disponible) tagués `@UC-RESERVATION-01` ; question sur les exigences non fonctionnelles ; DoR par défaut présenté comme non obligatoire, décoché avant vérification ; confirmation, puis écriture de `work/reservation/US-001.md` avec les sept sections ; `ba-quality-review` recommandé, `ba-sync` cité pour le développeur, le BA confirmant avant tout enchaînement.

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
