# Bibliothèque de skills BA — plan d'implémentation

> **Pour les agents d'exécution :** SOUS-SKILL REQUIS : utiliser superpowers:subagent-driven-development (recommandé) ou superpowers:executing-plans pour exécuter ce plan tâche par tâche. Les étapes utilisent des cases à cocher (`- [ ]`).

**Objectif :** produire 9 skills portables (point d'entrée, accueil des sources, élicitation, lecture de l'existant, cadrage, backlog, spécification d'US, revue de qualité, synchronisation), deux outils Python testés (lint des skills, contrôle cas d'utilisation ↔ Gherkin), puis les valider sur le projet biblioCQRS.

**Architecture :** chaque skill est un dossier `.agents/skills/<nom>/` avec `SKILL.md` (format standard, neutre vis-à-vis de l'outil) et `evals/scenarios.md`. Un bloc « Conventions » identique, défini une fois, est copié dans chaque skill et vérifié par le lint. Les deux scripts sont des outils Python 3 sans dépendance externe, développés en TDD avec pytest.

**Stack :** Markdown, Python 3.14 (stdlib seule), pytest 9.

**Spec :** `docs/superpowers/specs/2026-10-29-ba-skills-library-design.md` (les renvois « §n » désignent ses sections).

**Commits :** les étapes « Commit » ne sont exécutées qu'une fois l'exécution validée par l'utilisateur. Rien n'a été committé avant ce plan (spec, `AGENTS.md`, `CLAUDE.md` et lien `.claude/skills` compris : à inclure dans le premier commit).

## Contraintes globales

- Skills en français ; chemins via `<REQ_DIR>` (défaut `docs/requirements`) ; aucune dépendance à un outil, un harnais ou un modèle (§3, §10).
- Aucune mention de l'employeur de l'utilisateur, de sa marque ni d'outils internes dans les skills (§3, §2). Le nom lui-même ne doit apparaître nulle part dans le dépôt versionné.
- « Ne jamais inventer » : chaque skill contient `## Exemple : information manquante` (§13).
- Noms de skills : `ba-entrypoint`, `ba-source-intake`, `ba-elicitation`, `ba-as-is-reading`, `ba-framing`, `ba-backlog`, `ba-story-spec`, `ba-quality-review`, `ba-sync`.
- Identifiants : `SRC-nnn`, `GOAL-nn`, `US-nnn`, `UC-<DOMAINE>-nn` (DOMAINE en majuscules sans espace) ; tag Gherkin `@UC-<DOMAINE>-nn`, sensible à la casse.
- Provenance (§5) en ligne : `[source SRC-003 « passage »]`, `[validée 2026-10-29]`, `[hypothèse]`.
- Arborescence de `<REQ_DIR>` : `index.md`, `inbox/`, `sources/`, `domains/<domaine>/{use-cases/,rules.md,objects.md,requirements.md}`, `shared/{vision.md,context.md,actors.md,glossary.md}`, `work/<epic>/` (§6).

## Points de revue

Entrées ou conditions que le spec implique sans qu'un test d'origine les couvre ; chacune a son test dans la tâche indiquée.

1. Tags `@uc-pret-03` ou `@UC-PRET-3` mal saisis : ce sont des erreurs, pas des correspondances (Tâche 3).
2. Fichiers `.feature` en CRLF, mots-clés en français, plans de scénario (Tâche 3).
3. Dépôt sans `<REQ_DIR>` ou sans pré-requis : le skill le dit et ne crée rien d'inventé (Tâche 1, règle de lint ; scénarios dans chaque skill).
4. Document non textuel dans `inbox/` (pdf, image) : enregistré comme source, contenu non revendiqué (Tâche 5).
5. « Affinons l'US 12 » alors que les identifiants s'écrivent `US-012` ; deux US créées avec le même numéro (Tâches 4 et 9).

## Structure des fichiers

- `.agents/tools/lint_skills.py` + `test_lint_skills.py` : vérifie la forme de chaque skill.
- `.agents/tools/conventions.md` : bloc « Conventions » canonique.
- `.agents/tools/skill-template.md` : squelette d'un `SKILL.md`.
- `.agents/skills/ba-sync/scripts/check_uc_links.py` + `test_check_uc_links.py` : contrôle de cohérence (§7).
- `.agents/skills/<nom>/SKILL.md` et `evals/scenarios.md` pour chacun des 9 skills.
- Modifiés/supprimés en fin de plan : `AGENTS.md`, `.agents/rules/ireb-pipeline.md`, 4 skills `ireb-*`.

---

### Tâche 1 : Lint des skills

**Fichiers :**
- Créer : `.agents/tools/lint_skills.py`, `.agents/tools/test_lint_skills.py`

**Interfaces :**
- Produit : `lint_skill(skill_dir: Path, conventions: str, forbidden: list[str]) -> list[str]` (liste de violations, vide si conforme) ; `main(argv: list[str]) -> int` (0 si tout est conforme). CLI : `python3 .agents/tools/lint_skills.py [--skills-dir DIR]`.
- Règles vérifiées : `name` du frontmatter égal au nom du dossier ; `description` non vide et commençant par « À utiliser » ; `SKILL.md` contient `conventions` verbatim, un titre `## Entrées / Sorties`, un titre `## Exemple : information manquante`, un titre `## Pré-requis absents` ; absence de termes interdits ; `evals/scenarios.md` existe avec au moins 3 titres `### Scénario`.
- Termes interdits intégrés : `Claude`, `Gemini`, `Antigravity`, `Codex`, `run_workflow`, `mcp__`, `AskUserQuestion`. Termes interdits supplémentaires lus dans `.agents/tools/forbidden-terms.local.txt` (un par ligne, fichier **ignoré par git**, absent = avertissement sur stderr, pas d'échec).

- [ ] **Étape 1 : écrire les tests qui échouent** (`test_lint_skills.py`, fixtures `tmp_path` construisant un skill conforme minimal)
  - `test_conforming_skill_has_no_violations`
  - `test_name_must_match_folder` : violation contenant `name`
  - `test_description_must_start_with_a_utiliser`
  - `test_missing_conventions_block_is_reported` et `test_modified_conventions_block_is_reported`
  - `test_missing_required_headings_are_reported` (une assertion par titre)
  - `test_forbidden_term_is_reported` (terme intégré et terme passé en paramètre, insensible à la casse)
  - `test_evals_need_three_scenarios`
  - `test_main_returns_1_on_violation_and_0_when_clean`
  - `test_missing_local_forbidden_file_only_warns` (code de retour inchangé, message sur stderr)
- [ ] **Étape 2 : lancer et vérifier l'échec** — `python3 -m pytest .agents/tools/test_lint_skills.py -v` ; attendu : erreurs d'import.
- [ ] **Étape 3 : implémenter `lint_skill` et `main`** dans `lint_skills.py` avec les signatures ci-dessus (lecture du frontmatter par simple analyse de texte, sans dépendance YAML).
- [ ] **Étape 4 : lancer et vérifier le succès** — même commande ; attendu : tous les tests passent.
- [ ] **Étape 5 : ajouter `.agents/tools/forbidden-terms.local.txt` à `.gitignore`**, puis committer (`feat(skills): lint des skills`).

### Tâche 2 : Conventions partagées et gabarit

**Fichiers :**
- Créer : `.agents/tools/conventions.md`, `.agents/tools/skill-template.md`

**Interfaces :**
- Produit : le contenu canonique du bloc `## Conventions` (texte lu par le lint, copié tel quel dans chaque skill) et le squelette d'un skill.
- Le bloc contient, en moins de 40 lignes : les chemins par défaut `<REQ_DIR>` et l'arborescence (contraintes globales) ; les formats d'identifiants ; la syntaxe de provenance et la règle « une hypothèse ne passe pas le DoR » ; la règle « ne jamais inventer : poser une question ou marquer `[hypothèse]` » ; le sens de « invoquer un skill » (lire et appliquer son `SKILL.md`) ; les sections de `index.md` (Domaines, Sources, Travaux en cours) ; le fait que le DoR du projet prime sur le défaut proposé.
- Le gabarit contient, dans l'ordre : frontmatter (`name`, `description` commençant par « À utiliser »), `## Conventions` (marqueur à remplacer par le bloc), `## Pré-requis absents`, `## Entrées / Sorties`, `## Déroulement`, `## Exemple : information manquante`.

- [ ] **Étape 1 : rédiger `conventions.md`** selon les éléments ci-dessus.
- [ ] **Étape 2 : rédiger `skill-template.md`**.
- [ ] **Étape 3 : vérifier que le gabarit, instancié avec un nom et un `evals/scenarios.md` de trois scénarios factices, passe le lint** — créer le skill factice dans un dossier temporaire et lancer `python3 .agents/tools/lint_skills.py --skills-dir <tmp>` ; attendu : code 0.
- [ ] **Étape 4 : commit** (`docs(skills): conventions partagées et gabarit`).

### Tâche 3 : Contrôle cas d'utilisation ↔ Gherkin

**Fichiers :**
- Créer : `.agents/skills/ba-sync/scripts/check_uc_links.py`, `.agents/skills/ba-sync/scripts/test_check_uc_links.py`

**Interfaces :**
- Format d'un cas d'utilisation : fichier `<REQ_DIR>/domains/<domaine>/use-cases/UC-<DOMAINE>-nn.md`, titre `# UC-<DOMAINE>-nn : <titre>`, section `## Scénarios` listant `- <chemin du .feature> :: <nom du scénario>`.
- Format Gherkin : tag `@UC-…` sur la ligne précédant la ligne d'en-tête du scénario ; nom = texte après le premier `:` de cette ligne ; mot-clé quelconque (français ou anglais, plan de scénario inclus). Les tags placés avant `Feature`/`Fonctionnalité` ne comptent pas (hors périmètre, §7 laisse au constructeur).
- Produit : `scan_use_cases(domains_dir: Path) -> dict[str, set[tuple[str, str]]]` (id → ensemble de `(fichier, nom)` listés) ; `scan_feature_tags(roots: list[Path]) -> dict[str, set[tuple[str, str]]]` (id → ensemble de `(fichier, nom)` taggés) ; `check(domains_dir: Path, feature_roots: list[Path]) -> list[Finding]` où `Finding` est un `NamedTuple(kind: str, uc_id: str, detail: str)`.
- Types de constat : `UC_WITHOUT_SCENARIO` (aucun scénario taggé dans le code), `SCENARIO_WITHOUT_UC` (tag sans fichier de cas d'utilisation), `BROKEN_LINK` (scénario listé par le cas d'utilisation introuvable ou sans le tag).
- CLI : `python3 check_uc_links.py --domains DIR --features DIR [DIR…]` ; une ligne par constat `KIND<TAB>UC-ID<TAB>detail` ; code 0 si aucun constat, 1 sinon, 2 si `--domains` n'existe pas (message explicite).

- [ ] **Étape 1 : écrire les tests qui échouent** (fixtures `tmp_path`)
  - `test_clean_repository_has_no_findings`
  - `test_uc_without_tagged_scenario_is_reported`
  - `test_tag_without_use_case_file_is_reported`
  - `test_listed_scenario_missing_in_code_is_a_broken_link`
  - `test_listed_scenario_without_tag_is_a_broken_link`
  - `test_tag_is_case_sensitive` : `@uc-pret-03` produit `SCENARIO_WITHOUT_UC` (point de revue 1)
  - `test_uc_id_with_wrong_digits_is_not_matched` : `@UC-PRET-3` contre `UC-PRET-03` (point de revue 1)
  - `test_french_keywords_and_crlf_and_scenario_outline` : fichier `.feature` en CRLF avec `Plan du scénario :` (point de revue 2)
  - `test_cli_exit_codes` : 0, 1, et 2 pour un dossier `--domains` inexistant
- [ ] **Étape 2 : lancer et vérifier l'échec** — `python3 -m pytest .agents/skills/ba-sync/scripts -v` ; attendu : erreurs d'import.
- [ ] **Étape 3 : implémenter** les trois fonctions et la CLI avec les signatures ci-dessus (expression régulière pour les tags, lecture en `utf-8` avec normalisation des fins de ligne).
- [ ] **Étape 4 : lancer et vérifier le succès**.
- [ ] **Étape 5 : commit** (`feat(skills): contrôle de cohérence cas d'utilisation et Gherkin`).

### Tâche 4 : Skill `ba-entrypoint`

**Fichiers :**
- Créer : `.agents/skills/ba-entrypoint/SKILL.md`, `.agents/skills/ba-entrypoint/evals/scenarios.md`

**Interfaces :**
- Consomme : `<REQ_DIR>/index.md` (sections Domaines, Sources, Travaux en cours), `<REQ_DIR>/work/<epic>/`.
- Produit : une proposition de skill suivant, avec le motif ; aucun fichier.
- Contenu : routage de §8 skill 0 (trois formulations, cas « introuvable » : il le dit et demande), normalisation des identifiants (`US-12`, `US 12`, `us-012` → `US-012`, point de revue 5).

- [ ] **Étape 1 : écrire `evals/scenarios.md`** : scénarios `### Scénario 1` à `4` — « Affinons l'US 12 » (US avec hypothèses : attendu élicitation, interdit spécification directe) ; « J'ai déposé des documents » ; « Affinons le besoin X en US » où X est absent (attendu : enregistrement d'une déclaration, interdit : inventer X) ; **« Information manquante »** : identifiant `US-099` inexistant (attendu : le dit et demande ; interdit : choisir une autre US).
- [ ] **Étape 2 : écrire `SKILL.md`** depuis le gabarit.
- [ ] **Étape 3 : `python3 .agents/tools/lint_skills.py`** ; attendu : conforme.
- [ ] **Étape 4 : commit** (`feat(skills): ba-entrypoint`).

### Tâche 5 : Skill `ba-source-intake`

**Fichiers :** créer `.agents/skills/ba-source-intake/{SKILL.md,evals/scenarios.md}`.

**Interfaces :**
- Consomme : `<REQ_DIR>/inbox/*`, ou une déclaration du BA dans la conversation.
- Produit : `<REQ_DIR>/sources/index.md` (tableau : ID, type `document` ou `déclaration du BA`, date, titre, fichier ou `—`) ; `sources/SRC-nnn-<slug>.<ext>` ; pour une déclaration, `sources/SRC-nnn-declaration.md` (date, texte verbatim, reformulation, mention `[validée <date>]` une fois confirmée) ; création de `<REQ_DIR>/index.md` s'il est absent, ou mise à jour de sa section Sources.
- Contenu : prochain `SRC-nnn` libre (jamais de réutilisation) ; déplacement et non copie depuis `inbox/` ; un document non textuel est enregistré avec la mention « contenu non lu » (point de revue 4).

- [ ] **Étape 1 : scénarios** — deux documents déposés ; déclaration orale ; **information manquante** : pdf illisible (attendu : enregistré, « contenu non lu », aucune citation) ; `inbox/` vide (attendu : le dit).
- [ ] **Étape 2 : `SKILL.md`**. **Étape 3 : lint**. **Étape 4 : commit** (`feat(skills): ba-source-intake`).

### Tâche 6 : Skill `ba-elicitation`

**Fichiers :** créer `.agents/skills/ba-elicitation/{SKILL.md,evals/scenarios.md}`.

**Interfaces :**
- Consomme : sources `SRC-nnn`, `shared/actors.md` s'il existe.
- Produit : `<REQ_DIR>/work/<epic>/elicitation.md` — sections Parties prenantes, Besoins, Questions ouvertes, Hypothèses, Conflits, chaque ligne avec sa provenance.
- Contenu : §8 skill 2 (deux modes : interroger pas à pas, ou analyser des notes et signaler les trous ; perspectives données/fonctions/comportement ; repérage des exigences orientées solution ; techniques par moment).

- [ ] **Étape 1 : scénarios** — mode interview ; mode notes avec trou de parties prenantes (exploitation absente) ; exigence orientée solution (« je veux un fichier Excel ») ; **information manquante** : notes sans acteur identifié (attendu : question, pas d'acteur inventé).
- [ ] **Étapes 2 à 4 : `SKILL.md`, lint, commit** (`feat(skills): ba-elicitation`).

### Tâche 7 : Skill `ba-as-is-reading`

**Fichiers :** créer `.agents/skills/ba-as-is-reading/{SKILL.md,evals/scenarios.md}`.

**Interfaces :**
- Consomme : code du dépôt, zone désignée par le BA.
- Produit : mises à jour de `domains/<domaine>/{use-cases/,rules.md,objects.md}`, tous les faits marqués `[hypothèse]` avec chemin et lignes du code ; cas d'utilisation créés avec le titre et la section `## Scénarios` vide.
- Contenu : §8 skill 3 ; limite à la zone concernée ; jamais de `[validée]` sans réponse du BA.

- [ ] **Étape 1 : scénarios** — zone claire ; zone trop large (attendu : demande de la restreindre) ; **information manquante** : règle métier supposée par le code mais non vérifiable (attendu : `[hypothèse]` et question).
- [ ] **Étapes 2 à 4 :** `SKILL.md`, lint, commit (`feat(skills): ba-as-is-reading`).

### Tâche 8 : Skill `ba-framing`

**Fichiers :** créer `.agents/skills/ba-framing/{SKILL.md,evals/scenarios.md}`.

**Interfaces :**
- Produit : `<REQ_DIR>/work/<epic>/framing.md` (problème, Lean Canvas à cases vides autorisées, arbre des buts avec `GOAL-nn` reliés à leurs sources, hypothèses risquées, grille de maturité) ; contenu MVD d'idéation dans `shared/vision.md`, `shared/context.md` (diagramme Mermaid), `shared/actors.md`, `shared/glossary.md`.
- Contenu : grille de §9 (cinq axes, quatre niveaux nommés dans le skill, axe « solution » inversé, sous-axes en questions non notées) ; migration du contenu utile de `ireb-problem-analysis` et `ireb-domain-modeling` (réécrit avec les règles de ce plan, sans reprise mot à mot).

- [ ] **Étape 1 : scénarios** — demande avec solution déjà décrite (attendu : alerte sur l'axe solution) ; cadrage avec cases vides ; **information manquante** : aucun objectif exprimé (attendu : question, aucun `GOAL` inventé).
- [ ] **Étapes 2 à 4 :** `SKILL.md`, lint, commit (`feat(skills): ba-framing`).

### Tâche 9 : Skill `ba-backlog`

**Fichiers :** créer `.agents/skills/ba-backlog/{SKILL.md,evals/scenarios.md}`.

**Interfaces :**
- Consomme : `framing.md`, `elicitation.md`, `shared/glossary.md`.
- Produit : `<REQ_DIR>/work/<epic>/backlog.md` : Epics, Features (facultatives), US `US-nnn` avec domaine(s), `GOAL-nn`, sources, priorité MoSCoW et critère ; mise à jour de la section Travaux en cours de `index.md`.
- Contenu : story mapping, critères de priorisation explicites, prochain `US-nnn` libre calculé sur tout `work/` (point de revue 5), US multi-domaines signalées, niveau Feature selon §8 skill 5.

- [ ] **Étape 1 : scénarios** — création d'un backlog ; ajout d'une US quand `US-007` existe déjà dans un autre Epic (attendu : `US-008`) ; US touchant deux domaines ; **information manquante** : pas de `GOAL` pour une US (attendu : question).
- [ ] **Étapes 2 à 4 :** `SKILL.md`, lint, commit (`feat(skills): ba-backlog`).

### Tâche 10 : Skill `ba-story-spec`

**Fichiers :** créer `.agents/skills/ba-story-spec/{SKILL.md,evals/scenarios.md}`.

**Interfaces :**
- Consomme : une US de `backlog.md`.
- Produit : `<REQ_DIR>/work/<epic>/US-nnn.md` : état des lieux (hypothèses, points ouverts, écart au DoR), récit, exigences, scénarios Gherkin avec le tag `@UC-…` prévu, exigences non fonctionnelles, DoR coché seulement après vérification.
- Contenu : état des lieux d'abord (§8 skill 6) ; DoR du projet s'il existe, sinon liste par défaut non obligatoire ; Gherkin nominal et erreurs ; le cas d'utilisation visé est nommé ou à créer.

- [ ] **Étape 1 : scénarios** — US prête ; US avec hypothèses (attendu : questions avant toute spécification) ; **information manquante** : règle de gestion absente (attendu : point ouvert, pas de scénario inventé) ; DoR du projet présent (attendu : il prime).
- [ ] **Étapes 2 à 4 :** `SKILL.md`, lint, commit (`feat(skills): ba-story-spec`).

### Tâche 11 : Skill `ba-quality-review`

**Fichiers :** créer `.agents/skills/ba-quality-review/{SKILL.md,evals/scenarios.md}`.

**Interfaces :**
- Consomme : une US ou l'ensemble d'un Epic, `shared/glossary.md`.
- Produit : liste de constats dans `work/<epic>/open-questions.md`, chacun avec la citation, le critère enfreint et une question au BA ; aucune modification de l'US.
- Contenu : critères de §8 skill 7 (mots flous, quantificateurs, passif, termes non définis, complétude, cohérence entre US, vérifiabilité, glossaire).

- [ ] **Étape 1 : scénarios** — US avec « rapidement » et « etc. » ; deux US contradictoires ; **information manquante** : terme absent du glossaire (attendu : question, pas de définition inventée) ; US correcte (attendu : « aucun constat », pas de défaut fabriqué).
- [ ] **Étapes 2 à 4 :** `SKILL.md`, lint, commit (`feat(skills): ba-quality-review`).

### Tâche 12 : Skill `ba-sync`

**Fichiers :** créer `.agents/skills/ba-sync/{SKILL.md,evals/scenarios.md}` (le dossier `scripts/` existe déjà, Tâche 3).

**Interfaces :**
- Consomme : l'US spécifiée, le cas d'utilisation visé, le code produit ; `scripts/check_uc_links.py` (CLI de la Tâche 3).
- Produit : cas d'utilisation et documentation du domaine à jour ; `.feature` déplacés dans le code avec leurs tags `@UC-…` ; sortie du script collée dans le compte rendu.
- Contenu : première étape « détecter la convention du projet » (outil de test, emplacement des `.feature`, à défaut demander) ; un écart entre le code et l'US est signalé, jamais tranché ; « terminé » = script à 0 constat ; le BA relit dans la revue du changement ; langue Gherkin déclarée dans le fichier.

- [ ] **Étape 1 : scénarios** — mise à jour nominale ; **information manquante** : aucune convention de test détectée (attendu : question au développeur) ; écart code/US (attendu : signalé, US non « corrigée » à la place du BA) ; script en échec (attendu : « non terminé »).
- [ ] **Étapes 2 à 4 :** `SKILL.md`, lint, commit (`feat(skills): ba-sync`).

### Tâche 13 : Remplacement du brouillon et points d'entrée

**Fichiers :**
- Modifier : `AGENTS.md`
- Supprimer : `.agents/rules/ireb-pipeline.md`, `.agents/skills/ireb-{problem-analysis,domain-modeling,backlog-builder,story-refinement}/`

- [ ] **Étape 1 : vérifier** que chaque contenu utile des anciens skills figure dans `ba-framing`, `ba-backlog` ou `ba-story-spec` (comparaison de leurs listes de techniques) ; combler tout manque avant suppression.
- [ ] **Étape 2 : réécrire `AGENTS.md`** : renvoi vers `ba-entrypoint`, définition de `<REQ_DIR>` pour ce projet, mention que le DoR de l'équipe s'il existe prime. Supprimer la règle de pipeline et les 4 anciens skills.
- [ ] **Étape 3 : `python3 .agents/tools/lint_skills.py`** (9 skills conformes) puis `python3 -m pytest .agents -v` ; attendu : tout passe.
- [ ] **Étape 4 : commit** (`refactor(skills): remplacement du brouillon ireb par la bibliothèque ba-*`).

### Tâche 14 : Banc d'essai biblioCQRS

**Fichiers :** modifier `docs/requirements/` (migration), créer `docs/requirements/{index.md,sources/,domains/,shared/}` et `docs/superpowers/plans/2026-10-29-ba-skills-validation.md` (compte rendu).

- [ ] **Étape 1 : migrer les cinq documents existants** (`01_vision_contexte.md` à `05_backlog.md`) en les déposant dans `inbox/` puis en suivant `ba-source-intake` (ils deviennent `SRC-001` à `SRC-005`) ; reporter leurs faits dans `domains/` et `shared/` avec `[hypothèse]` partout.
- [ ] **Étape 2 : demander à l'utilisateur une vraie demande**, puis dérouler le parcours complet : `ba-entrypoint` → intake → élicitation → lecture de l'existant → cadrage → backlog → spécification → revue ; une phrase d'entrée par formulation du §8 skill 0.
- [ ] **Étape 3 : vérifier les critères d'acceptation** (§12, §13) : aucune information sans provenance ; au moins un cas d'information manquante traité par une question ou une hypothèse ; la revue n'a rien réécrit ; `ba-sync` sur une US implémentée laisse `check_uc_links.py` à 0 constat.
- [ ] **Étape 4 : consigner dans le compte rendu** chaque écart constaté et corriger les skills concernés (une tâche de correction par skill, relancer `lint_skills.py`).
- [ ] **Étape 5 : commit** (`docs(requirements): migration de biblioCQRS et validation des skills`).
