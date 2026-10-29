# 5. Backlog et User Stories

Ce tableau liste les User Stories priorisées pour l'implémentation.

| ID            | Epic      | En tant que... | Je veux...                                                                                    | Afin de...                                                                   | Statut  |
|---------------|-----------|----------------|-----------------------------------------------------------------------------------------------|------------------------------------------------------------------------------|---------|
| **US-CAT-01** | Catalogue | Bibliothécaire | Ajouter une référence abstraite (ISBN, Titre, Auteur)                                         | Préparer le catalogue pour recevoir des exemplaires                          | À faire |
| **US-CAT-02** | Catalogue | Bibliothécaire | Ajouter un exemplaire physique pour un ouvrage donné                                          | L'intégrer au stock empruntable                                              | À faire |
| **US-CAT-03** | Catalogue | Bibliothécaire | Saisir l'identifiant unique (code-barre/RFID) lors de l'ajout d'un exemplaire                 | Permettre sa traçabilité individuelle physique                               | À faire |
| **US-ADH-01** | Adhérents | Documentaliste | Enregistrer un nouvel adhérent dans le système                                                | Lui permettre de commencer à emprunter                                       | À faire |
| **US-PRT-01** | Prêts     | Documentaliste | Enregistrer un emprunt nominal d'un exemplaire à un adhérent                                  | Lui confier l'ouvrage pour 3 semaines (géré par le système)                  | À faire |
| **US-PRT-02** | Prêts     | Documentaliste | Être bloqué si je tente d'enregistrer un emprunt pour un adhérent ayant atteint son quota (5) | Garantir que tous les adhérents puissent accéder à un choix d'ouvrages riche | À faire |
| **US-PRT-03** | Prêts     | Documentaliste | Être bloqué si je tente d'enregistrer un emprunt pour un adhérent avec > 1 livre en retard    | Favoriser le retour rapide des exemplaires en circulation                    | À faire |
| **TS-TECH-01**| Technique | Développeur    | Séparer physiquement les bases de données Command (Event Store) et Query (Read Models)        | Respecter l'isolation stricte de CQRS et permettre une scalabilité indépendante| À faire |
