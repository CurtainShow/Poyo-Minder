# Poyo'Minder : Android App 

<p align="center">
  <img src="https://raw.githubusercontent.com/CurtainShow/Poyo-Minder/main/app/src/main/res/mipmap-xxxhdpi/logo.png" alt="drawing" width="250"/>
</p>

## Sujet : Le Pilulier

_Les personnes âgées, ayant parfois des ordonnances compliquées, ont parfois besoin d'une aide pour leur consommation de médicaments._

L'idée est de réaliser une application qui permet les fonctionnalités suivantes :

  * Sur une page "ordonnance", saisir des médicaments provenant d'une ordonnance (voir plus bas les éléments qu'on peut mettre pour stocker l'ordonnance)
  * Permettre de montrer pour des périodes intitulées "MATIN", "MIDI" et "SOIR" (page d'accueil claire de choix entre les trois, plus dans un coin la saisie d'ordonnance) l'accès à une page "médicaments du moment"
  * Sur la page "médicament du moment", il faut afficher tous les médicaments à prendre à ce moment, en montrant de façon claire une différence entre les pris et pas encore pris. Un clic sur un médicament permet de le faire passer de "non pris" à "pris" (ou l'inverse).
  * Sur une page "journal de bord", une liste des médicaments non pris dans les jours précédents.


Vous devrez commencer par faire des croquis préparatoires pour l'interface utilisateur, puis coder l'application.

## Développement de l'application

<p align="center">
  <img src="https://www.ambient-it.net/wp-content/uploads/2016/12/android_studio-logo-175.png" alt="drawing" width="250"/>
  <img src="https://img.icons8.com/color/480/000000/firebase.png" alt="drawing" width="190"/>
  <img src="https://thetechnicalreview.com/img/gitkraken-logo-dark-sq.png" alt="drawing" width="260"/>
</p>

Pour le développement de cette application, nous avions pour contraintes d'utiliser Android Studio dans l'optique d'une validation de module entrant en compte pour notre obtention de DUT Informatique. Le langage utilité, Java, a aussi été spécifier en tant que contrainte au début du projet. Pour ce qui est du reste, nous avions carte blanche pour faire fonctionner notre application en implémentant les fonctionnalités spécifiées dans le sujet.

Durant le développement, un point important nous a rapidement sauté aux yeux : Le besoin de stocker de l'information en tous genres. Pour ce faire, nous avions deux choix : 
- Gérer les informations en offline en les intégrant dans un fichier texte.
- Gérer l'intégralité des informations (Utilisateur, médicament, etc...) au sein d'une base de données pour donner un aspect plus "professionnel et abouti" au projet.

Pour améliorer nos connaissances dans ce domaine, le choix a vite été faits. L'option de la base de données nous ai apparu comme la plus complète en terme d'utilisation et d'intégration. De plus, Android Studio est fourni avec un module permettant de lier simplement une Firebase (Outils de gestion de base de données créée par Google, tout comme Android Studio) au projet.  

Une fois créés, nous avons de nombreuse fonctionnalités qui se présente à nous : Authentification, Realtime Database, Firestore, Function, etc...
Dans notre cas, nous avons utilisé l'Authentification et la Firestore pour gérer les créations/connexion de compte, mais aussi pour ajouter les médicaments à l'utilisateur courant. 

Le point faible de cette solution est qu'une connexion internet est OBLIGATOIRE pour fonctionner. 
De plus, cette base de données étant gratuite, il y a parfois des soucis de lenteur pour recevoir nos mails de confirmation de compte. Si cela survient, demander un nouveau mot de passe activera votre compte par la même occasion, et dans ce cas, l'envoi est immédiat.



# Fonctionnalités

- [x] **Présentation des services à l'aide d'un PreOnBoarding.**
- [x] **Création et connexion à un compte** (Conditions a respecté, vérification d'e-mail et reset de mot de passe).
- [x] **Ajout de médicaments à l'aide d'un nom, un type, un moment de prise et une description additionnelle**. 
- [x] **Listing des médicaments sur la page d'accueil en fonction des moments de la journée**.
- [x] **Appui simple sur un médicament pour signaler sa prise à la base de données**.
- [x] **Suppression automatique du médicament une fois la date de fin de prise atteinte**.
- [x] **Reminder matin, midi et soir chaque jour** pour ne pas oublier ses médicaments.

### Work In Progress 
- [ ] **Onglet résumé qui nous précise les médicaments pris/non pris dans la journée**.

# Changelog

## V-1.3 : Work In Progress
- Ajout d'une activité Résumé qui liste les médicaments non pris des jours précédents
- Création d'une vue Résumé
- Finalisation et correction globale de l'application

## V-1.2 : Ajout, visualisation et interaction d'un médicament
- Ajout du médicament à la base de données
- Interaction avec la base de données pour afficher les médicaments d'un utilisateur dans les Recycleurviews
- Changement de l'état d'un médicament en appuyant dessus avec modification dans la base de données
- Suppression du médicament une fois la date de fin de prise atteinte 

## V-1.1 : Firestore et RecyclerView
- Changement d'outils de gestion de base de données : Firestore
- Adaptation des méthodes de création du compte, connexion et création du médicament
- Création de RecyclerView pour chaque moment de la journée (Matin, Midi et Soir)
- Création de l'activité Adapter pour insérer les données dans les recyclerview depuis Firestore en utilisant une ligne de référence

## V-1.0 : Création du médicament
- Création d'une activité Profil
- Création d'une méthode médicament

## V-0.3 : Firebase
- Association de la Firebase
- Création du compte
- Conditions de création du compte
- Activité de Login 
- Validation du compte par mail
- Réinitialisation du mot de passe


## V-0.2 : PreOnboarding
- Ajout des 3 slides de pré-onboarding
- Ajout des animations des slides
- Création de l'activité Login/Register
- Redirection vers l'activité de Login

## V-0.1 : Initialisation
- Création d'une maquette sur l'outil Figma
- Installation d'Android Studio
- Création d'un projet Android
- Création de l'affichage Login/Register
