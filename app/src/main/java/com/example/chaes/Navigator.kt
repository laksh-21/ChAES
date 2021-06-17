package com.example.chaes

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chaes.detail.ChatDetailScreen
import com.example.chaes.home.HomeScreen
import com.example.chaes.search.SearchScreen

@Composable
fun Navigator(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screens.BottomNavScreens.Home.route
    ) {
        composable(Screens.BottomNavScreens.Home.route){
            HomeScreen(navController = navController)
        }
        composable(Screens.BottomNavScreens.Search.route){
            SearchScreen(navController = navController)
        }
        composable(Screens.ChatDetail.route){
            ChatDetailScreen(navController = navController)
        }
    }
}