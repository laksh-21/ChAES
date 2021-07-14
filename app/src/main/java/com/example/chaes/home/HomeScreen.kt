package com.example.chaes.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.chaes.Screens

@Composable
fun HomeScreen(navController: NavController){
    // TODO: add a FAB for a new message
    Column {
        Text(text = "Hello Home!")
        Button(onClick = {navController.navigate(Screens.ChatDetail.route)}){
            Text(text = "Click me!")
        }
    }
}