package com.example.chaes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppScaffold(
    navController: NavController
){
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            if(navBackStackEntry?.destination?.route == Screens.BottomNavScreens.Home.route ||
                navBackStackEntry?.destination?.route == Screens.BottomNavScreens.Search.route){
                BottomNavBar(navController = navController, bottomNavScreens)
            }
        }
    ) {
        Navigator(navController = navController as NavHostController)
    }
}