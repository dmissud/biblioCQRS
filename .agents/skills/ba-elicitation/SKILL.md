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
Un identifiant mal saisi est une erreur à signaler, jamais une correspondance approximative.

Provenance : toute information consignée porte l'un de ces marqueurs, en ligne.
- `[source SRC-003 « passage cité »]` : citée d'une source, avec son passage
- `[validée 2026-10-29]` : reformulée puis confirmée par le BA, avec la date
- `[hypothèse]` : déduite, non confirmée. Une hypothèse ne passe pas le DoR d'une US.
Une déclaration du BA dans la conversation est une source : l'enregistrer comme `SRC-nnn` avec la date et le texte.

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
- Sorties : `<REQ_DIR>/work/<epic>/elicitation.md`, intitulé `# Élicitation : <epic>`, avec exactement les sections `## Parties prenantes`, `## Besoins`, `## Questions ouvertes`, `## Hypothèses`, `## Conflits`. Chaque ligne de liste commence par `- ` et se termine par un marqueur de provenance. Une section sans contenu contient `- (aucun pour l'instant)`.

## Déroulement

1. Vérifier `## Pré-requis absents`. Obtenir le nom de l'epic. Si `work/<epic>/elicitation.md` existe, le lire et le compléter sans supprimer ni réécrire ses lignes ; une ligne déjà présente n'est pas ajoutée une seconde fois.
2. Lire les sources concernées et `shared/actors.md`. Ne retenir que ce qui y figure vraiment, avec le passage cité.
3. Choisir le mode et le dire au BA : mode 1 (interview) s'il n'a pas de notes ; mode 2 (analyse de notes) s'il en fournit. En cas de doute, demander.
4. Choisir la technique selon la situation et la proposer au BA, qui décide :
   - interview : peu de personnes, sujet flou ou sensible ;
   - atelier : plusieurs parties prenantes en désaccord ou à aligner ;
   - analyse de documents : des sources existent déjà ;
   - lecture du code : évolution d'un système existant (dans ce cas, renvoyer vers la lecture de l'existant) ;
   - observation : gestes réels qui diffèrent de ce que l'on en dit.
5. Parties prenantes. Poser, une question à la fois en mode 1 ; en mode 2 vérifier dans les notes : qui utilise (utilisateurs cibles) ? qui administre ? qui exploite (supervision, sauvegarde, support) ? qui décide ou paie ? qui subit le changement ? qui fournit ou reçoit des données ? Pour chaque catégorie sans réponse, écrire la question dans `## Questions ouvertes` : « partie prenante absente de la demande : exploitation ? ». Ne pas inventer de personne ni de rôle. Chaque partie prenante est consignée `- <rôle> : <intérêt> <marqueur>`.
6. Les trois perspectives. Pour chacune, poser au moins une question et consigner les réponses dans `## Besoins` en préfixant la ligne de `[données]`, `[fonctions]` ou `[comportement]` :
   - données : quelles informations sont manipulées, d'où viennent-elles, combien de temps vivent-elles ?
   - fonctions : que doit pouvoir faire chaque acteur ?
   - comportement : que se passe-t-il selon les états, les erreurs, les délais, les cas limites ?
   Une perspective qui n'a reçu aucune réponse est notée dans `## Questions ouvertes`.
7. Non-dits, hypothèses, conflits. Demander : « qu'est-ce qui vous semble évident mais n'est écrit nulle part ? », « qu'est-ce qui est supposé vrai sans l'avoir vérifié ? », « qui voudrait autre chose ? ». Les suppositions vont dans `## Hypothèses` (`[hypothèse]`), les désaccords dans `## Conflits` (qui veut quoi, source de chacun), les trous dans `## Questions ouvertes`.
8. Exigences orientées solution. Une phrase qui impose un moyen (« je veux un fichier Excel », « un bouton rouge », « une table de plus ») est consignée telle quelle dans `## Besoins`, marquée `[orientée solution]`, puis creusée avec le BA :
   - objectif : qu'essayez-vous d'obtenir avec cela ?
   - moyen : pourquoi ce moyen plutôt qu'un autre ?
   - mesure : comment saurez-vous que c'est réussi ?
   - risque : que se passe-t-il si on ne le fait pas, ou si on le fait mal ?
   Si la réponse n'est pas claire, enchaîner jusqu'à 5 « pourquoi » successifs, puis les 5 W (qui, quoi, où, quand, pourquoi). Le besoin sous-jacent est ajouté en `[hypothèse]` jusqu'à confirmation ; la formulation d'origine reste en place.
9. Mode 2 uniquement : lire les notes, consigner chaque affirmation avec `[source SRC-nnn « passage »]` (la déclaration est enregistrée via `ba-source-intake` si les notes ne sont pas encore une source ; demander au BA de le faire ou son accord), puis lister les trous : acteur non identifié, perspective manquante, partie prenante absente, terme flou, solution imposée. Un trou est une ligne de `## Questions ouvertes`, jamais un contenu inventé.
10. Marqueurs : ce que le BA vient d'affirmer dans la conversation est reformulé et reste `[hypothèse]` jusqu'à sa confirmation explicite. Présenter au BA les lignes à valider ; seulement après sa réponse « oui » ligne par ligne ou en bloc, remplacer par `[validée <date du jour>]`. Une reformulation corrigée par le BA redevient `[hypothèse]` jusqu'à sa nouvelle confirmation.
11. Écrire `work/<epic>/elicitation.md` (créer le dossier au besoin). Relire : aucune ligne sans marqueur, aucun acteur ni besoin qui ne vienne ni d'une source ni du BA.
12. Clore en annonçant : le chemin du fichier, le nombre de lignes par section, la liste numérotée des questions ouvertes et des lignes `[hypothèse]` à confirmer. Proposer ensuite le cadrage du besoin (ou le skill que le BA préfère) ; le BA confirme avant tout enchaînement.

## Exemple : information manquante

Notes du BA (SRC-004) : « Il faut pouvoir relancer les lecteurs en retard. » Aucun acteur n'est nommé, `shared/actors.md` est absent.

Le skill consigne le besoin avec `[source SRC-004 « relancer les lecteurs en retard »]` et pose : « Qui déclenche la relance, et qui la reçoit : un bibliothécaire, un système, le lecteur ? » Dans `## Questions ouvertes` : `- acteur qui déclenche la relance non identifié [hypothèse]`. Aucun acteur n'est écrit dans `## Parties prenantes`.
