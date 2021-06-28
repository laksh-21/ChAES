package com.example.chaes.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chaes.login.components.InfoTextField

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
            modifier = Modifier.fillMaxWidth(.9f),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Please sign-in to continue.",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.fillMaxWidth(.9f),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(16.dp))
        InfoTextField(
            hintText = "EMAIL",
            leadingIcon = Icons.Outlined.Email
        )
        Spacer(modifier = Modifier.height(16.dp))
        InfoTextField(
            hintText = "PASSWORD",
            leadingIcon = Icons.Outlined.Lock
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {},
            shape = RoundedCornerShape(50),
            modifier = Modifier.fillMaxWidth(0.5f)
        ){
            Text(
                text = "LOGIN",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Forgot Password?",
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.primary,
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
                .padding(bottom = 16.dp)
        ){
            Text(
                text = "Not Registered Yet?",
                style = MaterialTheme.typography.subtitle2,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.primary,
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenDemo(){
    LoginScreen(navController = null)
}