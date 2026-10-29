# Règles métier : prets

## Règles

- Avant d'enregistrer un emprunt, le système vérifie que l'adhérent a moins de 5 emprunts en cours et au maximum 1 seul retard (document : SRC-003, 3.4 Scénarios : Emprunt d'un exemplaire) [hypothèse]
- Quota atteint : si l'adhérent a déjà 5 emprunts actifs, le système refuse l'emprunt (document : SRC-003, 3.4 Scénarios : Emprunt d'un exemplaire) [hypothèse]
- Trop de retards : si l'adhérent a strictement plus de 1 livre en retard (2 ou plus), le système refuse l'emprunt (document : SRC-003, 3.4 Scénarios : Emprunt d'un exemplaire) [hypothèse]
- Le système fixe la date de retour maximale à +3 semaines (document : SRC-003, 3.4 Scénarios : Emprunt d'un exemplaire) [hypothèse]
- Le refus lié au quota vise à garantir que tous les adhérents puissent accéder à un choix d'ouvrages riche (document : SRC-005, 5. Backlog et User Stories, US-PRT-02) [hypothèse]
- Le refus lié aux retards vise à favoriser le retour rapide des exemplaires en circulation (document : SRC-005, 5. Backlog et User Stories, US-PRT-03) [hypothèse]
- Enregistrer un emprunt requiert un adhérent inscrit et un exemplaire ajouté au catalogue (document : SRC-003, 3.2 Cas d'Utilisation Opérationnels (Use Cases)) [hypothèse]

## Questions ouvertes

- Que se passe-t-il si l'emprunt d'un exemplaire déjà informatiquement « emprunté » par quelqu'un d'autre est enregistré ? (vu dans SRC-004, 4.1 Incohérences / Points à creuser)
- Comment l'usager rend-il le livre, que se passe-t-il s'il le rend en retard (pénalités financières, suspension de l'abonnement) et comment lève-t-on le statut « en retard » ? (vu dans SRC-004, 4.1 Incohérences / Points à creuser)
- À partir de quand un emprunt est-il en retard, et le retard se compte-t-il par emprunt ou par livre ? SRC-003 emploie « 1 seul retard » et « 1 livre en retard » (vu dans SRC-003, 3.4 Scénarios : Emprunt d'un exemplaire)
- Ces comportements sont-ils déjà réalisés ou à construire ? Toutes les US de prêts sont au statut « À faire » (vu dans SRC-005, 5. Backlog et User Stories)
- « Enregistrer un retour » est listé comme cas d'utilisation mais aucun scénario ni US ne le décrit (UC-PRETS-02) (vu dans SRC-003, 3.1 Arbre des Objectifs)
