package com.example.chaes.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(
    navController: NavController?
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Login",
            style = MaterialTheme.typography.h3,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Please sign-in to continue",
            style = MaterialTheme.typography.subtitle2,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {},
            shape = RoundedCornerShape(50),
            contentPadding = PaddingValues(horizontal = 40.dp, vertical = 16.dp),
        ){
            Text(text = "Login")
        }
    }
}

@Preview
@Composable
fun LoginScreenDemo(){
    LoginScreen(navController = null)
}