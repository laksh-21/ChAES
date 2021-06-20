package com.example.chaes.login

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.chaes.bottomNavScreensRoute

@Composable
fun LoginScreen(
    navController: NavController
){
    Button(onClick = {
        navController.navigate(bottomNavScreensRoute){
            navController.graph.startDestinationRoute?.let{
                popUpTo(it){
                    inclusive = true
                }
            }
        }
    }){
        Text(text = "Click me!")
    }
}