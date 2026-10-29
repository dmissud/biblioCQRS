# Instructions pour les agents IA

## Analyse fonctionnelle : bibliothèque de skills `ba-*`

Pour toute demande de création, d'évolution ou d'analyse fonctionnelle, commencer par le skill `ba-entrypoint` :
lire `.agents/skills/ba-entrypoint/SKILL.md` et l'appliquer. Il lit l'état du travail et propose le skill suivant.
« Invoquer un skill » signifie lire et appliquer son `SKILL.md` (`.agents/skills/<nom>/SKILL.md`), ou utiliser le
mécanisme de skills natif de l'outil.

Les skills :

- `ba-entrypoint` : choisit le bon skill à partir de la demande et de l'état du travail ; ne produit aucun fichier.
- `ba-source-intake` : range les documents de `inbox/` dans `sources/` (`SRC-nnn`) et enregistre les déclarations du BA.
- `ba-elicitation` : identifie parties prenantes, besoins, hypothèses, conflits et questions ouvertes.
- `ba-as-is-reading` : lit une zone de code existante et en extrait des faits référencés, à confirmer par le BA.
- `ba-framing` : cadre le problème, la vision, les objectifs (`GOAL-nn`), le Lean Canvas et la grille de maturité.
- `ba-backlog` : découpe en Epics et US (story mapping, INVEST, MoSCoW, traçabilité).
- `ba-story-spec` : spécifie une US (exigences, scénarios Gherkin, exigences non fonctionnelles, DoR).
- `ba-quality-review` : contrôle la qualité des US et écrit les questions au BA.
- `ba-sync` : synchronise la documentation avec le code après implémentation d'une US.

## Définitions pour ce projet

- `<REQ_DIR>` = `docs/requirements`. Les documents bruts se déposent dans `<REQ_DIR>/inbox/`.
- Le DoR de l'équipe, s'il existe, prime sur le DoR par défaut des skills.
- L'agent du développeur utilise `ba-sync` quand il implémente une US.

## Contrôles

- Lint des skills : `python3 .agents/tools/lint_skills.py`
- Tests : `python3 -m pytest .agents`
