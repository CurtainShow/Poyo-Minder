# Poyo-Minder - Android App 

## Sujet : Le Pilulier

_Les personnes âgées, ayant parfois des ordonnances compliquées, ont parfois besoin d'une aide pour leur consommation de médicaments._

L'idée est de réaliser une application qui permet les fonctionnalités suivantes :

  * Sur une page "ordonnance", saisir des médicaments provenant d'une ordonnance (voir plus bas les éléments qu'on peut mettre pour stocker l'ordonnance)
  * permettre de montrer pour des périodes intitulées "MATIN", "MIDI" et "SOIR" (page d'accueil claire de choix entre les trois, plus dans un coin la saisie d'ordonnance) l'accès à une page "médicaments du moment"
  * Sur la page "médicament du moment", il faut afficher tous les médicaments à prendre à ce moment, en montrant de façon claire une différence entre les pris et pas encore pris. Un clic sur un médicament permet de le faire passer de "non pris" à "pris" (ou l'inverse).
  * Sur une page "journal de bord", une liste des médicaments non pris dans les jours précédents.


Vous devrez commencer par faire des croquis préparatoires pour l'interface utilisateur, puis coder l'application.

## Développement de l'application


# Fonctionnalités

* **Présentation des services à l'aide d'un PreOnBoarding.**
* **Création et connexion à un compte** (Conditions à respecté, vérification d'e-mail et reset de mot de passe).
* **Ajout de médicaments à l'aide d'un nom, un type, un moment de prise et une description additionnelle**. 
* **Listing des médicaments sur la page d'accueil en fonction des moments de la journée**.
* **Onglet résumé qui nous précise les médicaments pris/non pris dans la journée**.
* **Reminder matin, midi et soir chaque jour** pour ne pas oublier ses médicaments.

# Changelog

## V-0.0.4 - Ajout de médicament
- Création d'une activité Profil
- Création d'un médicaments

## V-0.0.3 - Firebase
- Association de la Firebase
- Création de compte
- Conditions de création de compte
- Activité de Login 
- Validation du compte par mail
- Réinitialisation du mot de passe


## V-0.0.2 - PreOnboarding
- Ajout des 3 slides de pré-onboarding
- Ajout des animation des slide
- Création de l'activité Login/Register
- Redirection vers l'activité de Login

## V-0.0.1 - Initialisation
- Installation d'Android Studio
- Création d'un projet Android
- Création de l'affichage Login/Register
