# 4. Plan d'Élicitation & Incohérences

Dans le cadre de l'ingénierie des exigences, notre prise de connaissance partielle révèle des zones d'ombre nécessitant
des ateliers complémentaires.

## 4.1 Incohérences / Points à creuser

1. **Gestion des retours :** L'emprunt est défini, mais comment l'usager rend-il le livre ? Que se passe-t-il s'il le
   rend en retard (pénalités financières, suspension de l'abonnement) ? Comment lève-t-on le statut "en retard" ?
2. **Gestion fine de l'Adhérent :** Quelles sont les informations indispensables à sa création (Email pour les
   relances ? Date d'expiration de l'abonnement ?)
3. **Cas de l'exemplaire indisponible :** Que se passe-t-il si le système tente d'enregistrer l'emprunt d'un exemplaire
   qui est déjà informatiquement "emprunté" par quelqu'un d'autre ?
4. **Identification des Exemplaires :** Actuellement, les exemplaires n'ont pas d'ID explicite dans notre système.
   Comment le documentaliste peut-il cibler un exemplaire précis lors de l'emprunt (ex: scan d'un code-barre ou tag
   RFID) ?

## 4.2 Ateliers prévus

* **Atelier "Cycle de vie du Retour et Litiges"** : Modéliser le retour d'un ouvrage, la perte, la dégradation.
* **Atelier "Cycle de vie de l'Adhérent"** : Inscription, renouvellement, radiation, RGPD.
