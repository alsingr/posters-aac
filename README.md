# posters-aac

Une application qui affiche une liste d'affiches.
S'il n'y a pas de données en local au lancement de l'application, alors les données sont récupérées depuis une API. 
Le developpement de cette application a été guidé par les bonnes pratiques Android JetPack.

# Pour commencer
Ce project utilise Gradle

# Screenshots

# Librairies utilisées
* Architecture : Android Architecture Components pour faciliter la gestion des configurations et la persistance des données
  * Data Binding : pour lier une vue à des données afin de faciliter sa mise à jour. 
  * LiveData : Pour notifier les changements à d'autres composants et gérer le cycle de vie des composants de l'app : fragment, activity etc. Aussi il nous permet d'éviter des fuites mémoires, certains crash. Ainsi, ce composant trace les événements comme la rotation d'un device
  * Navigation : pour faciliter la navigation dans l'app
  * Room : Pour gérer la persistance des données
  * ViewModel
  * Fragment : Interface graphique permettant à l'utilisateur d'intéragir avec l'app 
* Glide pour charger les images
* Retrofit pour effectuer les appels vers l'API
