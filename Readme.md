# Simulation de Location de Vélo en Libre-Service

## 1. Binôme et introduction
**Binôme :**  
- Kadiatou Barry  
- Mamady Mansare

**Introduction :**  
Ce projet simule un système de location de vélos en libre-service avec un centre de contrôle supervisant les stations et la flotte de vélos.  
La simulation inclut la gestion des utilisateurs, des états de vélos, des accessoires, des maintenances et des vols.



## 2. HowTo

### 2.1 Récupérer les sources depuis le depôt git
bash
git clone <https://gitlab-etu.fil.univ-lille.fr/kadiatou.barry2.etu/projet_coo.git>
cd projet_coo

### 2.2 Générer la documentation
make doc
Toute la documentation sera dans le dossier doc à la racine du projet
### Compiler et exécuter les sources
make compile
make run

### 2.4 Compiler et exécuter les tests
make test

### 2.5 Générer et exécuter l’archive (.jar)
make jar

## 3. Présentation du code et éléments saillants
### 3.1 Architecture principale





### 3.2 Patterns utilisés

Observer : notifications entre stations et centre de contrôle.

Strategy : redistribution des vélos.

Decorator : ajout d’accessoires aux vélos.

Factory : création de vélos et extensions possibles (ex. trottinettes électriques).

### 3.3 Points forts du projet

Extensible pour ajouter de nouveaux types de véhicules ou accessoires.

Simulation réaliste avec tours de temps, actions aléatoires, maintenance et gestion des vols.

Tests automatisés couvrant la majorité des cas (JUnit 5).