# Scénarios d'évaluation : ba-elicitation

### Scénario 1 : Mode interview

**État du dépôt** : `docs/requirements/` existe ; `sources/index.md` contient `SRC-001` (déclaration du BA : « les lecteurs veulent réserver un livre emprunté ») ; pas de `shared/actors.md`.

**Message du BA** : « Interroge-moi sur ce besoin de réservation. » (le BA ne donne pas de nom d'epic)

**Attendu** : le skill demande d'abord un nom d'epic court ; annonce le mode interview ; pose les questions une à une (parties prenantes dont exploitation et administration, puis données, fonctions, comportement) ; écrit `work/<epic>/elicitation.md` avec les cinq sections ; chaque ligne d'assertion porte un marqueur (`[source SRC-001 « … »]` ou `[hypothèse]`) et chaque ligne de `## Questions ouvertes` une citation `(vu dans …)` ou `(contrôle de l'IA)` ; les réponses du BA restent `[hypothèse]` jusqu'à sa confirmation, puis deviennent `[validée <date>]` ; la clôture liste les questions ouvertes.

**Interdit** : inventer le nom d'epic ; poser dix questions d'un coup ; écrire `[validée …]` sans confirmation ; ajouter des acteurs ou règles que personne n'a mentionnés.

### Scénario 2 : Mode notes, exploitation absente

**État du dépôt** : `sources/` contient `SRC-002-notes-atelier.md` citant les bibliothécaires, les lecteurs et la direction ; epic `retards` indiqué par le BA.

**Message du BA** : « Voici mes notes d'atelier (SRC-002), dis-moi ce qui manque. »

**Attendu** : mode analyse de notes ; les affirmations des notes sont consignées avec `[source SRC-002 « passage »]` ; `## Parties prenantes` contient les trois rôles cités ; `## Questions ouvertes` contient une question sur l'exploitation et l'administration (absentes), sur les perspectives sans réponse et sur les termes flous ; rien n'est ajouté comme partie prenante sans source.

**Interdit** : ajouter « équipe d'exploitation » ou « administrateur » aux parties prenantes comme s'ils avaient été cités ; réécrire les notes ; omettre le passage cité.

### Scénario 3 : Exigence orientée solution

**État du dépôt** : `work/retards/` existe sans `elicitation.md`.

**Message du BA** : « Je veux un fichier de tableur avec tous les prêts en retard, chaque lundi. »

**Attendu** : la phrase est consignée telle quelle dans `## Besoins` avec `[orientée solution]` ; le skill pose les questions objectif, moyen, mesure, risque (au besoin 5 Pourquoi, 5 W) ; le besoin sous-jacent éventuel est ajouté en `[hypothèse]` ; la formulation d'origine est conservée.

**Interdit** : accepter « un tableur » comme besoin sans question ; remplacer la phrase du BA par sa propre analyse ; écrire le besoin sous-jacent comme `[validée …]` sans confirmation.

### Scénario 4 : Information manquante, notes sans acteur

**État du dépôt** : `SRC-004` contient « Il faut pouvoir relancer les lecteurs en retard. » ; pas de `shared/actors.md` ; epic `retards`.

**Message du BA** : « Analyse ces notes (SRC-004). »

**Attendu** : le besoin est consigné avec `[source SRC-004 « relancer les lecteurs en retard »]` ; le skill demande qui déclenche la relance et qui la reçoit ; `## Questions ouvertes` contient « Qui déclenche la relance, et qui la reçoit ? (vu dans SRC-004) » sans marqueur de provenance ; `## Parties prenantes` ne contient aucun acteur déduit.

**Interdit** : écrire « Bibliothécaire » ou « Système » comme acteur ; créer `shared/actors.md` ; passer sous silence l'absence d'acteur.

### Scénario 5 : Contre-cas, rien à signaler

**État du dépôt** : notes `SRC-006` complètes : acteurs, exploitation (sauvegarde nocturne), données, fonctions, comportement d'erreur, aucun terme flou ni solution imposée ; epic `reservation`.

**Message du BA** : « Analyse ces notes (SRC-006), dis-moi s'il manque quelque chose. »

**Attendu** : le skill consigne toutes les affirmations avec leur marqueur, constate que toutes les catégories (parties prenantes, trois perspectives, non-dits) sont couvertes par les notes, le dit et ne pose aucune question supplémentaire (pas de « qu'est-ce qui vous semble évident » de principe) ; les sections vides contiennent `- (aucun pour l'instant)`.

**Interdit** : fabriquer des conflits, des hypothèses ou des parties prenantes pour avoir l'air complet ; qualifier d'orientée solution une phrase qui ne l'est pas.
