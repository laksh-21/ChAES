package com.example.chaes.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chaes.Screens

@Composable
fun HomeScreen(navController: NavController){
    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { MessageFAB() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(paddingValues = paddingValues)
        ) {
            Text(text = "Hello Home!")
            Button(onClick = { navController.navigate(Screens.ChatDetail.route) }) {
                Text(text = "Click me!")
            }
        }
    }
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