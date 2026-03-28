# TP 12 — API REST sécurisée avec Spring Security et JWT (JSON Web Token)

## 📚 Cours
**Développement JakartaEE : Spring**


---

## 📝 Contexte
Ce TP porte sur la mise en place d'un mécanisme d'authentification et d'autorisation moderne pour les API REST. Contrairement aux sessions classiques, nous utilisons ici une approche **Stateless** (sans état) basée sur les jetons **JWT**. 

Dans ce modèle, le serveur ne stocke aucune information de session. Chaque requête client doit transporter un jeton signé numériquement qui prouve l'identité de l'utilisateur et ses droits d'accès.

---

## 🎯 Objectifs
* **Authentication Endpoint :** Créer une route /api/auth/login qui génère un token JWT après vérification des identifiants.
* **Architecture Stateless :** Configurer Spring Security pour fonctionner sans session (STATELESS).
* **Filtre Personnalisé :** Implémenter un filtre d'autorisation qui intercepte chaque requête pour valider le token.
* **RBAC (Role-Based Access Control) :** Sécuriser les endpoints selon les rôles (ADMIN, USER).
* **Gestion JWT :** Maîtriser la génération, l'extraction et la validation des claims (sujet, expiration, signature).

---

## 🛠 Technologies et Dépendances
* **Java 17 / 21**
* **Spring Boot 3+**
* **Spring Security 6**
* **Spring Data JPA & MySQL**
* **Lombok**
* **JJWT (io.jsonwebtoken)** : Bibliothèque pour la manipulation des tokens JWT.
  * jjwt-api, jjwt-impl, jjwt-jackson.

---

## 📁 Structure du projet
Le projet est organisé pour séparer clairement la logique de sécurité de la logique métier :

<img width="1362" height="907" alt="image" src="https://github.com/user-attachments/assets/46a29143-eff5-431d-8386-4e019ea6c1d0" />



---

## ⚙️ Configuration Clé (`application.properties`)
Le secret utilisé pour signer les tokens doit être robuste :
properties
jwt.secret=MySuperSecretKeyForJwtAuthentication123456
jwt.expiration=3600000   # Durée de validité : 1 heure
## 🚀 Fonctionnement du mécanisme JWT
### 1. Structure du Token
Le token généré par l'application suit la structure standard : Header.Payload.Signature.

### Header : Algorithme de hachage (HS256).

### Payload : Contient le username (Subject) et la date d'expiration.

### Signature : Garantit que le token n'a pas été modifié.

### 2. Le Filtre d'Autorisation
Le JwtAuthorizationFilter s'exécute à chaque requête :

Il cherche le header Authorization: Bearer <token>.

Il valide la signature et l'expiration via JwtUtil.

Si valide, il injecte l'utilisateur dans le SecurityContextHolder de Spring.

##  Tests avec Postman
Étape 1 : Authentification (POST)
URL : http://localhost:8080/api/auth/login

![WhatsApp Image 2026-03-28 at 13 12 00](https://github.com/user-attachments/assets/0ba8d773-14f1-43a8-be2a-3e349e5c9df3)


Étape 2 : Accès aux ressources protégées (GET)
Pour accéder à /api/user/profile ou /api/admin/dashboard :

Allez dans l'onglet Authorization.

Sélectionnez Type: Bearer Token.

Collez le jeton reçu à l'étape 1.

![WhatsApp Image 2026-03-28 at 13 18 42](https://github.com/user-attachments/assets/da0cf9b5-8b42-40c1-a2c3-6b5c44a453c8)


Étape 3 : Vérification des restrictions
Un utilisateur avec ROLE_USER recevra une erreur 403 Forbidden s'il tente d'accéder à /api/admin/**.

Une requête sans token ou avec un token expiré sera rejetée.

![WhatsApp Image 2026-03-28 at 13 20 42](https://github.com/user-attachments/assets/b18612bc-00a8-44ad-a118-8d5967ca5212)

![WhatsApp Image 2026-03-28 at 13 22 19](https://github.com/user-attachments/assets/e602cd5c-c100-426d-a57d-17001dc071f6)


## conclusion
Ce TP permet de comprendre comment sécuriser des microservices ou des applications découplées (Frontend/Backend). Nous avons mis en place :

Une base de données pour les utilisateurs et rôles.

Un système de cryptage des mots de passe avec BCrypt.

Une sécurité robuste qui ne surcharge pas la mémoire du serveur (Stateless).
