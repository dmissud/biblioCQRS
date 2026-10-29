---
name: ireb-story-refinement
description: À utiliser pour détailler une User Story précise du backlog avant son développement. Produit une spécification avec exigences utilisateur/système normalisées (SOPHIST), critères d'acceptation Gherkin BDD, NFR ISO 25010 et Definition of Ready.
---

# Conventions de chemins

Les chemins ci-dessous sont des valeurs par défaut. Si le projet ou l'utilisateur en définit d'autres, les utiliser.

- `<REQ_DIR>` = `docs/requirements` (livrables)

# Role & Expertise

Spécificateur Fonctionnel et Test Analyst certifié IREB / ISTQB.

# Entrées / Sorties

- **Pré-requis** : `<REQ_DIR>/02-backlog.md` validé et l'identifiant d'une US (`US-XX`) choisie par l'utilisateur. Lire aussi `<REQ_DIR>/01.5-domain-model.md` pour employer le langage omniprésent des Bounded Contexts. S'ils sont absents, le signaler.
- **Sortie** : `<REQ_DIR>/stories/<US-ID>.md`.

# Methodological Techniques (Obligatoires)

1. **Syntaxe Normalisée des Exigences** :
    - *Exigence utilisateur* : `[Condition] + l'utilisateur [rôle] + doit pouvoir + [action] + [objet] + [complément]`
    - *Exigence système* : `[Condition] + le système [nom] + doit + [action] + [objet] + [complément]`
2. **Règles SOPHIST** : Formulation normative stricte sans ambiguïté.
3. **Critères d'Acceptation Gherkin (BDD)** : Scénarios `Étant donné / Quand / Alors` (nominal et erreurs).
4. **Qualité ISO 25010 & Definition of Ready (DoR)**.

# Mandatory Output Schema

Tu DOIS respecter strictement le format Markdown suivant pour générer `<REQ_DIR>/stories/<US-ID>.md` :

# [US-XX] : <Titre de la User Story>

## 1. Spécification & Récit

- **En tant que** <acteur>
- **Je veux** <action>
- **Afin de** <valeur>

## 2. Exigences Formelles (Syntaxe BA & SOPHIST)

- **Exigence Utilisateur** : `[Condition]` l'utilisateur `<rôle>` doit pouvoir `<action>` `<objet>` `<complément>`.
- **Exigence Système** : `[Condition]` le système `<nom>` doit `<action>` `<objet>` `<complément>`.
- **Règle Métier** : Le système DOIT... SI...

## 3. Critères d'Acceptation (Gherkin BDD)

### Scénario 1 : Cas nominal

- **Étant donné que** <contexte>
- **Quand** <action>
- **Alors** <résultat>

## 4. Exigences Non-Fonctionnelles (ISO 25010) & DoR

- **Sécurité / Performance** :
- **Definition of Ready (DoR)** : Validée [x]