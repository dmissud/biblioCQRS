---
name: ba-sync
description: À utiliser quand une US spécifiée vient d'être implémentée et que l'agent du développeur doit synchroniser la documentation avec le code (« synchronise l'US-004 », « mets à jour le cas d'utilisation après l'implémentation », « déplace les scénarios Gherkin dans le code », « contrôle les liens cas d'utilisation / scénarios »). Skill autonome, souvent sans BA : détecte la convention de test, déplace les scénarios taggés `@UC-…` de l'US vers les `.feature`, met à jour le cas d'utilisation et la documentation du domaine avec ce que dit l'US, lance le script de contrôle et produit un compte rendu dont les écarts sont signalés, jamais tranchés.
---

# Synchronisation US, cas d'utilisation et code

## Conventions

Chemins : la racine de la documentation est `<REQ_DIR>` (défaut `docs/requirements`). Arborescence :
- `<REQ_DIR>/index.md` : index racine (sections `## Domaines`, `## Sources`, `## Travaux en cours`)
- `<REQ_DIR>/inbox/` : documents bruts déposés, à exploiter
- `<REQ_DIR>/sources/` : documents exploités, jamais supprimés, indexés par `SRC-nnn`
- `<REQ_DIR>/domains/<domaine>/` : documentation durable, avec `use-cases/`, `rules.md`, `objects.md`, `requirements.md`
- `<REQ_DIR>/shared/` : `vision.md`, `context.md`, `actors.md`, `glossary.md`
- `<REQ_DIR>/work/<epic>/` : zone temporaire (demande, Epic, US en cours), supprimée une fois implémentée

Identifiants : `SRC-nnn` (source), `GOAL-nn` (objectif), `US-nnn` (user story), `UC-<DOMAINE>-nn` (cas d'utilisation ;
DOMAINE en majuscules, sans espace). Le tag Gherkin d'un scénario est `@UC-<DOMAINE>-nn`, sensible à la casse.
Un identifiant mal saisi est une erreur à signaler, jamais une correspondance approximative.

Provenance : toute affirmation consignée porte l'un de ces marqueurs, en ligne. Une question ou une lacune n'est pas une
affirmation : elle ne porte aucun marqueur et cite où elle a été observée, `(vu dans SRC-nnn)` ou `(contrôle de l'IA)`.
- `[source SRC-003 « passage cité »]` : citée d'une source, avec son passage
- `[validée 2026-10-29]` : reformulée puis confirmée par le BA, avec la date
- `[hypothèse]` : déduite, non confirmée. Une hypothèse ne passe pas le DoR d'une US.
Une déclaration du BA dans la conversation est une source : proposer de l'enregistrer comme `SRC-nnn` (date et texte), avec l'accord du BA.

Ne jamais inventer : une information manquante devient une question posée au BA, ou une `[hypothèse]` marquée.
Ne jamais combler un vide par un contenu plausible. Le BA est l'auteur : signaler un défaut et poser une question,
sans réécrire à sa place.

Le projet décide : si le projet définit un DoR, une échelle de priorité ou un gabarit, il prime sur le défaut proposé ici.

« Invoquer un skill » signifie lire son `SKILL.md` et en appliquer les instructions, quel que soit l'outil utilisé.

Si `<REQ_DIR>` ou un pré-requis est absent, le dire (voir `## Pré-requis absents`) et ne rien créer d'inventé.


## Pré-requis absents

Dans tous les cas : le dire dans le compte rendu (statut `non terminé`), ne rien inventer, ne rien déplacer ni modifier. Les contrôles de `python3` et du dossier des `.feature` ont lieu à l'étape 1 ; ceux de l'US et du fichier du cas d'utilisation, à l'étape 2 ; tous avant toute écriture.
- Aucun fichier `<REQ_DIR>/work/<epic>/US-nnn.md` pour l'identifiant donné (mal formé : `US-8`, `us-008`, le signaler sans le corriger) : proposer `ba-story-spec`, qui produit l'US.
- `python3` non exécutable : le dire ; le script n'est pas lancé ; ne jamais le remplacer par une vérification à la main présentée comme sa sortie.
- Aucun emplacement de `.feature` détectable, ou dossier cible inexistant : voir l'exemple ci-dessous.
- Cas d'utilisation « existant » sans fichier `<REQ_DIR>/domains/<d>/use-cases/UC-<DOMAINE>-nn.md` : ne pas le créer, le signaler et proposer `ba-as-is-reading` (le BA confirme avant tout enchaînement). Cas « à créer » : voir l'étape 2.
- US sans aucune ligne `Cas d'utilisation visé :` : arrêter avant toute écriture, le signaler, statut `non terminé`, proposer `ba-story-spec` (le BA confirme avant tout enchaînement).
- US sans domaine nommé alors que le cas d'utilisation est « à créer » : arrêter et le signaler.

## Entrées / Sorties

- Entrées : l'US `<REQ_DIR>/work/<epic>/US-nnn.md` (format de `ba-story-spec`, dont `## Scénarios` avec une ligne `Cas d'utilisation visé : UC-<DOMAINE>-nn (existant | à créer)` par domaine) ; le code écrit ; le cas d'utilisation, `rules.md` et `objects.md` du domaine (formats de `ba-as-is-reading`) ; la configuration de test du projet.
- Sorties : `<REQ_DIR>/domains/<d>/use-cases/UC-<DOMAINE>-nn.md` (titre `# UC-<DOMAINE>-nn : <titre>`, sections `## Comportement actuel` puis `## Scénarios`, une ligne `- <chemin du .feature depuis la racine du dépôt> :: <titre exact du scénario>` par scénario) ; `rules.md` et `objects.md` ; `<REQ_DIR>/domains/<d>/requirements.md` (référentiel d'exigences du domaine, titre `# Exigences : <domaine>`, une ligne `- <exigence normalisée> (origine : US-nnn, <AAAA-MM-JJ>) <marqueur>` par exigence de l'US, la date étant celle du jour) ; les fichiers `.feature` ; le compte rendu.
- Marqueurs : un contenu tiré de l'US garde son marqueur (copié, jamais modifié) ; ce que l'IA déduit du code porte `[hypothèse]` ; jamais `[validée …]`.
- Jamais modifiés : l'US (le BA ou l'équipe décide quand l'écarter), le script, le code. Aucune question métier n'est tranchée.
- Compte rendu, sections exactes dans cet ordre : `## Convention détectée`, `## Fichiers modifiés`, `## Sortie du script` (verbatim, stdout et stderr, ou « script non lancé : <raison> »), `## Écarts`, `## Statut`. La ligne de statut est `terminé (aucun écart)`, `terminé (N écart(s) à trancher par le BA, voir Écarts)` ou `non terminé`.

## Déroulement

Skill autonome : pas de confirmation à chaque étape, et jamais de décision métier ; tout écart est consigné à l'étape 9.
1. Contrôles avant toute écriture. Vérifier que `python3` s'exécute. Détecter la convention : `.feature` existants, configuration de build et de test, instructions du projet ; relever l'outil Gherkin et le dossier. Plusieurs dossiers contiennent des `.feature` et la convention du projet n'en désigne pas un : demander lequel. Rien de détecté : demander « Quel outil Gherkin et quel dossier pour les fichiers `.feature` ? ». Personne ne peut répondre : « convention non détectée ». Vérifier que le dossier retenu existe. Tout échec : passer à l'étape 10 avec le motif, sans rien déplacer ni modifier.
2. Lire l'US (`## Exigences`, `## Scénarios`) : TOUTES les lignes `Cas d'utilisation visé :` (une par domaine) avec leur suffixe `(existant | à créer)`, ligne `# language: <code>`, scénarios avec tag `@UC-<DOMAINE>-nn` et titre exact. Aucune ligne `Cas d'utilisation visé :` : arrêter avant toute écriture, passer à l'étape 10 (statut `non terminé`) et proposer `ba-story-spec`. Un tag qui ne correspond à aucun des cas visés listés, ou mal formé : écart, jamais corrigé ; ce scénario reste dans l'US, n'est ni déplacé ni listé. Pour chaque cas « à créer » : créer `<REQ_DIR>/domains/<d>/use-cases/UC-<DOMAINE>-nn.md` avec le titre `# UC-<DOMAINE>-nn : <titre>` (titre du cas d'utilisation dans l'US, sinon titre de l'US), `## Comportement actuel` (étape 4) et `## Scénarios` (étape 6). Le domaine `<d>` vient de l'US, jamais inventé ; s'il n'a pas de dossier et que l'US le nomme, créer le dossier.
3. Lire le code de l'US. Comparer chaque exigence et scénario : couvert, non couvert, ou comportement du code absent de l'US. Garder les écarts pour l'étape 9 ; ne rien corriger.
4. Remplir `## Comportement actuel` avec les seuls faits de l'US (chaque fait va au cas d'utilisation et au domaine auxquels l'US le rattache ; si l'US ne le précise pas, l'écrire en écart, ne pas deviner) que le code satisfait (marqueur copié de l'US) ; un fait déduit du code seul porte `[hypothèse]`. Un fait que le code ne satisfait pas n'y est jamais écrit : il va seulement dans `## Écarts`. Ajouter à `rules.md` (`## Règles`) et `objects.md` (`## Objets`, `## Cycle de vie`) uniquement ce que l'US énonce, sans doublon. Une contradiction avec une ligne existante est un écart, pas une réécriture. Référentiel d'exigences : ajouter chaque ligne de `## Exigences` de l'US (la ligne `- Dépendances :` n'est pas une exigence, elle est ignorée) à `<REQ_DIR>/domains/<d>/requirements.md`, créé avec le titre `# Exigences : <domaine>` s'il est absent. Le domaine est celui de l'US si elle n'en nomme qu'un ; pour une US multi-domaine, seulement celui que l'US attribue à l'exigence, sinon écart et la ligne n'est pas écrite. Texte de l'exigence et marqueur copiés de l'US sans modification (une ligne sans marqueur dans l'US est écrite `[hypothèse]` ; ce skill n'écrit jamais `[validée …]` de lui-même). Ajout seulement : jamais de suppression ni de réécriture d'une ligne existante ; une ligne dont le texte est identique à une ligne existante est ignorée et le compte rendu le dit ; une exigence qui contredit une ligne existante (même sujet, comportement ou valeur incompatible) n'est pas écrite et devient un écart citant les deux lignes, sans trancher.
5. Déplacer les scénarios vers les `.feature` du dossier détecté (chaque scénario va avec le cas d'utilisation dont il porte le tag ; un scénario à tag orphelin n'est pas déplacé) : bloc recopié tel quel avec `# language: <code>`, tags et titres inchangés (le titre est le nom de la liste `::`), étapes inchangées. Fichier cible existant : y ajouter sans réécrire l'existant. Titre déjà présent : étapes identiques, ne pas dupliquer et le dire ; étapes différentes, garder l'existant et noter un écart.
6. Ajouter chaque scénario déplacé (ou déjà présent à l'identique) à `## Scénarios` du cas d'utilisation dont il porte le tag (par cas visé) : `- <chemin du .feature> :: <titre>`, chemin relatif à la racine du dépôt, sans doublon.
7. Comparer, pour chaque cas visé séparément : lister dans les `.feature` du dossier les scénarios tagués avec l'identifiant de CE cas visé seulement. Un scénario est tagué si le tag est sur la ou les lignes juste au-dessus de sa ligne d'en-tête (`Scénario`, `Plan du scénario`, `Scenario`, `Scenario Outline`…) ; plusieurs tags peuvent partager une ligne ; un tag sur une ligne `Fonctionnalité`/`Feature` est ignoré. Comparer à la liste `## Scénarios`. Le script ne signale pas un tagué non listé : cette comparaison est la seule protection. Tagué non listé issu de l'étape 5 : l'ajouter ; sinon l'écart n'est pas corrigé, il est consigné. Écrire « comparaison vide » ou les manquants, par cas visé.
8. Depuis la racine du dépôt : `python3 .agents/skills/ba-sync/scripts/check_uc_links.py --domains <REQ_DIR>/domains --features <dossier des .feature>`. Sortie normale : une ligne `KIND<TAB>UC-ID<TAB>détail` par constat (`UC_WITHOUT_SCENARIO`, `BROKEN_LINK`, `SCENARIO_WITHOUT_UC`) ; code 0 aucun constat, 1 constats. Code 2, ou sortie qui n'est pas faite de lignes de constat (trace d'erreur, fichier illisible) : coller stdout et stderr verbatim ; si un chemin est mal saisi, le corriger une fois et relancer, sinon statut `non terminé`. Un seul lancement couvre tous les domaines. Coller la sortie finale telle quelle. Un constat n'est corrigé que s'il vient d'une erreur de ce skill (chemin, titre recopié), puis relancer ; sinon il reste dans le compte rendu.
9. `## Écarts` : une ligne par écart, `- <exigence ou scénario de l'US> : <constat dans le code> (code : chemin:début-fin)`, ou « Aucun écart ». Sans marqueur. Jamais d'édition de l'US ni du cas d'utilisation pour les aligner sur le code.
10. Statut : `non terminé` (avec constats ou motif) si le script n'a pas fini à 0, ou si la comparaison de l'étape 7 n'est pas vide pour l'un des cas visés, ou si un contrôle des étapes 1 et 2 a échoué. Sinon (tous les cas visés passent) `terminé`, suivi du nombre d'écarts selon le format de `## Entrées / Sorties`. Un scénario tagué non listé rend le lien de documentation invérifiable : le skill ne peut pas garantir la synchronisation, d'où `non terminé` ; un écart code/US rapporté est un fait connu et transmis, d'où `terminé`. Produire le compte rendu. Le BA relit cas d'utilisation, documentation et écarts dans la revue du changement ; `ba-story-spec` (qui a produit l'US) ou `ba-as-is-reading` peuvent suivre, avec son accord.

## Exemple : information manquante

Le développeur dit : « Synchronise l'US-004. » L'US et le cas d'utilisation existent, mais le dépôt n'a aucun `.feature`, aucune configuration d'outil Gherkin, et ses instructions ne parlent pas de tests Gherkin. Le skill demande : « Quel outil Gherkin et quel dossier pour les fichiers `.feature` ? ». Sans réponse, il ne déplace ni ne modifie rien, ne lance pas le script, et rend un compte rendu : `## Convention détectée` : « convention non détectée », `## Fichiers modifiés` : « aucun », `## Statut` : `non terminé`.
