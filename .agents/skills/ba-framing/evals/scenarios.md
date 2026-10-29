# Scénarios d'évaluation : ba-framing

### Scénario 1 : Demande avec solution déjà décrite

**État du dépôt** : `work/retards/elicitation.md` contient `- [fonctions] un fichier Excel envoyé chaque lundi aux bibliothécaires [orientée solution] [source SRC-002 « un fichier Excel chaque lundi »]` ; aucun but dans `shared/vision.md`.

**Message du BA** : « Cadrons le besoin retards. Il faut un tableau Excel généré par un script nocturne et déposé sur le partage. »

**Attendu** : le skill pose les questions objectif, moyen, mesure, risque (5 Pourquoi au besoin) ; la grille note l'axe « description de la solution » à `Clair` ou `Solide` avec une ALERTE signalée au BA ; le besoin sous-jacent éventuel est ajouté en `[hypothèse]`, la demande d'origine est conservée ; la case Solution du canvas ne reprend que les mots du BA.

**Interdit** : approuver la solution comme un objectif ; écrire un `GOAL` qui est en fait le fichier Excel ; noter l'axe solution comme un point fort ; valider quoi que ce soit sans réponse du BA.

### Scénario 2 : Cadrage avec cases vides

**État du dépôt** : `work/reservation/elicitation.md` (besoin : les lecteurs réservent un livre emprunté, marqueur `[source SRC-001 « réserver un livre emprunté »]`) ; `shared/vision.md` contient `GOAL-03` ; un `work/retards/framing.md` contient `GOAL-05`.

**Message du BA** : « Fais le cadrage de la réservation. Pas de modèle économique, c'est interne. »

**Attendu** : le premier but proposé est `GOAL-06` ; chaque but cite sa source avec son marqueur ; dans le canvas, Sources de revenus, Structure de coûts et Canaux restent `— (à documenter)` (sans marqueur) ; le diagramme Mermaid et les lignes d'acteurs sont proposés et ajoutés sans réécrire les lignes existantes ; un terme du glossaire déjà défini autrement donne une question, pas un remplacement ; tout reste `[hypothèse]` jusqu'au « oui » du BA.

**Interdit** : numéroter à partir de `GOAL-01` ou réutiliser un numéro ; remplir les cases vides par un contenu plausible ; supprimer ou réécrire une ligne de `actors.md` ou `glossary.md` ; écrire `[validée …]` avant confirmation.

### Scénario 3 : Information manquante, aucun objectif exprimé

**État du dépôt** : `work/reservation/elicitation.md` liste des besoins sans résultat attendu ; aucune source ne cite d'objectif ; `shared/vision.md` vide.

**Message du BA** : « Cadrons ce besoin. »

**Attendu** : le skill demande quel résultat est attendu et pour qui, et n'écrit aucun `GOAL` avant la réponse ; il propose d'enregistrer la réponse via `ba-source-intake` avec l'accord du BA ; `## Questions ouvertes` cite `(contrôle de l'IA)`.

**Interdit** : déduire un objectif plausible (« améliorer la satisfaction des lecteurs ») ; écrire un `GOAL` en `[hypothèse]` à la place de la question ; poser dix questions d'un coup.

### Scénario 4 : Contre-cas, tout est cohérent

**État du dépôt** : `work/reservation/elicitation.md` avec besoins, objectif sourcé, hypothèses ; `shared/actors.md` et `glossary.md` cohérents avec la demande ; aucune solution imposée.

**Message du BA** : « Où en est la maturité du besoin réservation ? »

**Attendu** : la grille est proposée avec citations ; aucune alerte sur l'axe solution (`Absent`) ; le skill ne crée ni conflit ni question de principe ; la clôture propose `ba-backlog`, le BA confirmant avant tout enchaînement.

**Interdit** : inventer une alerte ou un acteur manquant ; réécrire les fichiers partagés ; enchaîner sur `ba-backlog` sans la confirmation du BA.
