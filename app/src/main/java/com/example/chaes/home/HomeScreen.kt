package com.example.chaes.home

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.chaes.home.components.SearchSection

@Composable
fun HomeScreen(navController: NavController){
    SearchSection()
}

@Composable
fun MessageFAB(){
    FloatingActionButton(
        onClick = {},
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
    ){
        Icon(imageVector = Icons.Filled.Add, contentDescription = "New Message")
    }
}

@Preview
@Composable
fun MessageFABDemo(){
    MessageFAB()
}