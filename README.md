# SnapEats 🍳📸

## Présentation

SnapEats est une application Android développée dans le cadre du cours **MVP (Minimum Viable Product)** de la **HEIG-VD**.

L'objectif du projet était d'identifier une problématique réelle, d'analyser les besoins des utilisateurs potentiels, puis de concevoir et développer un **MVP** permettant de valider une solution auprès du marché cible.

 SnapEats propose une solution simple : prendre une photo de ses ingrédients et obtenir instantanément des suggestions de recettes adaptées.

---

## Fonctionnalités principales

* Prise de photo des ingrédients directement depuis l'application
* Sélection d'une image depuis la galerie
* Analyse automatique des ingrédients présents sur l'image
* Génération de recettes personnalisées
* Gestion des préférences utilisateur
* Interface moderne développée avec Jetpack Compose

---

## Parcours utilisateur

1. L'utilisateur ouvre l'application.
2. Il prend une photo de ses ingrédients ou sélectionne une image existante.
3. L'application analyse l'image.
4. Des recettes correspondant aux ingrédients détectés sont générées.
5. L'utilisateur consulte les recettes proposées et choisit celle qui l'intéresse.

---

## Architecture du projet

Le projet suit une architecture inspirée du modèle **MVVM (Model - View - ViewModel)** afin de séparer clairement :

* **Model** : gestion des données et logique métier
* **View** : écrans Jetpack Compose
* **ViewModel** : gestion de l'état et communication entre la vue et les données

Cette architecture facilite :

* la maintenabilité du code ;
* les tests ;
* l'évolution future de l'application.

---

## Technologies utilisées

### Développement Android

* Kotlin
* Android Studio
* Jetpack Compose
* Navigation Compose
* Android ViewModel

### Fonctionnalités

* CameraX / API caméra Android
* Sélecteur de galerie Android
* Gestion des permissions Android

### Outils

* Git
* GitHub
* Gradle

---

## Installation

### Prérequis

* Android Studio (version récente recommandée)
* Android SDK
* JDK 17 ou supérieur

### Cloner le projet

```bash
git clone https://github.com/snapeats-mvp/mobile-app.git
```

### Ouvrir le projet

1. Ouvrir Android Studio
2. Sélectionner **Open Project**
3. Choisir le dossier cloné
4. Laisser Gradle synchroniser les dépendances

### Lancer l'application

1. Connecter un appareil Android ou lancer un émulateur
2. Cliquer sur **Run**

---

## Vision MVP

Le projet a été développé selon la démarche MVP enseignée à la HEIG-VD :

* Identification d'une problématique
* Analyse des besoins
* Définition d'une proposition de valeur
* Conception de maquettes
* Développement d'un prototype fonctionnel
* Validation des hypothèses auprès des utilisateurs

L'objectif principal n'était pas de développer un produit final complet, mais de créer une version fonctionnelle permettant de tester la pertinence de la solution proposée.

---

## Améliorations futures

* Filtrage avancé des recettes
* Sauvegarde des recettes favorites
* Création de listes de courses
* Comptes utilisateurs
* Historique des analyses
* Support multilingue

---

## Licence

Projet académique réalisé dans le cadre du cursus de la HEIG-VD.
