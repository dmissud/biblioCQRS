# Interfaces et Topics Kafka

Dans une approche CQRS, le point d'entrée pour la modification de l'état (Command) se fait via des API REST synchrones,
mais la propagation de ces changements d'état vers les modèles de lecture (Query) se fait de manière asynchrone via
Kafka.

## 1. Interfaces API (Command)

Le module `biblio-command-infra` expose des contrôleurs REST. Les commandes sont validées par le domaine et acceptées (
retour asynchrone symbolique `202 Accepted`).

### API Ouvrages (`OuvrageCommandController`)

* **`POST /api/ouvrages`**
    * **Description** : Référence un nouvel ouvrage dans le catalogue.
    * **Body** : `{"isbn": "...", "titre": "...", "auteur": "..."}`
    * **Domaine** : Interagit avec l'agrégat `Ouvrage`.

* **`POST /api/ouvrages/{isbn}/exemplaires`**
    * **Description** : Ajoute un exemplaire physique à un ouvrage existant.
    * **Body** : `{"salle": "...", "etagere": "...", "position": "..."}`
    * **Domaine** : Interagit avec l'agrégat `Ouvrage`.

## 2. Registre des Topics Kafka

Les événements issus de la couche commande (`Domain Events`) sont interceptés par l'adaptateur de persistance (
`OuvrageRepositoryAdapter`) et sérialisés pour être publiés sur Kafka. Ces événements seront consommés ultérieurement
par les modules de requête (`biblio-query-infra`).

### Topic : `biblio.ouvrages.events`

Ce topic est dédié au cycle de vie de l'agrégat `Ouvrage` et de ses composants internes (`Exemplaire`).

#### `OuvrageReferenceEvent`

* **Description** : Émis lorsqu'un nouvel ouvrage est référencé par le bibliothécaire.
* **Payload (JSON attendu)** :
  ```json
  {
    "eventType": "OuvrageReferenceEvent",
    "isbn": "978-X-XXXX-XXXX-X",
    "titre": "Titre du livre",
    "auteur": "Nom de l'auteur"
  }
  ```

#### `ExemplaireAjouteEvent`

* **Description** : Émis lorsqu'un exemplaire physique est associé à un ouvrage dans la bibliothèque.
* **Payload (JSON attendu)** :
  ```json
  {
    "eventType": "ExemplaireAjouteEvent",
    "isbn": "978-X-XXXX-XXXX-X",
    "salle": "Salle 1",
    "etagere": "Rayon A",
    "position": "Etagère 3"
  }
  ```
