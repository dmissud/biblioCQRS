Feature: Référencer un nouvel ouvrage
  Afin de rendre un livre disponible dans le catalogue
  En tant que bibliothécaire
  Je veux pouvoir référencer un nouvel ouvrage

  Scenario: Référencer un ouvrage abstrait
    Given qu'aucun ouvrage avec l'ISBN "978-2-07-036822-8" n'existe
    When le bibliothécaire référence l'ouvrage avec l'ISBN "978-2-07-036822-8", le titre "1984" et l'auteur "George Orwell"
    Then l'ouvrage est ajouté au catalogue avec succès

  Scenario: Refuser un ouvrage déjà existant
    Given que l'ouvrage avec l'ISBN "978-2-07-036822-8" existe déjà dans le catalogue
    When le bibliothécaire tente de référencer à nouveau l'ouvrage avec l'ISBN "978-2-07-036822-8", le titre "1984" et l'auteur "George Orwell"
    Then le référencement est refusé pour cause de doublon

  Scenario: Ajouter un exemplaire physique à un ouvrage existant
    Given que l'ouvrage "1984" avec l'ISBN "978-2-07-036822-8" existe dans le catalogue
    When le bibliothécaire ajoute un exemplaire identifié par "CBR-001" en "Salle A", "Étagère 2", "Position 15" à l'ouvrage "978-2-07-036822-8"
    Then un nouvel exemplaire identifié par "CBR-001" est enregistré pour cet ouvrage
    And il est assigné à la "Salle A", "Étagère 2", "Position 15"
