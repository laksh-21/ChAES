package com.example.chaes

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(
    navController: NavController,
    screens: List<Screens.BottomNavScreens>
){
    BottomNavigation(

    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            BottomNavigationItem(
                selected = currentRoute == screen.route,
                onClick = {
                          navController.navigate(screen.route){
                              launchSingleTop = true
                              popUpTo(Screens.BottomNavScreens.Home.route)
                          }
                },
                label = { Text(text = screen.title) },
                icon = { Icon(imageVector = screen.icon, contentDescription = "") }
            )
        }
    }
}