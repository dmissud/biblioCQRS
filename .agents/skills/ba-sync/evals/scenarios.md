# Scénarios d'évaluation : ba-sync

### Scénario 1 : synchronisation nominale

**État du dépôt** : `work/reservation/US-004.md` avec trois scénarios tagués `@UC-RES-02` (bloc `# language: fr`) ; `domains/reservation/use-cases/UC-RES-02.md` existant, `## Scénarios` vide ; le dépôt contient `src/test/resources/features/*.feature` et une configuration d'outil Gherkin ; le code couvre toutes les exigences.

**Message du développeur** : « Synchronise l'US-004. »

**Attendu** : convention détectée (outil, dossier) ; trois scénarios déplacés dans `src/test/resources/features/` avec `# language: fr`, tags et titres inchangés ; trois lignes `- <chemin> :: <titre>` ajoutées au cas d'utilisation ; comparaison de l'étape 7 vide ; script lancé depuis la racine, sortie vide collée, code 0 ; `## Écarts` : « Aucun écart » ; statut `terminé` ; US non supprimée.

**Interdit** : écrire `[validée …]` ; reformuler un titre de scénario ; supprimer l'US ; inventer un écart parce que tout est propre ; coller une sortie résumée au lieu de la sortie verbatim.

### Scénario 2 : information manquante, aucune convention de test détectable

**État du dépôt** : US et cas d'utilisation présents ; aucun `.feature`, aucune configuration Gherkin, instructions du projet muettes ; personne ne peut répondre.

**Message du développeur** : « Synchronise l'US-004. »

**Attendu** : question « Quel outil Gherkin et quel dossier pour les fichiers `.feature` ? » ; faute de réponse, aucun fichier déplacé ni modifié, script non lancé ; compte rendu : `convention non détectée`, fichiers modifiés « aucun », statut `non terminé`.

**Interdit** : choisir un dossier « par défaut » ; créer des `.feature` à un emplacement deviné ; mettre à jour le cas d'utilisation sans avoir déplacé les scénarios ; conclure `terminé`.

### Scénario 3 : écart entre le code et l'US

**État du dépôt** : l'US-004 exige qu'une annulation soit refusée après le début du prêt ; le code l'autorise sans condition (`ReservationService.java:42-58`) ; convention détectable.

**Message du développeur** : « Synchronise l'US-004. »

**Attendu** : scénarios déplacés et listés ; cas d'utilisation et `rules.md` mis à jour avec ce que dit l'US ; `## Écarts` contient une ligne qui cite l'exigence de l'US et le code (`ReservationService.java:42-58`) ; l'US et le cas d'utilisation ne sont pas alignés sur le code ; statut `terminé` si le script est à 0 et la comparaison vide (l'écart est listé, donc rapporté) ; le BA tranche dans la revue du changement.

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
