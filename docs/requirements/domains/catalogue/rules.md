# Règles métier : catalogue

## Règles

- Le système garantit l'unicité stricte des ouvrages référencés (document : SRC-003, 3.3 Scénarios : Référencement de livre) [hypothèse]
- Un exemplaire physique est rattaché à un ouvrage (document : SRC-003, 3.3 Scénarios : Référencement de livre) [hypothèse]
- Ajouter un exemplaire physique requiert un ouvrage référencé (« require » vers « Référencer un nouvel ouvrage abstrait ») (document : SRC-003, 3.2 Cas d'Utilisation Opérationnels (Use Cases)) [hypothèse]

## Questions ouvertes

- Que fait le système quand un ouvrage dont l'ISBN existe déjà est déclaré ? L'unicité est affirmée, pas son traitement (vu dans SRC-003, 3.3 Scénarios : Référencement de livre)
- Ces comportements sont-ils déjà réalisés ou à construire ? Toutes les US du catalogue sont au statut « À faire » (vu dans SRC-005, 5. Backlog et User Stories)
- « Identifier un exemplaire par ID » (UC-CATALOGUE-03) est-il un cas d'utilisation distinct de l'ajout d'un exemplaire (UC-CATALOGUE-02) ? Il est absent du diagramme des cas d'utilisation (vu dans SRC-003, 3.2 Cas d'Utilisation Opérationnels (Use Cases))
