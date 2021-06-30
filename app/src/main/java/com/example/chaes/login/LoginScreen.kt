package com.example.chaes.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chaes.login.components.InfoTextField
import com.example.chaes.login.viewModel.LoginViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chaes.Screens

@Composable
fun LoginScreen(
    navController: NavController?,
    viewModel: LoginViewModel = viewModel()
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        HeaderSection()
        val emailText: String by viewModel.emailText.observeAsState("")
        val passwordText: String by viewModel.passwordText.observeAsState("")
        InputFieldSection(
            emailText = emailText,
            passwordText = passwordText,
            onEmailTextChanged = { viewModel.onEmailTextChanged(it) },
            onPasswordTextChanged = { viewModel.onPasswordTextChanged(it) }
        )
        LoginButtons()
        SignupRow(navController = navController)
    }
}

@Composable
fun LoginButtons() {
    Button(
        onClick = {},
        shape = RoundedCornerShape(50),
        modifier = Modifier.fillMaxWidth(0.5f)
    ) {
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
}

@Composable
fun HeaderSection(){
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
}

@Composable
fun InputFieldSection(
    emailText: String = "",
    passwordText: String = "",
    onEmailTextChanged: (String) -> Unit = {},
    onPasswordTextChanged: (String) -> Unit = {},
){
    InfoTextField(
        hintText = "EMAIL",
        leadingIcon = Icons.Outlined.Email,
        text = emailText,
        onTextChanged = { onEmailTextChanged(it) }
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoTextField(
        hintText = "PASSWORD",
        leadingIcon = Icons.Outlined.Lock,
        obfuscate = true,
        text = passwordText,
        onTextChanged = { onPasswordTextChanged(it) }
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun SignupRow(
    navController: NavController?
){
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
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
            modifier = Modifier.clickable(
                onClick = {
                    navController?.navigate(Screens.LoginFlowScreens.SignUp.route){
                        popUpTo(Screens.LoginFlowScreens.Login.route)
                    }
                }
            )
        )
    }
}

@Preview
@Composable
fun ScreenDemo(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeaderSection()
        InputFieldSection()
        LoginButtons()
        SignupRow(navController = null)
    }
}