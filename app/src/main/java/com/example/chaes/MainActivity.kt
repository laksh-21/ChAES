package com.example.chaes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chaes.ui.detail.ChatDetailScreen
import com.example.chaes.ui.detail.viewModel.ChatDetailViewModel
import com.example.chaes.ui.home.HomeScreen
import com.example.chaes.ui.home.viewModel.HomeScreenViewModel
import com.example.chaes.ui.login.LoginScreen
import com.example.chaes.ui.login.SignUpScreen
import com.example.chaes.ui.login.viewModel.LoginViewModel
import com.example.chaes.ui.login.viewModel.SignUpViewModel
import com.example.chaes.ui.splash.SplashScreen
import com.example.chaes.utilities.NavigationRoutes.chatDetailNameArgName
import com.example.chaes.utilities.NavigationRoutes.chatDetailScreenRoute
import com.example.chaes.utilities.NavigationRoutes.chatDetailUidArgName
import com.example.chaes.utilities.NavigationRoutes.homeScreenRoute
import com.example.chaes.utilities.NavigationRoutes.loginScreenRoute
import com.example.chaes.utilities.NavigationRoutes.signupScreenRoute
import com.example.chaes.utilities.NavigationRoutes.splashScreenRoute
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var randomString: String
    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContent {
            val navController = rememberNavController()
            Scaffold{
                NavHost(navController = navController, startDestination = splashScreenRoute){
                    composable(splashScreenRoute){
                        SplashScreen(navController = navController)
                    }
                    composable(loginScreenRoute) {
                        val loginViewModel: LoginViewModel = hiltViewModel()
                        LoginScreen(
                            navController = navController,
                            viewModel = loginViewModel
                        )
                    }
                    composable(signupScreenRoute) {
                        val signupViewModel: SignUpViewModel = hiltViewModel()
                        SignUpScreen(
                            navController = navController,
                            viewModel = signupViewModel
                        )
                    }
                    composable(homeScreenRoute){
                        val homeViewModel: HomeScreenViewModel = hiltViewModel()
                        HomeScreen(
                            navController = navController,
                            viewModel = homeViewModel
                        )
                    }
                    composable("$chatDetailScreenRoute/{$chatDetailUidArgName}/{$chatDetailNameArgName}") { backStackEntry ->
                        val chatDetailViewModel: ChatDetailViewModel = hiltViewModel()
                        ChatDetailScreen(
                            navController = navController,
                            viewModel = chatDetailViewModel,
                            toUid = backStackEntry.arguments?.getString(chatDetailUidArgName),
                            name = backStackEntry.arguments?.getString(chatDetailNameArgName),
                        )
                    }
                }
            }
        }
        Timber.d(randomString)
    }
}