# PROJET_COO
## Binôme
1.Mamady Mansaré
2.Kadiatou.barry

## 1. Introduction
**Le projet Vélos Libre Service** est une application Java orientée objet, développée dans le cadre du cours de Programmation Orientée Objet.

Ce projet consiste à simuler un système de location de vélos en libre-service en Java orienté objet.

Le système gère :

des stations avec capacité limitée pour déposer ou retirer des vélos,

des vélos de différents types (classiques, électriques, pliants) avec accessoires,

des utilisateurs payant selon les options choisies,

un centre de contrôle supervisant le réseau et redistribuant les vélos en cas de déséquilibre.

La simulation prend en compte les événements dynamiques : pannes, vols, et interventions de métiers spécialisés.
La conception est extensible, permettant d’ajouter facilement de nouveaux véhicules ou métiers.
## 2. HowTo — Instructions d’exécution
Toutes les commandes suivantes sont à exécuter depuis le répertoire racine du projet.
* Récupérer les sources du projet
    * git clone [lien gitlab] (https://github.com/utilisateur/nom-du-projet.git)!
    * ```cd PROJET_COO```

Tous les fichiers sources se trouvent dans le dossier src du projet.

* Générer la documentation (JavaDoc)
``` make doc ```
> Localisation : La documentation HTML générée se trouve dans le dossier **_doc_**/.

* Compiler et exécuter le projet
``` make run ```


* Compiler et exécuter les tests
Avec Makefile :
``` make test ```
Localisation des tests : test/
Résultats des tests : affichés dans le terminal.

* Générer et exécuter l’archive .jar
Génération :
``` make jar ```
Exécution :
java -jar projet.jar

> Archive générée : projet.jar dans le dossier dist/.

# 3. Éléments de conception et aspects techniques
 Principes de conception mis en œuvre
**Encapsulation:** tous les attributs sont privés, accessibles via getters/setters.

**Héritage:** la classe X hérite de Y pour factoriser les comportements communs.

**Polymorphisme:** les méthodes redéfinies permettent un comportement spécifique selon le type d’objet.

**Abstraction:** les classes abstraites définissent une interface commune pour les sous-classes.

**Interfaces:** utilisées pour garantir la cohérence du comportement entre différentes implémentations.

**Design pattern:** Factory, Singleton, Strategy...

**Gestion des exceptions personnalisées pour rendre le code plus robuste.**

**Architecture claire**.

