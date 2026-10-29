---
name: ba-as-is-reading
description: À utiliser quand une évolution touche un système existant et que le BA veut savoir ce que le code fait aujourd'hui (« que fait le code actuel pour les réservations ? », « lis l'existant de cette zone », « extrais le comportement actuel », « documente l'existant avant l'évolution »). Lit uniquement la zone de code désignée, en extrait des faits référencés (fichier et lignes) tous marqués `[hypothèse]`, les fait confirmer par le BA groupe par groupe, et alimente `domains/<domaine>/use-cases/`, `rules.md` et `objects.md`.
---

# Lecture de l'existant

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
Un identifiant TAPÉ par le BA dans sa demande (`US 8`, `us-008`, `US8`) est lu sous sa forme canonique (`US-008`,
`SRC-003`, `GOAL-02`, `UC-<DOMAINE>-nn`) : seule la FORME est normalisée (casse, séparateur, zéros initiaux), jamais le
numéro, et la forme lue est annoncée (« je lis US-008 »). Une forme qui ne se ramène pas à préfixe + numéro est signalée
et le BA est interrogé. Un identifiant déjà ÉCRIT dans un fichier et mal formé est signalé, jamais corrigé.

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

- `<REQ_DIR>` absent : le dire au BA et s'arrêter ; ne rien créer sans sa confirmation.
- Zone de code non désignée par le BA : la demander (chemins de fichiers ou fonctionnalité précise). Ne jamais choisir la zone à sa place.
- Chemin de la zone introuvable dans le dépôt : le dire, demander le bon chemin ; ne rien lire d'autre à la place.
- Domaine non donné par le BA et aucun dossier dans `<REQ_DIR>/domains/` : demander le nom du dossier de domaine (minuscules, tirets, sans accent). Ne jamais l'inventer ni le déduire du code. Le dossier n'est créé qu'après la confirmation du BA.

## Entrées / Sorties

- Entrées : la zone de code désignée par le BA ; le domaine ; les fichiers existants de `<REQ_DIR>/domains/<domaine>/` ; `shared/glossary.md` s'il existe.
- Sorties, sous `<REQ_DIR>/domains/<domaine>/` :
  - `use-cases/UC-<DOMAINE>-nn.md`, intitulé `# UC-<DOMAINE>-nn : <titre>`, avec les sections `## Comportement actuel` (lignes de faits) et `## Scénarios` (créée vide, sans aucune ligne : elle sera remplie à l'implémentation) ;
  - `rules.md`, intitulé `# Règles métier : <domaine>`, sections `## Règles` et `## Questions ouvertes` ;
  - `objects.md`, intitulé `# Objets métier : <domaine>`, sections `## Objets`, `## Cycle de vie` et `## Questions ouvertes`.
- Format d'une ligne de fait (assertion) : `- <énoncé en langage métier> (code : <chemin>:<ligne début>-<ligne fin>) [hypothèse]`. Exception : un fait issu d'une réponse du BA reprend la référence de code de la question, ou `(réponse du BA, <date>)` si la question n'en avait pas. Après confirmation du BA, `[hypothèse]` devient `[validée <date>]` ; la référence au code reste. Une ligne de `## Questions ouvertes` ne porte aucun marqueur et se termine par `(contrôle de l'IA)` ou `(code : <chemin>:<lignes>)`.

## Déroulement

1. Vérifier `## Pré-requis absents`. Obtenir la zone et le domaine.
2. Lister les fichiers de la zone (sans les ouvrir). Si la zone est un module ou un dossier entier, ou compte plus de 5 fichiers, ne rien lire : annoncer le nombre de fichiers et demander au BA de la restreindre (une fonctionnalité, quelques fichiers). Reprendre à l'étape 2 avec la zone restreinte.
3. Lister `<REQ_DIR>/domains/<domaine>/use-cases/`. Ignorer les fichiers cachés. Un fichier qui ne s'appelle pas exactement `UC-<DOMAINE>-nn.md` est signalé au BA, jamais renommé. Le numéro d'un nouveau cas d'utilisation est le plus grand `nn` existant dans ce domaine plus 1 (`01` si aucun). `<DOMAINE>` est le nom du dossier en majuscules, sans espace ; le proposer au BA et attendre sa confirmation.
4. Lire uniquement les fichiers de la zone. Si le code appelle du code extérieur à la zone, le noter comme question ouverte, sans le lire.
5. Extraire, pour la zone seulement : les actions possibles (cas d'utilisation), les conditions et contrôles (règles), les données et leurs états (objets, cycle de vie). Chaque fait reçoit son énoncé en langage métier, sa référence `(code : chemin:début-fin)` et `[hypothèse]`. N'écrire que ce que le code montre ; ne pas exécuter le code ; ne pas déduire l'intention métier.
6. Pour tout ce que le code ne permet pas de conclure (règle supposée mais non vérifiable, valeur codée en dur sans explication, branche jamais atteinte, comportement dépendant d'un code hors zone), écrire une question ouverte avec sa citation ; ne jamais en faire une `[hypothèse]`.
7. Découper puis regrouper. Un cas d'utilisation par action ou point d'entrée distinct observable par un utilisateur ou un appelant externe (endpoint, action d'écran, déclencheur planifié). Les conditions, validations et calculs vont dans `rules.md`, les données et états dans `objects.md` ; seule la séquence observable va dans `## Comportement actuel`. Groupes : un par cas d'utilisation, un pour les règles, un pour les objets et leur cycle de vie. Avant d'écrire quoi que ce soit, présenter au BA la liste proposée des titres de cas d'utilisation (avec leurs numéros, et le préfixe si le domaine est nouveau) et attendre sa confirmation ou ses corrections de la liste.
8. Après la confirmation de la liste de l'étape 7, écrire les fichiers en brouillon : tout fait en `[hypothèse]`. Un cas d'utilisation existant n'est identifié que par son titre exact (ligne `# UC-<DOMAINE>-nn : <titre>`) ; s'il existe, le compléter, sinon créer le suivant. Ne pas ajouter une ligne déjà présente (même énoncé et même référence), ne pas supprimer ni réécrire une ligne existante ; si un fait contredit une ligne existante, ajouter une ligne `- Conflit : <ancienne ligne avec son marqueur> / <nouvelle ligne avec `[hypothèse]`>` et poser la question au BA. Créer `rules.md` et `objects.md` avec leurs titre et sections s'ils sont absents.
9. Présenter au BA le premier groupe de faits, un seul à la fois, avec les références au code. Demander : « Ces faits décrivent-ils le comportement réel et voulu ? Confirmez ou corrigez, fait par fait si besoin. »
10. Attendre la réponse. Pour chaque fait explicitement confirmé, remplacer `[hypothèse]` par `[validée <date du jour>]`. Un fait corrigé est réécrit avec la correction et reste `[hypothèse]` jusqu'à nouvelle confirmation. Un fait sans réponse reste `[hypothèse]`. Ne jamais déduire une confirmation d'un silence ou d'un « continuez ».
11. Répéter les étapes 9 et 10 pour chaque groupe suivant, dans l'ordre de l'étape 7.
12. Présenter ensuite les questions ouvertes une à une. La réponse du BA devient un fait, écrit dans le même fichier et la même section que la question qu'il règle (`## Règles` ou `## Objets` / `## Cycle de vie`, ou `## Comportement actuel` du cas d'utilisation concerné), et qui reprend la référence de code de la question ; si la question n'en avait pas, il porte `(réponse du BA, <date du jour>)` à la place de `(code : …)`. Si la réponse est explicite et sans ambiguïté, le fait est écrit `[validée <date du jour>]` sans redemander de confirmation ; si elle est ambiguë ou partielle, le fait reste `[hypothèse]` et la question est posée de nouveau. La question est retirée une fois son fait écrit en `[validée]`.
13. Relire les fichiers écrits : tout fait a sa référence de code (sauf un fait issu d'une réponse du BA sans code, qui porte `(réponse du BA, <date>)`) et son marqueur, toute question sa citation, chaque `## Scénarios` est vide.
14. Annoncer : les chemins écrits ou modifiés, le nombre de faits `[validée]` et `[hypothèse]` par fichier, la liste numérotée des questions ouvertes. Proposer `ba-elicitation` pour les questions qui relèvent d'un besoin à faire émerger, ou `ba-framing` pour continuer ; le BA confirme avant tout enchaînement.

## Exemple : information manquante

Zone : `src/reservation/ReservationService.java`. Aux lignes 40-52, une réservation est refusée si le lecteur a plus de 3 prêts en cours ; rien n'indique si ce plafond est une règle métier ou un réglage temporaire.

Le skill écrit dans `rules.md`, section `## Règles` : `- Une réservation est refusée si le lecteur a plus de 3 prêts en cours (code : src/reservation/ReservationService.java:40-52) [hypothèse]`. Dans `## Questions ouvertes` : `- Le plafond de 3 prêts est-il une règle voulue, et vaut-il pour tous les lecteurs ? (code : src/reservation/ReservationService.java:44)`. Aucun `[validée …]` n'est écrit avant la réponse explicite du BA ; aucune raison métier n'est supposée.
