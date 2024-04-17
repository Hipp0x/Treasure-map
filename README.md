# Treasure-map

## Description du projet

Ce projet vise à simuler une chasse au trésor.  
Avec une carte contenant des montagnes et des trésors, les aventuriers doivent se déplacer et récupérer des trésors.  
Chaque aventurier possède une suite prédéfinie d'actions à réaliser.  
Notons que :  
- 2 aventuriers ne peuvent pas se trouver au même endroit  
- Un aventurier ne peut pas se déplacer sur une montagne
- Un aventurier ne peut pas récupérer tous les trésors présent à sa position, il n'en récupère qu'un seul. Il doit donc repasser sur la case pour récupérer un autre trésor. 

## Lancement du projet

Avec Intellij IDEA, ouvrir le projet et lancer le main de la classe Main.java 

Avec le terminal, se placer à la racine du projet et lancer la commande suivante :
- `mvn package`
- `java -jar target/treasure-map-1.0-SNAPSHOT.jar src/main/resources/map.txt` (adapter le chemin du fichier map.txt si necessaire)


## Données du projet 

### Carte

La carte contient des plaines, des montagnes et des trésors.

On définit ses dimensions comme suit :
C - 3 - 4
    => 3 cases de largeur et 4 cases de hauteur
On numérote les cases de la carte de la manière suivante :
Ouest -> Est
Nord -> Sud
Tout en commencant à 0

Les montagnes sont définies comme suit :
M - 1 - 1 
    => montagne située à la case (1,1)

Les trésors sont définis comme suit :
T - 0 - 3 - 2
    => trésor situé à la case (0,3) et contenant 2 trésors

### Aventurier

Un aventurier est caractérisé par :
- son nom
- sa position
- son orientation  

Initialement, chaque aventurier possède une liste d'actions qu'il executera par la suite.
Les actions possibles sont les suivantes :
- A : avancer
- D : tourner à droite (rotation et non déplacement)
- G : tourner à gauche (rotation et non déplacement)

On notera qu'un aventurier ne peut se déplacer sur les montagnes, ni sur une case étant déjà occupée par un autre aventurier.

Un aventurier est défini comme suit :
- A - Lara - 1 - 1 - S - AADADAGGA
    => aventurier nommé Lara, situé à la case (1,1) et orienté vers le Sud. Il possède la liste d'actions suivante : AADADAGGA

## Exemple d'une chasse au trésor

Début de la chasse au trésor:

  .     M    .  
  .     A    .  
  .     .    .  
T(2)   T(3)  .  

=> en version fichier
C - 3 - 4  
M - 1 - 0  
M - 2 - 1  
T - 0 - 3 - 2  
T - 1 - 3 - 3  
A - Lara - 1 - 1 - S - AADADAGGA  


Fin de la chasse au trésor:

  .     M    .  
  .     .    .  
  .     .    .  
  A    T(2)  .  
  
=> en version fichier
C - 3 - 4  
M - 1 - 0  
M - 2 - 1  
T - 1 - 3 - 2  
A - Lara - 0 - 3 - S - 3  