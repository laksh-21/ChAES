package com.example.chaes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route: String, val title: String){
    sealed class BottomNavScreens(
        route: String,
        title: String,
        val icon: ImageVector
    ): Screens(route, title){
        object Home: BottomNavScreens("homeScreen", "Home", Icons.Filled.Home)
        object Profile: BottomNavScreens("profileScreen", "Profile", Icons.Filled.AccountCircle)
    }

    object ChatDetail: Screens(route = "chatDetail", title = "Chat")

    sealed class LoginFlowScreens(
        route: String,
        title: String,
    ): Screens(route, title){
        object Login: LoginFlowScreens(route = "loginScreen", title = "Login")
        object SignUp: LoginFlowScreens(route = "signUpScreen", title = "SignUp")
    }
}

val bottomNavScreens = listOf(
    Screens.BottomNavScreens.Home,
    Screens.BottomNavScreens.Profile
)

const val bottomNavScreensRoute: String = "homeScreensRoute"
const val loginFlowScreensRoute: String = "loginScreensRoute"