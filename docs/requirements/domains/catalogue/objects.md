# Objets métier : catalogue

## Objets

- Ouvrage (Work/Book) : référence abstraite de livre, caractérisée par son ISBN (identifiant unique global), son titre et son auteur (document : SRC-002, 2. Modèle du Domaine (Glossaire)) [hypothèse]
- Exemplaire (Copy/Item) : entité physique concrète (document : SRC-002, 2. Modèle du Domaine (Glossaire)) [hypothèse]
- Lieu de Stockage (Location) : localisation physique (Salle, Étagère, Position) (document : SRC-002, 2. Modèle du Domaine (Glossaire)) [hypothèse]
- L'ouvrage est déclaré de façon abstraite avec ISBN, titre et auteur (document : SRC-003, 3.3 Scénarios : Référencement de livre) [hypothèse]
- L'identifiant unique d'un exemplaire (code-barre ou RFID) est saisi lors de son ajout, pour sa traçabilité individuelle physique (document : SRC-005, 5. Backlog et User Stories, US-CAT-03) [hypothèse]

## Cycle de vie

- Un ouvrage est référencé d'abord ; des exemplaires physiques lui sont ajoutés ensuite (document : SRC-003, 3.3 Scénarios : Référencement de livre) [hypothèse]
- Un exemplaire ajouté est intégré au stock empruntable (document : SRC-005, 5. Backlog et User Stories, US-CAT-02) [hypothèse]

## Questions ouvertes

- Les exemplaires ont-ils un identifiant explicite ? SRC-004 dit qu'ils n'en ont pas, SRC-003 (emprunt) et SRC-005 (US-CAT-03) en supposent un (vu dans SRC-004, 4.1 Incohérences / Points à creuser)
- Quel est le rôle du Lieu de Stockage ? Il est défini et cité dans la vision (« localiser avec précision »), mais aucun scénario ni US ne le saisit (vu dans SRC-002, 2. Modèle du Domaine (Glossaire))
- Perte et dégradation d'un exemplaire : quel effet sur son cycle de vie ? L'atelier « Retour et Litiges » est prévu (vu dans SRC-004, 4.2 Ateliers prévus)
