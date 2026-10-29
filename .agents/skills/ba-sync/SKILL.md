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

Dans tous les cas : le dire dans le compte rendu (statut « non terminé »), ne rien inventer, ne rien déplacer.
- Aucun fichier `<REQ_DIR>/work/<epic>/US-nnn.md` pour l'identifiant donné (mal formé : `US-8`, `us-008`, le signaler sans le corriger) : proposer `ba-story-spec`, qui produit l'US.
- Cas d'utilisation visé (`Cas d'utilisation visé : UC-<DOMAINE>-nn` dans la section `## Scénarios` de l'US) sans fichier `<REQ_DIR>/domains/<d>/use-cases/UC-<DOMAINE>-nn.md` : ne pas le créer ; le signaler comme écart et proposer `ba-as-is-reading` (le BA confirme avant tout enchaînement).
- Aucun emplacement de `.feature` détectable (étape 1) : voir l'exemple ci-dessous.
- `python3` absent : dire que le script n'a pas pu être lancé ; le statut est « non terminé » ; ne jamais le remplacer par une vérification à la main présentée comme sa sortie.

## Entrées / Sorties

- Entrées : l'US `<REQ_DIR>/work/<epic>/US-nnn.md` (format de `ba-story-spec` : sections `## État des lieux`, `## Récit`, `## Exigences`, `## Scénarios`, `## Exigences non fonctionnelles`, `## DoR`, `## Questions ouvertes`) ; le code écrit ; le cas d'utilisation et `rules.md`, `objects.md` du domaine (formats de `ba-as-is-reading`) ; la configuration de test du projet.
- Sorties : `<REQ_DIR>/domains/<d>/use-cases/UC-<DOMAINE>-nn.md` (titre `# UC-<DOMAINE>-nn : <titre>`, sections `## Comportement actuel` puis `## Scénarios`, une ligne `- <chemin du .feature depuis la racine du dépôt> :: <titre exact du scénario>` par scénario) ; `rules.md` et `objects.md` ; les fichiers `.feature` dans le code ; le compte rendu.
- Marqueurs : un contenu tiré de l'US garde le marqueur qu'il porte dans l'US (copié, jamais modifié) ; ce qui est déduit du code porte `[hypothèse]` ; ce skill n'écrit jamais `[validée …]`. Une ligne ajoutée à un fichier de domaine par ce skill reste donc une affirmation à relire par le BA.
- Ce skill ne modifie jamais l'US (elle n'est pas supprimée : le BA ou l'équipe décide quand l'écarter), ne modifie jamais le script de contrôle, et ne tranche jamais une question métier.
- Compte rendu, sections exactes dans cet ordre : `## Convention détectée`, `## Fichiers modifiés`, `## Sortie du script` (verbatim, ou « script non lancé : <raison> »), `## Écarts`, `## Statut` (`terminé` ou `non terminé`).

## Déroulement

Le skill est autonome : il ne demande pas de confirmation à chaque étape. Il ne décide jamais une question métier : tout écart est consigné à l'étape 9.
1. Détecter la convention de test : chercher les `.feature` existants (`**/*.feature`), la configuration de build et de test, les instructions du projet. Relever l'outil Gherkin et le dossier des `.feature`. Rien de détecté : demander au développeur « Quel outil Gherkin et quel dossier pour les fichiers `.feature` ? » ; si personne ne peut répondre, ne rien déplacer ni modifier, passer à l'étape 10 avec « convention non détectée » et le statut « non terminé ».
2. Lire l'US (`## Exigences`, `## Scénarios`). Relever le cas d'utilisation visé, le bloc `gherkin` (sa ligne `# language: <code>`) et la liste des scénarios avec leur tag `@UC-<DOMAINE>-nn` et leur titre exact. Un tag différent du cas d'utilisation visé, ou mal formé : le consigner à l'étape 9, ne pas le corriger.
3. Lire le code produit pour l'US. Comparer chaque exigence et chaque scénario au code : couvert, non couvert, ou comportement du code absent de l'US. Garder la liste pour l'étape 9 ; ne rien corriger.
4. Mettre à jour `<REQ_DIR>/domains/<d>/use-cases/UC-<DOMAINE>-nn.md` : `## Comportement actuel` avec les seuls faits que l'US énonce (marqueur copié de l'US) ; un fait déduit du code seul porte `[hypothèse]`. Ajouter à `rules.md` (`## Règles`) et `objects.md` (`## Objets`, `## Cycle de vie`) uniquement ce que l'US énonce, sans doublon d'une ligne existante. Une contradiction avec une ligne existante est un écart (étape 9), pas une réécriture.
5. Déplacer les scénarios de l'US vers les `.feature` du dossier détecté à l'étape 1 : bloc recopié tel quel, en gardant la ligne `# language: <code>`, chaque tag `@UC-…` et chaque titre de scénario exactement inchangés (le titre est le nom utilisé dans la liste `::`). Ne pas modifier les étapes. Si un fichier cible existe, y ajouter sans réécrire les scénarios existants ni dupliquer un titre déjà présent.
6. Ajouter chaque scénario déplacé à la section `## Scénarios` du cas d'utilisation, au format `- <chemin du .feature> :: <titre>`, chemin relatif à la racine du dépôt, sans doublon.
7. Vérifier la liste : lister les scénarios tagués `@UC-<DOMAINE>-nn` des `.feature` du dossier, puis les comparer à la liste `## Scénarios` du cas d'utilisation. Le script ne signale pas un scénario tagué absent de la liste : cette comparaison est la seule protection. Tout scénario tagué non listé : l'ajouter s'il vient de l'étape 5, sinon le consigner comme écart. Écrire le résultat (« comparaison vide » ou la liste des manquants).
8. Lancer depuis la racine du dépôt : `python3 .agents/skills/ba-sync/scripts/check_uc_links.py --domains <REQ_DIR>/domains --features <dossier des .feature>`. Vérifier d'abord que `--features` est un dossier existant (un chemin mal saisi ferait apparaître de faux `UC_WITHOUT_SCENARIO`). Sortie : une ligne par constat `KIND<TAB>UC-ID<TAB>détail` (`UC_WITHOUT_SCENARIO`, `BROKEN_LINK`, `SCENARIO_WITHOUT_UC`) ; code 0 aucun constat, 1 constats, 2 `--domains` invalide. Coller la sortie telle quelle. Constats : les corriger seulement s'ils viennent d'une erreur de ce skill (chemin, titre recopié), puis relancer ; sinon les laisser dans le compte rendu.
9. Dresser les écarts, section `## Écarts` : une ligne par écart, `- <exigence ou scénario de l'US> : <constat dans le code> (code : chemin:début-fin)`, ou « Aucun écart ». Sans marqueur. Ne jamais modifier l'US ni le cas d'utilisation pour les faire coïncider avec le code. Le BA les lit dans la revue du changement.
10. Statut : `terminé` seulement si le script a fini à 0, la comparaison de l'étape 7 est vide et chaque exigence de l'US est couverte par le code ou listée en écart ; sinon `non terminé`, avec les constats ou le motif. Produire le compte rendu au format de `## Entrées / Sorties`. Le BA relit les cas d'utilisation, la documentation et les écarts dans la revue du changement ; il décide de supprimer l'US et de relancer `ba-story-spec` ou `ba-as-is-reading` si nécessaire.

## Exemple : information manquante

Le développeur dit : « Synchronise l'US-004. » L'US et le cas d'utilisation existent, mais le dépôt n'a aucun `.feature`, aucune configuration d'outil Gherkin, et ses instructions ne parlent pas de tests Gherkin. Le skill demande : « Quel outil Gherkin et quel dossier pour les fichiers `.feature` ? ». Sans réponse, il ne déplace aucun scénario, ne modifie aucun fichier, ne lance pas le script, et rend un compte rendu : `## Convention détectée` : « convention non détectée », `## Fichiers modifiés` : « aucun », `## Statut` : `non terminé`.
