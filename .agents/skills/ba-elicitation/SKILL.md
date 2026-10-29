---
name: ba-elicitation
description: À utiliser quand le BA veut faire émerger ou compléter un besoin avant de le cadrer ou de le découper en US (« interroge-moi sur ce besoin », « voici mes notes, qu'est-ce qui manque ? », « qui est concerné ? », « on commence l'élicitation »). Identifie les parties prenantes, les besoins, les hypothèses, les conflits et les questions ouvertes, repère les exigences orientées solution, et écrit `work/<epic>/elicitation.md` avec la provenance de chaque ligne.
---

# Élicitation : faire émerger le besoin

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
- Aucune source `SRC-nnn` (`sources/index.md` absent ou vide) et aucune parole du BA à exploiter : le dire et demander au BA de décrire le besoin ; cette description est alors enregistrée comme déclaration via `ba-source-intake` (avec son accord). Ne rien déduire.
- `<REQ_DIR>/shared/actors.md` absent : le dire, continuer sans ; les acteurs viennent alors uniquement du BA ou des sources. Ne pas créer ce fichier.
- Nom d'epic non donné par le BA : demander un nom court (minuscules, tirets, sans accent). Ne jamais l'inventer ni le déduire du contenu.

## Entrées / Sorties

- Entrées : la demande du BA ; les sources `SRC-nnn` indiquées ou pertinentes (`sources/index.md`) ; `shared/actors.md` et `shared/glossary.md` s'ils existent ; les notes du BA (mode 2).
- Sorties : `<REQ_DIR>/work/<epic>/elicitation.md`, intitulé `# Élicitation : <epic>`, avec exactement les sections `## Parties prenantes`, `## Besoins`, `## Questions ouvertes`, `## Hypothèses`, `## Conflits`. Chaque ligne de liste commence par `- `. Les lignes d'assertion (Parties prenantes, Besoins, Hypothèses, Conflits) se terminent par un marqueur de provenance. Les lignes de `## Questions ouvertes` se terminent par leur citation `(vu dans SRC-nnn)` ou `(contrôle de l'IA)`. Une section sans contenu contient `- (aucun pour l'instant)`, qui n'a besoin ni de l'un ni de l'autre.

## Déroulement

Règle de provenance (s'applique aux deux modes). Les marqueurs portent sur les assertions : `## Parties prenantes`, `## Besoins`, `## Hypothèses`, `## Conflits`. Une ligne de `## Questions ouvertes` n'est pas une assertion : elle ne porte aucun marqueur, mais cite où le trou a été vu, sous la forme `(vu dans SRC-nnn)` ou `(contrôle de l'IA)`. Une ligne de `## Conflits` cite les deux camps, chacun avec son marqueur (`[source SRC-a « … »]` / `[source SRC-b « … »]`, ou `[hypothèse]` pour un camp venant de l'IA). Une ligne reprise de `shared/actors.md` garde le marqueur qu'elle y a déjà. Un trou (acteur manquant, perspective absente) est toujours une question, jamais une `[hypothèse]`.

Contrôles communs (liste de référence pour les deux modes) :
- C1 parties prenantes : utilisateurs cibles ? administration ? exploitation (supervision, sauvegarde, support) ? qui décide ou paie ? qui subit le changement ? qui fournit ou reçoit des données ?
- C2 perspectives : données (quelles informations, d'où, quelle durée de vie), fonctions (que fait chaque acteur), comportement (états, erreurs, délais, cas limites).
- C3 exigences orientées solution : une phrase qui impose un moyen (« un fichier Excel », « un bouton rouge »).
- C4 non-dits : ce qui semble évident mais n'est écrit nulle part ; suppositions non vérifiées ; désaccords entre personnes.

Préparation (communes)
1. Vérifier `## Pré-requis absents`. Obtenir le nom de l'epic. Si `work/<epic>/elicitation.md` existe, le lire et le compléter sans supprimer ni réécrire ses lignes ni ajouter une ligne déjà présente.
2. Lire les sources concernées et `shared/actors.md`.
3. Choisir le mode et le dire : mode 1 (interview) sans notes ; mode 2 (analyse de notes) si le BA fournit des notes ou un document. En cas de doute, demander.
4. Proposer la technique adaptée, le BA décide : interview (peu de personnes, sujet flou ou sensible) ; atelier (parties prenantes à aligner) ; analyse de documents (sources existantes) ; lecture du code (évolution d'un système existant : renvoyer vers `ba-as-is-reading`) ; observation (gestes réels différents du discours).

Mode 1 : interview. Une seule question à la fois, attendre la réponse avant la suivante.
5. Poser les questions de C1 une à une. Consigner chaque réponse `- <rôle> : <intérêt> [hypothèse]`. Une catégorie sans réponse devient une ligne de `## Questions ouvertes` (`(contrôle de l'IA)`). Ne pas inventer de personne.
6. Poser au moins une question par perspective de C2. Consigner dans `## Besoins`, ligne préfixée `[données]`, `[fonctions]` ou `[comportement]` et terminée par `[hypothèse]` (réponse du BA, pas encore confirmée). Une perspective sans réponse va en `## Questions ouvertes`.
7. C3 : consigner la phrase telle quelle dans `## Besoins` avec `[orientée solution]`, puis demander successivement l'objectif (qu'en attendez-vous ?), le moyen (pourquoi celui-ci ?), la mesure (comment saura-t-on que c'est réussi ?), le risque (que se passe-t-il sinon ?). Si c'est encore flou, jusqu'à 5 « pourquoi », puis les 5 W (qui, quoi, où, quand, pourquoi). Le besoin sous-jacent est ajouté `[hypothèse]` ; la phrase d'origine reste.
8. C4 : demander « qu'est-ce qui vous semble évident mais n'est écrit nulle part ? », « qu'est-ce qui est supposé sans vérification ? », « qui voudrait autre chose ? ». Suppositions dans `## Hypothèses`, désaccords dans `## Conflits`, trous dans `## Questions ouvertes`.
9. En fin d'interview, proposer d'enregistrer les réponses clés du BA comme déclaration via `ba-source-intake` (le BA décide). Sans accord, elles restent `[hypothèse]`.

Mode 2 : analyse de notes
10. Lire les notes en entier. Si elles ne sont pas encore une source `SRC-nnn`, proposer que `ba-source-intake` les enregistre, attendre l'accord du BA, et ne pas les citer en `[source …]` tant qu'aucun numéro n'existe (d'ici là, les affirmations sont `[hypothèse]`).
11. Consigner chaque affirmation des notes dans la section qui convient, avec `[source SRC-nnn « passage cité »]`.
12. Passer C1, C2, C3 et C4 en liste de contrôle contre les notes, sans poser de question au BA à ce stade. C3 : marquer `[orientée solution]` les besoins concernés.
13. Lister les trous constatés : chacun est une ligne de `## Questions ouvertes` avec `(vu dans SRC-nnn)`. Si toutes les catégories sont couvertes par les notes, le dire et ne rien demander de plus (pas de question C4 « de principe »).
14. Poser alors au BA, une à la fois, les seules questions des trous, et appliquer pour une exigence orientée solution les questions objectif / moyen / mesure / risque de l'étape 7 (mode 1).

Clôture (communes)
15. Les affirmations du BA dans la conversation restent `[hypothèse]` jusqu'à sa confirmation explicite. Présenter les lignes à valider ; seulement après son « oui » (ligne par ligne ou en bloc), remplacer par `[validée <date du jour>]`. Une reformulation corrigée redevient `[hypothèse]` jusqu'à nouvelle confirmation.
16. Écrire `work/<epic>/elicitation.md` (créer le dossier au besoin). Relire : toute assertion a son marqueur, toute question sa mention `(vu dans …)` ou `(contrôle de l'IA)`, aucun acteur ni besoin ne vient de nulle part.
17. Annoncer : le chemin, le nombre de lignes par section, la liste numérotée des questions ouvertes et des lignes `[hypothèse]` à confirmer. Proposer `ba-framing` ; le BA confirme avant tout enchaînement.

## Exemple : information manquante

Notes enregistrées (SRC-004) : « Il faut pouvoir relancer les lecteurs en retard. » Aucun acteur n'est nommé, `shared/actors.md` est absent.

Le skill consigne dans `## Besoins` : `- [fonctions] relancer les lecteurs en retard [source SRC-004 « relancer les lecteurs en retard »]`. Dans `## Questions ouvertes` : `- Qui déclenche la relance, et qui la reçoit ? (vu dans SRC-004)`. Aucun acteur n'est écrit dans `## Parties prenantes`, aucune `[hypothèse]` ne le remplace ; la question est posée au BA.
