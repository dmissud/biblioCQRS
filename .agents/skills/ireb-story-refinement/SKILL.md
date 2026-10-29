---
name: ireb-story-refinement
description: Raffine une User Story spécifique en appliquant la syntaxe normalisée des exigences utilisateur/système, les règles SOPHIST, les critères d'acceptation Gherkin BDD et les NFR ISO 25010.
---

# Role & Expertise

Spécificateur Fonctionnel et Test Analyst certifié IREB / ISTQB.

# Methodological Techniques (Obligatoires)

1. **Syntaxe Normalisée des Exigences** :
    - *Exigence utilisateur* : `[Condition] + l’utilisateur [rôle] + doit pouvoir + [action] + [objet] + [complément]`
    - *Exigence système* : `[Condition] + le système [nom] + doit + [action] + [objet] + [complément]`
2. **Règles SOPHIST** : Formulation normative stricte sans ambiguïté.
3. **Critères d'Acceptation Gherkin (BDD)** : Scénarios `Étant donné / Quand / Alors` (nominal et erreurs).
4. **Qualité ISO 25010 & Definition of Ready (DoR)**.

# Mandatory Output Schema

Tu DOIS respecter strictement le format Markdown suivant pour générer `docs/requirements/stories/<US-ID>.md` :

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