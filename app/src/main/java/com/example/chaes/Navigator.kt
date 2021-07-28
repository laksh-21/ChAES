package com.example.chaes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.chaes.detail.ChatDetailScreen
import com.example.chaes.home.HomeScreen
import com.example.chaes.login.LoginScreen
import com.example.chaes.search.SearchScreen
import com.example.chaes.login.SignUpScreen

@ExperimentalMaterialApi
@Composable
fun Navigator(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = loginFlowScreensRoute,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        // Login flow graph
        navigation(
            startDestination = Screens.LoginFlowScreens.Login.route,
            loginFlowScreensRoute
        ) {
            composable(Screens.LoginFlowScreens.Login.route) {
                LoginScreen(navController = navController)
            }
            composable(Screens.LoginFlowScreens.SignUp.route) {
                SignUpScreen(navController = navController)
            }
        }
        // Home screens graph
        navigation(
            startDestination = Screens.BottomNavScreens.Home.route,
            bottomNavScreensRoute
        ) {
            composable(Screens.BottomNavScreens.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(Screens.BottomNavScreens.Profile.route) {
                SearchScreen(navController = navController)
            }
        }
        composable(Screens.ChatDetail.route) {
            ChatDetailScreen(navController = navController)
        }
    }
}