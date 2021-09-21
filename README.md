<p align="center"><img src="assets/chaesIcon.png" width="150"></p> 
<h2 align="center"><b>ChAES</b></h2>
<h4 align="center">An AES encrypted app written entirely in Jetpack Compose.</h4>

<p align="center"><a href="#screenshots">Screenshots</a> &bull; <a href="#description">Description</a> &bull; <a href="#features">Features</a> &bull; <a href="#libraries">Libraries Used</a>

## Screenshots

### Light Theme
[<img src="assets/splash_light.jpg" width=160>](assets/splash_light.jpg)
[<img src="assets/login_light.jpg" width=160>](assets/login_light.jpg)
[<img src="assets/signup_light.jpg" width=160>](assets/signup_light.jpg)
[<img src="assets/home_light.jpg" width=160>](assets/home_light.jpg)
[<img src="assets/chat_light.jpg" width=160>](assets/chat_light.jpg)

### Dark Theme
[<img src="assets/splash_dark.jpg" width=160>](assets/splash_dark.jpg)
[<img src="assets/login_dark.jpg" width=160>](assets/login_dark.jpg)
[<img src="assets/signup_dark.jpg" width=160>](assets/signup_dark.jpg)
[<img src="assets/home_dark.jpg" width=160>](assets/home_dark.jpg)
[<img src="assets/chat_dark.jpg" width=160>](assets/chat_dark.jpg)

#### Validator
[<img src="assets/validator_dark.jpg" width=160>](assets/validator_dark.jpg)

## Description
ChAES uses client side AES encryption for secure messaging.<br>
The app uses Firebase for database and authentication and the new UI toolkit Jetpack Compose for UI.

### Features
* User authentication
* Search usernames to send them a text
* AES encryption
* Dependency Injection
* Light and Dark Theme
* Input validator
* Unread message count

### Libraries
* [Jetpack Compose](https://developer.android.com/jetpack/compose) - UI
* [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)- Navigation
* [Firebase-Auth](https://firebase.google.com/docs/auth) - Authentication
* [Firestore](https://firebase.google.com/docs/firestore) - Database
* [Preferences Datastore](https://developer.android.com/topic/libraries/architecture/datastore) - Persistent data
* [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Dependency Injection

### Project Setup
As this project uses Firebase for database and authentication, to set it up on your own system, you'll need to configure the project to your own firebase account. <br>
You can follow [this guide](https://firebase.google.com/docs/android/setup) to setup your firebase account. <br>
Once you download the **google-services.json** file, just replace the one in the project with that file.

