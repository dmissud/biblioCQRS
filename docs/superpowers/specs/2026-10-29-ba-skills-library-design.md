# Bibliothèque de skills pour Business Analysts — design

Statut : brouillon pour relecture. Les choix marqués **[mon choix]** sont des décisions de l'assistant, pas de
l'utilisateur ; tout le reste a été dit ou validé par l'utilisateur pendant la session de cadrage.

## 1. Intention

Donner à des Business Analysts (BA), qui assistent un Product Owner (PO) ou en tiennent le rôle, une petite
bibliothèque de skills d'IA pour étudier un besoin et alimenter **un à deux sprints d'avance** en User Stories (US)
prêtes à être développées. Les skills portent les forces d'IREB (et du BABOK) : l'**élicitation** et la **qualité des
exigences**. Ils servent aussi bien pour un **projet neuf** que pour **l'évolution d'un projet existant**, cas le plus
fréquent : la demande est le plus souvent orale, avec un peu de code existant et peu de documentation.

Les skills sont destinés à une bibliothèque personnelle, **réutilisable entre projets et entre IA différentes**
(Claude, Gemini, etc.). Le brouillon actuel (4 skills `ireb-*` et une règle `ireb-pipeline`, produits par Gemini et
jamais utilisés) est le point de départ, pas un acquis.

## 2. Objectifs, non-objectifs, anti-objectifs

**Objectifs**
- Aider le BA à comprendre un besoin : parties prenantes, objectifs, contexte, hypothèses, conflits, non-dits.
- Produire un backlog d'US dont la qualité est vérifiée et dont la provenance est tracée.
- Maintenir, au fil des évolutions, une documentation durable (cas d'utilisation, règles, objets métier…) reliée au
  code par les scénarios Gherkin.
- Fonctionner avec n'importe quelle IA capable de lire un fichier `SKILL.md`.

**Non-objectifs**
- Remplacer le jugement du BA ou du PO : le BA reste l'auteur.
- Gérer le code, l'architecture ou la conception technique (hors du rôle du BA, sauf le skill de synchronisation).
- Remplacer un outil de gestion de backlog (Jira, etc.) : tout vit en fichiers markdown dans le dépôt.
- Imposer une méthode : DoR, priorisation, format des US sont ceux du projet ; les skills proposent des défauts.

**Anti-objectif (le défaut qui rendrait la bibliothèque inutilisable)**
- **Inventer.** Aucun skill ne produit un fait, une exigence ou un sens métier sans source ni validation du BA.
- Autres défaillances à éviter : des cas d'utilisation qui perdent le lien avec leurs scénarios Gherkin ; toute
  mention de l'employeur de l'utilisateur ou reprise mot pour mot de supports de formation propriétaires.

## 3. Principes

1. **Ne jamais inventer.** Chaque information porte une provenance (§5). Ce qui manque devient une question au BA ou
   une hypothèse marquée, jamais un contenu rédigé « pour faire plein ».
2. **Le BA est l'auteur.** Les skills de revue signalent un défaut et posent une question. Ils ne réécrivent pas.
3. **Les skills proposent, le projet décide.** Si le projet définit un DoR, une échelle de priorité ou un gabarit, le
   skill l'utilise. Sinon il propose un défaut, non obligatoire.
4. **Portabilité.** Chaque skill est autonome, au format standard (`SKILL.md` avec `name` et `description`), sans
   dépendance à un outil, un harnais ou un nom de modèle. Les chemins sont paramétrables (`<REQ_DIR>`).
5. **Neutralité.** Les méthodes (Lean Canvas, arbre des buts, 5 Pourquoi, 5W, MoSCoW, grille de maturité, MVD…) sont
   reformulées avec des mots propres. Aucune marque, aucun outil interne, aucun exemple copié.

## 4. Cycle de travail

Le travail est organisé **par demande**, en trois temps (le cycle se répète pour chaque nouvelle demande) :

| Temps | But | Skills |
|---|---|---|
| Entrée (toujours) | Orienter la demande du BA vers le bon skill | point d'entrée |
| Idéation (réception) | Comprendre la demande, ne rien oublier | accueil des sources, élicitation, lecture de l'existant, cadrage |
| Préparation | Formaliser et prioriser les besoins | élicitation, lecture de l'existant, backlog |
| Post-préparation | Spécifier et vérifier ce qui entre en sprint | spécification d'une US, revue de qualité |
| Réalisation (côté développeur) | Livrer et garder la documentation à jour | synchronisation |

Il n'y a **pas de phases séquentielles obligatoires** ni de « quality gates » rigides : le BA avance selon l'état de la
demande. Les validations du BA portent sur les contenus (reformulations, hypothèses), pas sur le passage d'une phase.

## 5. Provenance (traçabilité de la source)

Toute information consignée dans la documentation de travail ou durable porte l'un de ces statuts :

- **source** : citée d'un document, avec son identifiant `SRC-xxx` et le passage. Une **déclaration orale ou écrite du
  BA** dans la conversation (demande de client rapportée, besoin exprimé de vive voix) est aussi une source : elle est
  enregistrée comme `SRC-xxx` de type « déclaration du BA », avec la date, le texte tel qu'il a été dit et, si le BA
  la reformule, la reformulation confirmée ;
- **validée** : reformulée par l'IA puis confirmée par le BA (avec la date ou la trace de la validation) ;
- **hypothèse** : déduite par l'IA (du code, d'un document) et non encore confirmée. Une hypothèse ne peut pas passer
  le DoR d'une US.

Les provenances sont portées par la **documentation durable** (§6), pour survivre à la suppression des US.

## 6. Arborescence et cycle de vie des documents

Tout est en markdown dans le dépôt. Racine : `<REQ_DIR>` (par défaut `docs/requirements`). L'arborescence ci-dessous
est **[mon choix]** pour les noms de dossiers ; le principe (deux zones, domaines métier, index racine) est celui de
l'utilisateur.

```
<REQ_DIR>/
  index.md                 index racine : domaines, sources, travaux en cours
  inbox/                   boîte de dépôt des documents bruts (répertoire d'entrée)
  sources/                 documents exploités, rangés, indexés SRC-001, SRC-002…
  domains/<domaine>/       documentation durable d'un domaine métier
    use-cases/             cas d'utilisation et scénarios
    rules.md               règles métier
    objects.md             objets métier, cycle de vie, attributs critiques
    requirements.md        référentiel d'exigences du domaine
  shared/                  acteurs, contexte (diagramme), glossaire, vision
  work/<epic>/             zone temporaire : demande, Epic, US en cours
```

- **Zone temporaire (`work/`)** : demande, Epic, US. Elles sont **supprimées** une fois implémentées (l'historique
  git les conserve ; pas d'archive à part).
- **Documentation durable** : cas d'utilisation et scénarios, référentiel d'exigences, règles métier, objets métier
  et cycle de vie, acteurs, contexte, glossaire. Elle est classée **par domaine métier** et mise à jour à chaque
  évolution livrée.
- **Sources** : les documents du répertoire d'entrée sont déplacés dans `sources/` après exploitation, jamais
  supprimés, et indexés par `SRC-xxx` pour que les références survivent aux déplacements.
- Une US qui touche plusieurs domaines est autorisée ; le skill le signale au lieu de la forcer dans un seul domaine.

**Minimum Viable Documentation.** Le contenu durable minimal croît avec l'avancement :
parties prenantes, objectifs, contexte et vision (dès l'idéation) ; acteurs et liste des exigences métier (préparation) ;
acteurs et cas d'utilisation, puis scénarios, règles métier et cycle de vie des objets (post-préparation).

## 7. Liaison cas d'utilisation ↔ Gherkin

- Les critères d'acceptation sont rédigés en Gherkin dans l'US (zone temporaire), puis **déplacés dans le code** (fichiers
  `.feature`) à l'implémentation. Après suppression de l'US, le Gherkin reste, comme test exécutable.
- Le cas d'utilisation (documentation durable) doit rester **relié aux scénarios du code**. **[mon choix]** : un
  identifiant stable par cas d'utilisation (`UC-<DOMAINE>-nn`) ; chaque scénario porte un tag `@UC-…` ; chaque cas
  d'utilisation liste les scénarios qui le réalisent. Fonctionne avec tout outil de test.
- **[mon choix]** Un **contrôle de cohérence** mécanique (un petit script, identique pour toutes les IA) détecte :
  cas d'utilisation sans scénario, scénario sans cas d'utilisation, identifiant disparu. Il fait partie de la
  définition de « terminé ».
- Le cas d'utilisation est mis à jour **dans le même changement que le code**, par l'**agent qui travaille pour le
  développeur**. Le BA relit cette mise à jour **[mon choix]** dans la revue du changement.

## 8. Les 9 skills

Chaque skill déclare ses entrées, ses sorties et ses pré-requis, ne suppose pas l'historique de la conversation, et
signale un pré-requis absent au lieu de l'inventer.

0. **Point d'entrée** (routeur léger) : reçoit la demande du BA en langage naturel, lit l'état du travail (`index.md`,
   `work/`) et propose le prochain skill, avec le motif. Sa `description` reprend les formulations courantes (« affinons
   l'US X », « j'ai déposé des documents, travaillons dessus », « affinons le besoin X en US »). `AGENTS.md` y renvoie
   pour les outils qui ne choisissent pas bien un skill à partir de sa description. Il ne produit aucun livrable.
   Routage :
   - « Affinons l'US X » : retrouve l'US dans `work/<epic>/` ; envoie vers l'**élicitation** si elle contient des
     hypothèses ou des points ouverts, sinon vers la **spécification** (6) puis la **revue** (7).
   - « J'ai déposé des documents » : **accueil des sources** (1), puis **élicitation** (2).
   - « Affinons le besoin X en US » : cherche X dans la documentation et les sources. S'il n'y est pas, l'**accueil
     des sources** enregistre la description du BA comme déclaration (§5), puis **élicitation** (2), **backlog** (5),
     **spécification** (6).
   - Si l'identifiant ou le besoin n'est pas retrouvé, il le dit et demande au BA, il ne devine pas.
1. **Accueil des sources** : lit `inbox/`, enregistre chaque document dans l'index `SRC-xxx`, le range dans
   `sources/`, met à jour l'index racine. Enregistre aussi, à la demande, une **déclaration du BA** (§5) sous forme de
   source `SRC-xxx`, sans document.
2. **Élicitation** : identifie les parties prenantes (dont exploitation, administration, utilisateurs cibles) et
   celles que la demande oublie ; choisit la technique adaptée (interview, atelier, analyse de documents, lecture du
   code, observation) ; couvre les perspectives données, fonctions et comportement ; fait émerger non-dits,
   hypothèses et conflits ; repère les exigences orientées solution (questions : objectif, moyen, mesure, risque ; 5
   Pourquoi ; 5W). Deux modes : interroger le BA pas à pas, ou analyser ses notes et signaler les trous.
3. **Lecture de l'existant** : pour une évolution, extrait du code le comportement actuel de la **zone concernée**
   uniquement ; tout ce qui en sort est une hypothèse à faire confirmer ; alimente la documentation durable.
4. **Cadrage** : problème, vision, objectifs (arbre des buts), hypothèses risquées (Lean Canvas, cases vides
   autorisées), contexte et acteurs, grille de maturité du besoin ; produit le contenu MVD d'idéation.
5. **Construction du backlog** : Epics et US par story mapping, priorisation (MoSCoW avec critères explicites),
   traçabilité vers sources et objectifs ; niveau « Feature » **facultatif** entre Epic et US pour la vue globale
   (décision de l'utilisateur) : le skill l'utilise quand le BA le demande ou quand un Epic grossit, sinon il passe
   directement de l'Epic à l'US.
6. **Spécification d'une US** : commence par un **état des lieux** de l'US (hypothèses, points ouverts, provenance
   manquante, écart avec le DoR). S'il reste des hypothèses ou des points ouverts, il pose d'abord les questions au BA
   (ou renvoie vers l'élicitation) au lieu de supposer l'US prête. Ensuite : récit, exigences, scénarios Gherkin
   (nominal et erreurs), exigences non fonctionnelles, vérification par rapport au DoR du projet (à défaut, DoR par
   défaut non obligatoire).
7. **Revue de qualité** : vérifie une US ou un ensemble d'US selon des critères explicites (ambiguïté : mots flous,
   quantificateurs, passif, termes non définis ; complétude ; cohérence et conflits ; vérifiabilité ; respect du
   glossaire). **Signale et pose une question au BA**, ne réécrit pas.
8. **Synchronisation** (côté développeur, agent autonome) : à partir de l'US et de ses scénarios, met à jour le cas
   d'utilisation et la documentation du domaine, déplace le Gherkin dans le code avec ses tags, lance le contrôle de
   cohérence ; ne tranche jamais un écart entre le code et l'US : il le signale.

Les skills 0 à 7 sont **interactifs** (le BA valide). Le skill 8 est **autonome** et donc plus mécanique et précis.

**Point d'entrée et portabilité.** Le choix d'un skill à partir de sa `description` dépend de l'outil. Le skill 0 et le
renvoi dans `AGENTS.md` sont donc le filet de sécurité : le BA peut aussi invoquer un skill par son nom. Le routeur est
**[mon choix]** un skill de plus plutôt qu'une table dans `AGENTS.md`, pour voyager avec la bibliothèque d'un projet à
l'autre.

## 9. Grille de maturité du besoin

**[mon choix, d'après les supports fournis par l'utilisateur]** Cinq axes évalués sur une échelle à 4 niveaux :
description du problème, description des besoins métier, besoins non fonctionnels, hypothèses et contraintes,
description de la solution. L'axe « solution » est inversé : une solution déjà détaillée à ce stade est un signal
d'alerte. Des sous-axes servent de questions guides, non notées.

## 10. Portabilité

- Skills dans `.agents/skills/<nom>/SKILL.md`, au format standard ; `AGENTS.md` à la racine du projet renvoie au point
  d'entrée ; `CLAUDE.md` l'importe ; `.claude/skills` est un lien symbolique vers `.agents/skills` (déjà en place dans
  le dépôt biblioCQRS).
- « Invoquer un skill » signifie lire et appliquer son `SKILL.md`, ou utiliser le mécanisme natif de l'outil.
- Chemins paramétrables (`<REQ_DIR>`) ; aucune dépendance à un outil ni à un modèle.
- La bibliothèque a vocation à vivre dans son propre dépôt, liée ou copiée dans chaque projet. Ce choix est repoussé.

## 11. Ce qui est laissé au constructeur

- Les gabarits précis de chaque livrable, les banques de questions, les critères MoSCoW, la liste du DoR par défaut.
- Le langage du script de contrôle de cohérence (**[mon choix]** à décider selon la portabilité : shell POSIX ou
  Python).
- Les identifiants autres que `SRC-xxx` et `UC-…` (US, objectifs).
- La migration des 4 skills actuels vers les 8 nouveaux (la modélisation DDD devient un contenu des dossiers de
  domaine et du glossaire, plus une étape séparée **[mon choix]**) et la relation avec le contenu existant de
  `docs/requirements/` de biblioCQRS.
- Les exemples complets dans les skills : sur un domaine neutre (la bibliothèque convient comme domaine d'exemple).

## 12. Banc d'essai

Décision de l'utilisateur : le projet biblioCQRS sert de **banc d'essai** des skills.

- Le contenu actuel de `docs/requirements/` est migré vers la nouvelle arborescence (domaines, `SRC-xxx`, provenance).
  Comme ses faits n'ont pas de source, ils sont tous marqués **hypothèse** jusqu'à confirmation par le BA.
- Chaque skill est validé par une passe à blanc sur une vraie demande de biblioCQRS, y compris le cas où l'information
  manque (le skill doit poser une question ou marquer une hypothèse, pas inventer).
- La validation est un jalon du plan, avec ses propres critères d'acceptation.

## 13. Appels du constructeur, issus de la relecture du design

Tous sont **[mon choix]** et modifiables.

- **Brouillon existant.** Les 4 skills `ireb-*` ont été relus : ils ne contiennent aucune référence à l'employeur de
  l'utilisateur ni de texte repris. La migration se fait donc de façon incrémentale (structure réutilisée, texte réécrit
  avec les nouvelles règles), pas par une réécriture à partir de zéro. Un passage de contrôle « aucune mention de
  l'employeur » est fait sur chaque skill final.
- **Skill 8 (synchronisation).** Il est générique : une première étape détecte la convention du projet (outil de test,
  emplacement des `.feature`) et, à défaut, demande au développeur. Il ne cible aucun outil de test. Le BA a accès au
  dépôt puisque toute la documentation est en markdown dedans ; sa relecture se fait dans la revue du changement du
  projet.
- **Script de contrôle.** Il fait partie de la bibliothèque et se range dans le dossier du skill concerné
  (`scripts/`), pour voyager avec lui. Python 3 sans dépendance externe. Il lit les tags `@UC-…` par simple recherche
  de motif, donc indépendamment de l'outil de test et de la langue du Gherkin. C'est un livrable de code avec ses
  propres tests.
- **Langue.** Les skills et livrables sont en français par défaut. La langue des mots-clés Gherkin suit celle du projet
  et est déclarée dans le fichier (`# language: fr`).
- **Critère « ne jamais inventer ».** Chaque skill contient au moins un exemple de cas où l'information manque et où
  le comportement attendu est de poser une question ou de marquer une hypothèse. Le contrôle de cet exemple fait partie
  de la validation d'un skill.
