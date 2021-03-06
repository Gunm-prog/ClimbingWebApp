# ClimbingWebApp
P6:Réalisation d'un site communautaire autour de l'escalade

Climbing Webapp  . Lien vers le github du projet : https://github.com/GunmProg/ClimbingWebApp

Développement :

Cette application a été développée avec IntelliJ IDEA Community Edition et la Java JDK 12 (12.0.2).
C'est une architecture Maven mono-module. J'ai utilisé les framework SpringBoot (Spring-core pour l'inversion de contrôle et l'injection de dépendance, Spring-data pour l'accées aux données et Spring-webmvc pour la gestion des parties vues et controller) et Hibernate pour la partie persistence des entités.


Features:

- Site Web Responsive
- Connexion Utilisateur sécurisée (encryptage du mot de passe +  masquage du mot de passe lorsque l'utilisateur le saisit 
 + impossibilité pour un utilisateur d'accéder à un autre compte utilisateur par le biais de l'url)
- Barre de recherche Multi-critères
- Création de contenu Spot/Area/Route/Pitch/Topo
- Système de gestion de prêts de topos
- Système de création de commentaires par les utilisateurs + gestion des commentaires (modification/suppression) par l'admin
- Gestion de "tag" des spots (officiel) par l'admin


Technologies:

- JAVA EE
- Maven
- Apache Tomcat 9
- Spring BOOT
- Spring MVC
- Spring DATA/JPA/HIBERNATE
- JPQL
- Thymeleaf
- Bootstrap
- HTML 5
- CSS 3
- BDD MySQL Workbench


Installation:
 

Réalisez un git clone du répertoire ClimbingWebApp (disponible sur: https://github.com/GunmProg/ClimbingWebApp)

dans votre repertoire de travail. 

Importez-le dans Intellij Idea.

Allez dans le fichier application.properties , dans la ligne "spring.jpa.hibernate.ddl-auto=update" , passez la configuration en mode "create".

Créez une bdd via un editeur SQL (j'ai utilisé MySQLWorkbench), utilisez le jeu de données dans le dossier dump fourni,

puis dans le fichier application.properties : "spring.datasource.url=" entrez le lien vers 

votre BDD "spring.datasource.username=" Votre username "spring.datasource.password=" Votre mot de passe

Remettez la configuration "spring.jpa.hibernate.ddl-auto= create" en mode "update" dans le fichier application.properties.


Dans le terminal saisissez les commandes suivantes:

mvn clean package spring-boot: repackage, puis java -jar target\ClimbingWebApp-0.0.1-SNAPSHOT.jar



Pour visulaliser ce que faire l'admin et l'utilisateur connecté, voici les identifiants et mots de passe nécessaires lors du login:

- admin: emi@hotmail.com  mot de passe: dkdk;
- utilisateurs connectés: 
aa@hotmail.com, mot de passe: goodwin
chris@hotmail.com, mot de passe: tine
tom@hotmail.com, mot de passe: 8282
zak@hotmail.com, mot de passe: bagans


Lancement :

Vous pouvez lancer un serveur web depuis votre IDE (TOMCAT9) et vous rendre sur http://localhost:8080/ dans votre navigateur.


Auteur:

Emilie BALSEN dans le cadre de ma formation Développeur d'applications JAVA chez OpenClassrooms.

