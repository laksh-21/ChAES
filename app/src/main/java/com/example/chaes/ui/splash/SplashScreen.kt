package com.example.chaes.ui.splash

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.chaes.utilities.NavigationRoutes.homeScreenRoute
import com.example.chaes.utilities.NavigationRoutes.loginScreenRoute
import com.example.chaes.utilities.NavigationRoutes.splashScreenRoute
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import timber.log.Timber

//@Preview
@Composable
fun SplashScreen(
    navController: NavController
){
    val auth: FirebaseAuth = Firebase.auth
    LaunchedEffect(Unit){
        delay(1000)
        if(auth.currentUser != null){
            navController.navigate(homeScreenRoute){
                popUpTo(0)
            }
        } else {
            navController.navigate(loginScreenRoute) {
                popUpTo(splashScreenRoute) {
                    inclusive = true
                }
            }
        }
    }
}