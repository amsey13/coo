# Simulation de Location de Vélo en Libre-Service

## 1. Binôme et introduction
**Binôme :**  
- Kadiatou Barry  
- Mamady Mansare

**Introduction :**  
Ce projet simule un système de location de vélos en libre-service avec un centre de contrôle supervisant les stations et la flotte de vélos.  
La simulation inclut la gestion des utilisateurs, des états de vélos, des accessoires, des maintenances et des vols.



## 2. HowTo

### 2.1 Récupérer les sources
bash
git clone <>
cd projet_coo

### 2.2 Générer la documentation

javadoc -d docs -sourcepath src/main/java -subpackages main.java
La documentation sera générée dans le dossier docs/.

### Compiler et exécuter les sources
javac -d bin -cp "lib/junit-platform-console-standalone-1.10.2.jar" src/main/java/**/*.java
javac -d bin src/main/java/simulation/Simulation.java
Se placer à la racine du projet pour exécuter ces commandes.

### 2.4 Compiler et exécuter les tests
javac -d bin -cp "lib/junit-platform-console-standalone-1.10.2.jar" test/java/**/*.java
java -jar lib/junit-platform-console-standalone-1.10.2.jar --class-path bin --scan-class-path

### 2.5 Générer et exécuter l’archive (.jar)
jar --create --file projet_coo.jar -C bin .
java -jar projet_coo.jar

## 3. Présentation du code et éléments saillants
### 3.1 Architecture principale

Velo : différents types et accessoires, état du vélo.

Station : gestion des emplacements, dépôt/retrait, maintenance.

Utilisateur : location de vélos selon le solde disponible.

CentreControle : observer des stations et redistribution des vélos (stratégie Round Robin ou aléatoire).

Simulation : boucle principale simulant les tours de temps et les actions aléatoires.

### 3.2 Patterns utilisés

Observer : notifications entre stations et centre de contrôle.

Strategy : redistribution des vélos.

Decorator : ajout d’accessoires aux vélos.

Factory : création de vélos et extensions possibles (ex. trottinettes électriques).

### 3.3 Points forts du projet

Extensible pour ajouter de nouveaux types de véhicules ou accessoires.

Simulation réaliste avec tours de temps, actions aléatoires, maintenance et gestion des vols.

Tests automatisés couvrant la majorité des cas (JUnit 5).