# Ticket App

Application mobile Android de réservation de billets pour des événements, développée avec Kotlin et Jetpack Compose.

Le projet inclut :
- une application Android pour consulter des événements et réserver des tickets ;
- une base locale Room pour stocker les données ;
- un backend Node.js simple pour créer des intentions de paiement Stripe ;
- une intégration NFC présente dans l'application.

## Aperçu

L'application permet de :
- afficher une liste d'événements ;
- consulter le détail d'un événement ;
- réserver un ou plusieurs billets ;
- confirmer une réservation ;
- initier un paiement avec Stripe.

## Technologies utilisées

- Kotlin
- Jetpack Compose
- Android Studio
- Room
- Navigation Compose
- Node.js
- Express
- Stripe

## Structure du projet

```text
Ticket-App/
├── app/        # Application Android
├── backend/    # Serveur Node.js pour Stripe
├── gradle/     # Configuration Gradle
└── README.md
```

## Prérequis

- Android Studio
- JDK 17 ou version compatible avec votre installation Android
- Node.js 18+ recommandé
- Un émulateur Android ou un appareil physique

## Lancer le backend

Depuis le dossier `backend` :

```bash
npm install
node server.js
```

Le serveur démarre par défaut sur `http://localhost:4242`.

## Lancer l'application Android

1. Ouvrir le dossier du projet dans Android Studio.
2. Laisser Gradle synchroniser le projet.
3. Démarrer un émulateur ou connecter un téléphone Android.
4. Lancer l'application avec le bouton `Run`.

## Configuration

Le projet contient actuellement une configuration Stripe de test dans le code source pour les essais locaux. Pour un usage réel, il est recommandé de déplacer les clés sensibles dans des variables d'environnement ou une configuration sécurisée.

## Remarques

- Le `minSdk` du projet est configuré à `34`.
- Le backend est prévu pour des tests locaux.
- Certaines données d'événements sont préremplies dans l'application.

## Auteur

Projet initial : [dorian-404](https://github.com/dorian-404)
