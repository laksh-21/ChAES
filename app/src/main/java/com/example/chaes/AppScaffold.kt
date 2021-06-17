package com.example.chaes

import androidx.compose.animation.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@ExperimentalAnimationApi
@Composable
fun AppScaffold(
    navController: NavController
){
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            val visible = navBackStackEntry?.destination?.route == Screens.BottomNavScreens.Home.route ||
                    navBackStackEntry?.destination?.route == Screens.BottomNavScreens.Search.route
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically() + expandVertically(expandFrom = Alignment.Bottom) + fadeIn(),
                exit = fadeOut()
            ){
                BottomNavBar(navController = navController, bottomNavScreens)
            }
        }
    ) { paddingValues ->
        Navigator(navController = navController as NavHostController, paddingValues = paddingValues)
    }
}