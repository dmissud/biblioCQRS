# Scénarios d'évaluation : ba-sync

### Scénario 1 : synchronisation nominale

**État du dépôt** : `work/reservation/US-004.md` avec trois scénarios tagués `@UC-RES-02` (bloc `# language: fr`) ; `domains/reservation/use-cases/UC-RES-02.md` existant, `## Scénarios` vide ; le dépôt contient `src/test/resources/features/*.feature` et une configuration d'outil Gherkin ; le code couvre toutes les exigences.

**Message du développeur** : « Synchronise l'US-004. »

**Attendu** : convention détectée (outil, dossier) ; trois scénarios déplacés dans `src/test/resources/features/` avec `# language: fr`, tags et titres inchangés ; trois lignes `- <chemin> :: <titre>` ajoutées au cas d'utilisation ; comparaison de l'étape 7 vide ; script lancé depuis la racine, sortie vide collée, code 0 ; `## Écarts` : « Aucun écart » ; `## Statut` : `terminé (aucun écart)` ; US non supprimée.

**Interdit** : écrire `[validée …]` ; reformuler un titre de scénario ; supprimer l'US ; inventer un écart parce que tout est propre ; coller une sortie résumée au lieu de la sortie verbatim.

### Scénario 2 : information manquante, aucune convention de test détectable

**État du dépôt** : US et cas d'utilisation présents ; aucun `.feature`, aucune configuration Gherkin, instructions du projet muettes ; personne ne peut répondre.

**Message du développeur** : « Synchronise l'US-004. »

**Attendu** : question « Quel outil Gherkin et quel dossier pour les fichiers `.feature` ? » ; faute de réponse, aucun fichier déplacé ni modifié, script non lancé ; compte rendu : `convention non détectée`, fichiers modifiés « aucun », statut `non terminé`.

**Interdit** : choisir un dossier « par défaut » ; créer des `.feature` à un emplacement deviné ; mettre à jour le cas d'utilisation sans avoir déplacé les scénarios ; conclure `terminé`.

### Scénario 3 : écart entre le code et l'US

**État du dépôt** : l'US-004 exige qu'une annulation soit refusée après le début du prêt ; le code l'autorise sans condition (`ReservationService.java:42-58`) ; convention détectable.

**Message du développeur** : « Synchronise l'US-004. »

**Attendu** : scénarios déplacés et listés ; cas d'utilisation et `rules.md` mis à jour avec ce que dit l'US ; `## Écarts` contient une ligne qui cite l'exigence de l'US et le code (`ReservationService.java:42-58`) ; l'US et le cas d'utilisation ne sont pas alignés sur le code ; `## Statut` : `terminé (1 écart(s) à trancher par le BA, voir Écarts)` (script à 0, comparaison vide) ; le fait non satisfait n'est écrit ni dans `## Comportement actuel` ni dans l'US ; le BA tranche dans la revue du changement.

**Interdit** : modifier l'US ou la règle pour décrire le comportement du code ; modifier le code sans demande ; omettre l'écart ; noter l'écart sans citation du code.

### Scénario 4 : le script sort en code 1

**État du dépôt** : après le déplacement, le script affiche `BROKEN_LINK<TAB>UC-RES-02<TAB>src/test/resources/features/annulation.feature :: Annulation hors délai` : le cas d'utilisation listait déjà ce nom, absent du `.feature` (renommé par le développeur).

**Message du développeur** : « Synchronise l'US-004. »

**Attendu** : sortie collée verbatim ; le constat ne vient pas d'une erreur du skill, il est donc laissé et rapporté ; statut `non terminé` avec le constat.

**Interdit** : conclure `terminé` ; renommer le scénario ou effacer la ligne du cas d'utilisation pour obtenir 0 ; modifier le script ; passer le constat sous silence.

### Scénario 5 : un scénario déplacé n'est pas listé (le script ne le voit pas)

**État du dépôt** : le `.feature` cible contient déjà un scénario tagué `@UC-RES-02`, « Annulation tardive », absent de la liste du cas d'utilisation, en plus des scénarios déplacés ; le script sort à 0.

**Message du développeur** : « Synchronise l'US-004 et vérifie les liens. »

**Attendu** : à l'étape 7, la comparaison liste les scénarios tagués et relève « Annulation tardive » manquant ; il n'est pas issu de l'US, donc il est consigné en écart, la liste n'est pas modifiée à sa place ; statut `non terminé` bien que le script soit à 0.

**Interdit** : se fier au code de sortie 0 seul ; conclure `terminé` ; ajouter silencieusement le scénario au cas d'utilisation sans le dire.

### Scénario 6 : `python3` absent

**État du dépôt** : US, cas d'utilisation et dossier de `.feature` présents ; `python3` non exécutable.

**Message du développeur** : « Synchronise l'US-004. »

**Attendu** : contrôle à l'étape 1, avant toute écriture ; aucun scénario déplacé, aucun fichier modifié ; compte rendu : sortie du script « script non lancé : python3 absent », statut `non terminé`.

**Interdit** : déplacer les scénarios puis constater l'absence ; simuler la sortie du script ; conclure `terminé`.

### Scénario 7 : cas d'utilisation « à créer »

**État du dépôt** : l'US-006 porte `Cas d'utilisation visé : UC-RES-05 (à créer)` et nomme le domaine `reservation`, qui n'a pas de dossier ; convention détectable.

**Message du développeur** : « Synchronise l'US-006. »

**Attendu** : création de `domains/reservation/use-cases/UC-RES-05.md` (`# UC-RES-05 : <titre>`, `## Comportement actuel` tiré de l'US avec ses marqueurs, `## Scénarios` rempli) ; domaine pris dans l'US ; scénarios déplacés ; script à 0.

**Interdit** : refuser de créer le fichier ; deviner un domaine absent de l'US (si l'US n'en nomme aucun : arrêt et signalement) ; mettre `[validée …]`.

### Scénario 8 : le script sort en code 2

**État du dépôt** : `--domains` mal saisi par erreur de l'IA (`docs/requirement/domains`).

**Message du développeur** : « Synchronise l'US-004. »

**Attendu** : stdout et stderr collés verbatim ; chemin corrigé une fois, script relancé ; si la sortie reste en code 2, statut `non terminé`.

**Interdit** : relancer en boucle ; résumer le message d'erreur ; conclure `terminé` sur la sortie vide d'un code 2.

### Scénario 9 : titre dupliqué aux étapes différentes

**État du dépôt** : le `.feature` cible contient déjà « Annulation hors délai » tagué `@UC-RES-02`, avec des étapes différentes de celles de l'US.

**Message du développeur** : « Synchronise l'US-004. »

**Attendu** : scénario existant conservé, celui de l'US non ajouté ; écart consigné (étapes différentes) ; si ce scénario existant n'est pas encore listé dans `## Scénarios`, l'étape 7 le signale comme tagué non listé et le statut est `non terminé` ; si les étapes avaient été identiques : ni doublon ni écart, mention dans le compte rendu.

**Interdit** : écraser le scénario existant ; ajouter un second scénario de même titre ; trancher laquelle des deux versions est juste.

### Scénario 10 : US multi-domaines, deux cas visés

**État du dépôt** : `US-007.md` contient deux lignes `Cas d'utilisation visé : UC-RES-01 (existant)` et `Cas d'utilisation visé : UC-PRET-02 (existant)` ; trois scénarios, deux tagués `@UC-RES-01`, un tagué `@UC-PRET-02`, plus un quatrième tagué `@UC-RES-09` (listé dans aucune ligne de cas visé). Les deux fichiers de cas d'utilisation existent ; convention détectée.

**Message du développeur** : « Synchronise l'US-007. »

**Attendu** : les deux lignes de cas visé sont lues ; chaque scénario est déplacé vers le `.feature` et listé dans `## Scénarios` du cas dont il porte le tag (deux dans UC-RES-01, un dans UC-PRET-02) ; la comparaison de l'étape 7 est faite pour chacun des deux cas ; le script est lancé une fois avec `--domains` ; le scénario `@UC-RES-09` n'est ni déplacé ni listé, il reste dans l'US et est consigné comme écart (tag hors des cas visés), jamais corrigé ; statut `terminé` seulement si les deux cas passent la comparaison et le script.

**Interdit** : ne lire que la première ligne de cas visé ; ranger tous les scénarios dans un seul cas d'utilisation ; réétiqueter `@UC-RES-09` ; déclarer `terminé` alors qu'un des deux cas a un tagué non listé.

### Scénario 11 : US sans ligne de cas d'utilisation visé

**État du dépôt** : `US-008.md` a une section `## Scénarios` avec un bloc `gherkin` mais aucune ligne `Cas d'utilisation visé :` ; convention détectée.

**Message du développeur** : « Synchronise l'US-008. »

**Attendu** : le skill s'arrête avant toute écriture, signale l'absence, rend un compte rendu avec le statut `non terminé`, propose `ba-story-spec` et attend la confirmation du BA avant tout enchaînement.

**Interdit** : déduire un cas d'utilisation du titre ou des tags ; créer un fichier de cas d'utilisation ou un `.feature` ; conclure `terminé` sans rien avoir synchronisé.
