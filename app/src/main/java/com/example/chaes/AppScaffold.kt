package com.example.chaes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController

@Composable
fun AppScaffold(
    navController: NavController
){
    val scope = rememberCoroutineScope()

    val bottomBar: @Composable () -> Unit = {

    }
}