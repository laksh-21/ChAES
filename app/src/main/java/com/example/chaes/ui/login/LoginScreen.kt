package com.example.chaes.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chaes.repository.callbacks.SignInCallback
import com.example.chaes.ui.login.components.HeaderSection
import com.example.chaes.ui.login.components.InfoTextField
import com.example.chaes.ui.login.viewModel.LoginViewModel
import com.example.chaes.utilities.NavigationRoutes.homeScreenRoute
import com.example.chaes.utilities.NavigationRoutes.loginScreenRoute
import com.example.chaes.utilities.NavigationRoutes.signupScreenRoute
import timber.log.Timber

@Composable
fun LoginScreen(
    navController: NavController?,
    viewModel: LoginViewModel,
){
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
        val emailIsError = viewModel.emailIsError.value
        val emailHintText = viewModel.emailHintText.value
        val passwordText: String by viewModel.passwordText.observeAsState("")
        val passwordIsError = viewModel.passwordIsError.value
        val passwordHintText = viewModel.passwordHintText.value
        val isLoading = viewModel.isLoading.value
        InputFieldSection(
            emailText = emailText,
            emailHintText = emailHintText,
            emailIsError = emailIsError,
            passwordText = passwordText,
            passwordHintText = passwordHintText,
            passwordIsError = passwordIsError,
            onEmailTextChanged = { viewModel.onEmailTextChanged(it) },
            onPasswordTextChanged = { viewModel.onPasswordTextChanged(it) },
        )
        LoginButtons(
            onClickLogin = {
                viewModel.onClickLogin(
                    object : SignInCallback{
                        override fun onSignInSuccessful() {
                            Timber.d("Sign-in Successful")
                            navController?.navigate(homeScreenRoute){
                                popUpTo(loginScreenRoute){
                                    inclusive = true
                                }
                            }
                        }

                        override fun onSignInFailed() {
                            // nothing yet
                        }

                        override fun onUserDoesNotExist() {

                        }

                        override fun onAuthCredentialsWrong() {
                            // Password is wrong
                        }

                    }
                )
            },
            isLoading = isLoading,
        )
        SignupRow(navController = navController)
    }
}

@Composable
private fun LoginButtons(
    onClickLogin: () -> Unit = {},
    isLoading: Boolean
) {
    Button(
        onClick = onClickLogin,
        shape = RoundedCornerShape(50),
        modifier = Modifier.fillMaxWidth(0.5f),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary
        )
    ) {
        if(!isLoading) {
            Text(
                text = "LOGIN",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                textAlign = TextAlign.Center
            )
        } else{
            CircularProgressIndicator()
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun InputFieldSection(
    emailText: String = "",
    emailHintText: String,
    emailIsError: Boolean,
    passwordText: String = "",
    passwordHintText: String,
    passwordIsError: Boolean,
    onEmailTextChanged: (String) -> Unit = {},
    onPasswordTextChanged: (String) -> Unit = {},
){
    InfoTextField(
        hintText = emailHintText,
        leadingIcon = Icons.Outlined.Email,
        text = emailText,
        onTextChanged = { onEmailTextChanged(it) },
        keyboardType = KeyboardType.Email,
        isError = emailIsError
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoTextField(
        hintText = passwordHintText,
        leadingIcon = Icons.Outlined.Lock,
        obfuscate = true,
        text = passwordText,
        onTextChanged = { onPasswordTextChanged(it) },
        keyboardType = KeyboardType.Password,
        isError = passwordIsError
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