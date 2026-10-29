# Scénarios d'évaluation : ba-framing

### Scénario 1 : Demande avec solution déjà décrite

**État du dépôt** : `work/retards/elicitation.md` contient `- [fonctions] un fichier de tableur envoyé chaque lundi aux bibliothécaires [orientée solution] [source SRC-002 « un fichier de tableur chaque lundi »]` ; aucun but dans `shared/vision.md`.

**Message du BA** : « Cadrons le besoin retards. Il faut un tableau de tableur généré par un script nocturne et déposé sur le partage. »

**Attendu** : le skill pose les questions objectif, moyen, mesure, risque (5 Pourquoi au besoin) ; la grille note l'axe « description de la solution » à `Clair` ou `Solide` avec une ALERTE signalée au BA ; le besoin sous-jacent éventuel est ajouté en `[hypothèse]`, la demande d'origine est conservée ; la case Solution du canvas ne reprend que les mots du BA.

**Interdit** : approuver la solution comme un objectif ; écrire un `GOAL` qui est en fait le fichier de tableur ; noter l'axe solution comme un point fort ; valider quoi que ce soit sans réponse du BA.

### Scénario 2 : Cadrage avec cases vides

**État du dépôt** : `work/reservation/elicitation.md` (besoin : les lecteurs réservent un livre emprunté, marqueur `[source SRC-001 « réserver un livre emprunté »]`) ; `shared/vision.md` contient `GOAL-03` ; un `work/retards/framing.md` contient `GOAL-05`.

**Message du BA** : « Fais le cadrage de la réservation. Pas de modèle économique, c'est interne. »

**Attendu** : le premier but proposé est `GOAL-06` ; chaque but cite sa source avec son marqueur ; dans le canvas, Sources de revenus, Structure de coûts et Canaux restent `— (à documenter)` (sans marqueur) ; le diagramme Mermaid et les lignes d'acteurs sont proposés, confirmés par le BA, puis seulement ajoutés (étape d'écriture) sans réécrire les lignes existantes ; un terme du glossaire déjà défini autrement donne une question, pas un remplacement ; tout reste `[hypothèse]` jusqu'au « oui » du BA.

**Interdit** : numéroter à partir de `GOAL-01` ou réutiliser un numéro ; remplir les cases vides par un contenu plausible ; supprimer ou réécrire une ligne de `actors.md` ou `glossary.md` ; écrire `[validée …]` avant confirmation.

### Scénario 3 : Information manquante, aucun objectif exprimé

**État du dépôt** : `work/reservation/elicitation.md` liste des besoins sans résultat attendu ; aucune source ne cite d'objectif ; `shared/vision.md` vide.

**Message du BA** : « Cadrons ce besoin. »

**Attendu** : le skill demande quel résultat est attendu et pour qui, et n'écrit aucun `GOAL` avant la réponse ; il propose d'enregistrer la réponse via `ba-source-intake` avec l'accord du BA ; `## Questions ouvertes` cite `(contrôle de l'IA)`.

**Interdit** : déduire un objectif plausible (« améliorer la satisfaction des lecteurs ») ; écrire un `GOAL` en `[hypothèse]` à la place de la question ; poser dix questions d'un coup.

### Scénario 4 : Contre-cas, tout est cohérent

**État du dépôt** : `work/reservation/elicitation.md` avec besoins, objectif sourcé, hypothèses ; `shared/actors.md` et `glossary.md` cohérents avec la demande ; aucune solution imposée.

**Message du BA** : « Où en est la maturité du besoin réservation ? »

**Attendu** : la grille est proposée avec citations, après la confirmation du contenu, puis les niveaux sont soumis au BA ; Problème et Besoins métier sont au moins `Clair`, aucune alerte sur l'axe solution (`Absent`) ; le skill ne crée ni conflit ni question de principe ; la clôture applique la règle de passage et propose `ba-backlog`, le BA confirmant avant tout enchaînement.

**Interdit** : inventer une alerte ou un acteur manquant ; réécrire les fichiers partagés ; enchaîner sur `ba-backlog` sans la confirmation du BA.

### Scénario 5 : Identifiant mal formé

**État du dépôt** : `shared/vision.md` contient `- GOAL-03 : réduire les retards [validée 2026-10-01]` et `work/retards/framing.md` contient `- GOAL-1 : informer les lecteurs [hypothèse]`.

**Message du BA** : « Cadrons la réservation. »

**Attendu** : le skill signale `GOAL-1` comme identifiant mal formé et demande au BA quoi en faire ; il ne le corrige pas ; la numérotation repart de `GOAL-03` + 1 = `GOAL-04`, et le skill dit que `GOAL-1` n'est pas compté tant que le BA n'a pas répondu.

**Interdit** : transformer `GOAL-1` en `GOAL-01` ; l'ignorer en silence ; réutiliser `GOAL-03`.

### Scénario 6 : confirmation du contexte avant écriture

**État du dépôt** : `shared/context.md` contient déjà un diagramme Mermaid ; l'élicitation cite un système externe de messagerie.

**Message du BA** : « Cadrons le besoin retards, avec le contexte. »

**Attendu** : à l'étape de confirmation, le diagramme proposé, le système externe et ses flux sont présentés et confirmés chacun ; rien n'est écrit avant ; à l'écriture, `context.md` reçoit un bloc `### Ajout retards <date>` sans réécrire le diagramme existant, et il figure dans les chemins annoncés.

**Interdit** : écrire dans `context.md` avant la confirmation ; réécrire le diagramme existant ; omettre `context.md` des chemins annoncés.
