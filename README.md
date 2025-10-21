## Contexte
Je suis une entreprise de réservation de vols déjà fonctionnel mais qui voudrait ajouter aux clientes la possibilité d’accumuler des points par distance voyagé. Selon le type de passager (First Class, Business Class ou Economy Class), la distance accumulé sera multiplié respectivement comme, 2x, 1.5x et 1x.

## Objectif technique 
Fonctionnement actuel du code :
1)	Comme agent de l’entreprise, on vous demande d’abord dans quel vol le passager désire y être :
      a.	En choisissant le vol, il vient aussi de choisir le type d’avion et la quantité de place disponible par type de passager (First, Business ou Economy Class)
2)	Une fois le vol identifié, on va demander les éléments nécessaires pour construire un Object Passenger :
      a.	Son passeport
      b.	Son nom
      c.	Son âge
      d.	Quel type de passager il est? First class, business class ou economy class
3)	Pour ajouter le passager au vol, un service va valider la quantité de place disponible par type de passager et va proposer une logique d’ajustement de type de passager selon les disponibilités du vol, alors :
      a.	S’il s’agit d’un passager du type First Class, mais qu’il n’y a plus de place First Class, la logique va déplacer ce passager en Business Class
      b.	De même, s’il n’y a plus de place Business Class dans ce vol, la logique va déplacer le passager en Economy Class
      c.	Le moment que le vol est complet, un message avisera les passagers
4)	La liste de passagers par vol sera disponible à la fin dans le fichier à la racine qui s’appelle : passengerData.csv
5)  Distance:
      a.    Si le passager est du type FIRST CLASS, un factor multiplicateur sera ajouter pour donner 2x la distance du vol
      b.    Si le passager est du type BUSINESS CLASS, un factor multiplicateur sera ajouter pour donner 1.5x la distance du vol
      c.    Si le passager est du type ECONOMY CLASS, le factor sera de 1x la distance du vol

Ces étapes expliquent le fonctionnement du code et vous aide à en ajouter la quantité maximale de tests unitaires + les tests d'intégration, pour valider sa qualité et, au maximum possible, garantir aux usagers son fonctionnement. 
En cas de doute, vous pouvez aussi exécuter le code et suivre les étapes demandées à la console, comme une agente de voyage le ferait.

## Tâches
1)	Ajouter les tests unitaires en TDD pour calculer la distance en ajoutant le factor multiplicateur selon le type de passager
2)	Implementer les tests d'intégration déjà sélectioné
3)	Ajouter par vous mêmes, des scenarios de tests d'intégration pour faire des validations du code existant
4)	Après avoir rédigé les tests d'erreur, corrigez le code pour faire passé le test et faites le en mode TDD

##Auteurs:
-Flavio Castells
-Youssoufi Garba Abdourrahmane
