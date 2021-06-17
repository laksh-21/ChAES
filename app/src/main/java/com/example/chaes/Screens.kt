package com.example.chaes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route: String, val title: String){
    sealed class BottomNavScreens(
        route: String,
        title: String,
        val icon: ImageVector
    ): Screens(route, title){
        object Home: BottomNavScreens("homeScreen", "Home", Icons.Filled.Home)
        object Search: BottomNavScreens("searchScreen", "Search", Icons.Filled.Search)
    }

    object ChatDetail: Screens(route = "chatDetail", title = "Chat")
}

val bottomNavScreens = listOf(
    Screens.BottomNavScreens.Home,
    Screens.BottomNavScreens.Search
)