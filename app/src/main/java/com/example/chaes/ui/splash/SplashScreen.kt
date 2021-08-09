package com.example.chaes.ui.splash

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.chaes.utilities.NavigationRoutes.loginScreenRoute
import com.example.chaes.utilities.NavigationRoutes.splashScreenRoute
import timber.log.Timber

//@Preview
@Composable
fun SplashScreen(
    navController: NavController
){
    Timber.d("Splash recompose")
    Button(
        onClick = {
            navController.navigate(loginScreenRoute){
                popUpTo(splashScreenRoute){
                    inclusive = true
                }
            }
        }
    ){
        Text(text = "Click me!")
    }
}