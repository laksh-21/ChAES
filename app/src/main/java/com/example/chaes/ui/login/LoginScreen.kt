package com.example.chaes.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chaes.ui.login.components.InfoTextField
import com.example.chaes.ui.login.viewModel.LoginViewModel
import com.example.chaes.ui.login.components.HeaderSection
import com.example.chaes.utilities.NavigationRoutes.homeScreenRoute
import com.example.chaes.utilities.NavigationRoutes.loginScreenRoute
import com.example.chaes.utilities.NavigationRoutes.signupScreenRoute

@Composable
fun LoginScreen(
    navController: NavController?,
    viewModel: LoginViewModel,
){
    val userLoggedIn by viewModel.userLoggedIn.observeAsState(false)
    val userNavigated by viewModel.userNavigated.observeAsState(false)
    if(userLoggedIn && !userNavigated){
        viewModel.onUserNavigated()
        navController?.navigate(homeScreenRoute){
            popUpTo(loginScreenRoute){
                inclusive = true
            }
        }
    }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        HeaderSection(
            mainText = "Login",
            subText = "Please sign-in to continue."
        )
        val emailText: String by viewModel.emailText.observeAsState("")
        val passwordText: String by viewModel.passwordText.observeAsState("")
        InputFieldSection(
            emailText = emailText,
            passwordText = passwordText,
            onEmailTextChanged = { viewModel.onEmailTextChanged(it) },
            onPasswordTextChanged = { viewModel.onPasswordTextChanged(it) }
        )
        LoginButtons(onClickLogin = { viewModel.onClickLogin() })
        SignupRow(navController = navController)
        Button(
            onClick = {
                navController?.navigate(homeScreenRoute){
                    popUpTo(loginScreenRoute){
                        inclusive = true
                    }
                }
            }
        ){
            Text(text = "Click me!")
        }
    }
}

@Composable
private fun LoginButtons(
    onClickLogin: () -> Unit = {}
) {
    Button(
        onClick = onClickLogin,
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
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun InputFieldSection(
    emailText: String = "",
    passwordText: String = "",
    onEmailTextChanged: (String) -> Unit = {},
    onPasswordTextChanged: (String) -> Unit = {},
){
    InfoTextField(
        hintText = "EMAIL",
        leadingIcon = Icons.Outlined.Email,
        text = emailText,
        onTextChanged = { onEmailTextChanged(it) },
        keyboardType = KeyboardType.Email,
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoTextField(
        hintText = "PASSWORD",
        leadingIcon = Icons.Outlined.Lock,
        obfuscate = true,
        text = passwordText,
        onTextChanged = { onPasswordTextChanged(it) },
        keyboardType = KeyboardType.Password,
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun SignupRow(
    navController: NavController?
){
    Row(
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
                    navController?.navigate(signupScreenRoute){
                        popUpTo(loginScreenRoute)
                    }
                }
            )
        )
    }
}

@Preview
@Composable
private fun ScreenDemo(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeaderSection(
            mainText = "Login",
            subText = "Please sign-in to continue."
        )
        InputFieldSection()
        LoginButtons()
        SignupRow(navController = null)
    }
}