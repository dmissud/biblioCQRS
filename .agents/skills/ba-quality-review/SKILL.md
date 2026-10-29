---
name: ba-quality-review
description: À utiliser quand le BA veut contrôler la qualité d'une user story ou de toutes les US d'un Epic (« relis l'US-004 », « fais une revue de qualité de l'Epic réservation », « y a-t-il des ambiguïtés ou des contradictions ? », « vérifie le glossaire »). Applique des critères explicites (mots flous, quantificateurs, passif, termes non définis, complétude, cohérence entre US, vérifiabilité, glossaire), ne modifie aucune US et écrit les constats, chacun avec une question au BA, dans `work/<epic>/open-questions.md`.
---

# Revue de qualité d'une US ou d'un Epic

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

- `<REQ_DIR>` absent, ou aucun `work/<epic>/US-*.md` : le dire, proposer `ba-story-spec` (ou `ba-entrypoint`), ne rien créer.
- Identifiant d'US mal formé (`US-8`, `us-008`) : le signaler au BA, ne jamais le corriger ni deviner. Aucun fichier `work/<epic>/US-nnn.md` portant exactement cet identifiant : le dire, proposer `ba-story-spec`, ne rien relire à la place (une ligne du backlog n'est pas une US spécifiée).
- Plusieurs Epics possibles pour l'identifiant ou le nom donné : demander lequel, ne pas choisir.
- `shared/glossary.md` absent : le dire ; le critère 4 (termes non définis) et le critère 8 (glossaire) ne sont pas contrôlables, seuls les termes définis dans l'US sont acceptés ; ne pas créer le glossaire.
- US d'un Epic présentes dans `backlog.md` mais sans fichier : les lister comme « non revues ».

## Entrées / Sorties

- Entrées : une US `<REQ_DIR>/work/<epic>/US-nnn.md` (sections de `ba-story-spec` : `## État des lieux`, `## Récit`, `## Exigences`, `## Scénarios`, `## Exigences non fonctionnelles`, `## DoR`, `## Questions ouvertes`), ou toutes les `US-*.md` du dossier d'un Epic ; `backlog.md` (le titre `### US-nnn : <titre>` situe l'US, format de `ba-backlog`) ; `shared/glossary.md`.
- Sortie unique : `<REQ_DIR>/work/<epic>/open-questions.md`, intitulé `# Questions ouvertes : <epic>`, suivi d'une ligne par constat : `- <critère> : « <citation exacte> » (vu dans US-nnn, <section>) — Question : <question au BA>`. `<critère>` est l'un des huit noms de critères ci-dessous ; `<section>` est l'un des titres de section de l'US. Un constat porte sur UN seul mot ou passage : une citation composée est scindée en plusieurs constats. Défaut d'absence (scénario d'erreur, acteur ou section NFR manquants) : aucune citation de l'élément manquant ; citer mot pour mot l'élément PRÉSENT le plus proche (titre du scénario nominal, texte de l'exigence ou titre de la section) et nommer l'élément manquant dans la question. Un constat entre deux US cite les deux côtés : `- cohérence : « <A> » (vu dans US-001, Exigences) et « <B> » (vu dans US-002, Scénarios) — Question : …`.
- Les lignes de question ne portent aucun marqueur : elles citent où le défaut est observé. Le fichier n'est jamais réécrit : on ajoute à la fin.
- Numérotation : le numéro d'un constat est sa position dans le fichier (premier `- ` = 1). Ordre fixe : par identifiant d'US croissant (un constat entre deux US suit la plus petite), puis par ordre d'apparition dans l'US. Un constat n'est jamais renuméroté ni déplacé ; les nouveaux se placent à la suite.
- Ce skill ne modifie aucune US, aucun glossaire, aucun autre fichier.

## Critères (chaque constat cite un mot ou un passage exact, jamais une paraphrase)

1. mots flous : un mot ou une expression est un constat quand aucun nombre, seuil, référence ni énumération complète n'apparaît dans la phrase qui le définit (« rapidement », « facilement », « adapté », « convivial » ; « en moins de 3 secondes » n'en est pas un). Les exemples illustrent, le test décide. « etc. » est toujours un constat (il cache des éléments non listés), sauf dans un texte cité d'une source.
2. quantificateurs : « tous », « plusieurs », « souvent », « parfois », « la plupart » sans nombre ni règle qui les borne.
3. passif : une action sans acteur (« la réservation est annulée », « un mail est envoyé ») quand l'acteur ou le système responsable n'est pas écrit dans la MÊME exigence ou la MÊME étape de scénario ; un acteur nommé ailleurs dans l'US ne compte pas.
4. termes non définis : un terme métier absent de `shared/glossary.md` et non défini dans l'US (« liste d'attente », « lecteur premium »).
5. complétude : toute exigence qui touche une entrée, un état ou un autre acteur doit avoir au moins un scénario d'erreur, sinon c'est un constat (demander s'il existe un cas d'erreur, ou pourquoi non) ; un acteur ou une valeur absents d'une exigence ; aucune exigence non fonctionnelle et aucun « Aucune exigence non fonctionnelle ».
6. cohérence : deux US du même Epic qui prescrivent des comportements opposés (renouvellement permis / interdit pour le même cas) ; un même terme employé avec deux sens.
7. vérifiabilité : un « Alors » qu'on ne peut ni observer ni tester (« le système réagit correctement », « l'utilisateur est satisfait »).
8. glossaire : un terme employé dans un sens différent de sa définition dans `glossary.md` (« emprunt » défini comme prêt de 21 jours, employé pour une consultation sur place).

## Déroulement

Rien n'est écrit avant l'étape 9. Les constats sont des propositions : le BA peut en écarter.
1. Vérifier `## Pré-requis absents`. Demander au BA le périmètre s'il ne l'a pas donné : « Quelle US (identifiant `US-nnn`) ou quel Epic relire ? » et attendre.
2. US seule : la situer par son titre `### US-nnn : <titre>` dans un `backlog.md` (une mention ailleurs ne compte pas), puis lire `work/<epic>/US-nnn.md`. Epic : lire toutes les `US-*.md` du dossier, ignorer les fichiers cachés, signaler tout autre fichier ou sous-dossier inattendu.
3. Lire `shared/glossary.md`. Lire `work/<epic>/open-questions.md` s'il existe, pour connaître les constats déjà présents.
4. Appliquer les critères 1 à 8 à chaque US, section par section, dans l'ordre des sections. En mode Epic, appliquer ensuite les critères 6 (comparaison de chaque paire d'US) et 8 sur l'ensemble. Ne retenir que ce qui enfreint une règle : un texte précis et défini n'est pas un défaut.
5. Écarter tout constat déjà présent dans `open-questions.md` et dire lesquels. Clé d'un constat : critère + ensemble de ses citations (pour un constat à deux côtés, les deux citations doivent correspondre). La même citation sous deux critères différents fait deux constats, sauf si les critères 6 et 8 s'appliquent au même terme : retenir le 8 si le glossaire est en cause, sinon le 6, jamais les deux.
6. Pour chaque constat retenu, formuler UNE question au BA qui cite le mot ou le passage fautif. La question ne propose aucun texte corrigé ; elle ne propose 2 ou 3 options neutres que si le BA les demande. Une information qui manque (valeur, définition, acteur) reste une question, jamais une définition ou une valeur inventée.
7. Aucun constat (hors constats déjà présents) : dire « aucun constat », ne rien écrire, ne rien demander de plus, et s'arrêter. Ne pas fabriquer un défaut.
8. Présenter la liste numérotée dans l'ordre fixe (numéros qui continuent après les constats existants), puis demander : « Gardez-vous ces constats, ou en écartez-vous certains (donnez les numéros) ? ». Le BA écarte ; les numéros restants ne sont pas renumérotés. Un constat écarté n'est consigné nulle part : à une relance il peut être proposé de nouveau (le BA peut l'écarter encore). Branche « tous écartés » : ne rien écrire, aller à l'étape 10.
9. Écrire une seule fois : créer `open-questions.md` avec son titre s'il n'existe pas, ajouter à la fin les lignes retenues au format de `## Entrées / Sorties`, sans toucher aux lignes existantes. Relire : format exact, chaque citation présente mot pour mot dans l'US (pour un défaut d'absence, l'élément présent cité), aucun marqueur, aucune US modifiée.
10. Annoncer : nombre de constats écrits, écartés et déjà présents, chemin du fichier, US non revues. Dire que ce skill n'applique aucune réponse : le BA (ou `ba-story-spec`) modifie l'US, et le skill peut être relancé pour vérifier. Si un constat révèle une information absente des sources, proposer `ba-elicitation`. Le BA décide et confirme avant tout enchaînement.

## Exemple : information manquante

L'US-005 dit : « Le lecteur est placé sur la liste d'attente. » Ni `glossary.md` ni l'US ne définissent « liste d'attente ».

Le skill ne définit pas le terme. Il propose le constat `- termes non définis : « liste d'attente » (vu dans US-005, Récit) — Question : Que désigne « liste d'attente » (qui y entre, dans quel ordre, quand en sort-on) ?`, attend le tri du BA, puis l'écrit dans `open-questions.md` sans modifier l'US ni le glossaire.
