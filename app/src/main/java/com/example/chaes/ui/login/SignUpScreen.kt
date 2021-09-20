package com.example.chaes.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
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
import com.example.chaes.ui.login.viewModel.SignUpViewModel
import com.example.chaes.utilities.NavigationRoutes.homeScreenRoute
import com.example.chaes.utilities.NavigationRoutes.loginScreenRoute
import com.example.chaes.utilities.NavigationRoutes.signupScreenRoute

@Composable
fun SignUpScreen(
    navController: NavController?,
    viewModel: SignUpViewModel,
){
    Scaffold(
        topBar = { TitleBar(navController = navController) }
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val nameText: String by viewModel.nameText.observeAsState("")
            val nameHintText = viewModel.nameHintText.value
            val nameIsError = viewModel.nameIsError.value
            val userNameText: String by viewModel.userNameText.observeAsState("")
            val userNameHintText = viewModel.userNameHintText.value
            val userNameIsError = viewModel.userNameIsError.value
            val emailText: String by viewModel.emailText.observeAsState("")
            val emailHintText = viewModel.emailHintText.value
            val emailIsError = viewModel.emailIsError.value
            val passwordText: String by viewModel.passwordText.observeAsState("")
            val passwordHintText = viewModel.passwordHintText.value
            val passwordIsError = viewModel.passwordIsError.value
            val confirmPasswordText: String by viewModel.confirmPasswordText.observeAsState("")
            val confirmPasswordHintText = viewModel.confirmPasswordHintText.value
            val confirmPasswordIsError = viewModel.confirmPasswordIsError.value
            val isLoading = viewModel.isLoading.value
            HeaderSection(
                mainText = "Create Account",
                subText = "Please fill the inputs below."
            )
            InfoFieldSection(
                nameText = nameText,
                nameHintText = nameHintText,
                nameIsError = nameIsError,
                userNameText = userNameText,
                userNameHintText = userNameHintText,
                userNameIsError = userNameIsError,
                emailText = emailText,
                emailHintText = emailHintText,
                emailIsError = emailIsError,
                passwordText = passwordText,
                passwordHintText = passwordHintText,
                passwordIsError = passwordIsError,
                confirmPasswordText = confirmPasswordText,
                confirmPasswordHintText = confirmPasswordHintText,
                confirmPasswordIsError = confirmPasswordIsError,
                onNameTextChanged = { viewModel.onNameTextChanged(it) },
                onUserNameTextChanged = { viewModel.onPhoneTextChanged(it) },
                onEmailTextChanged = { viewModel.onEmailTextChanged(it) },
                onPasswordTextChanged = { viewModel.onPasswordTextChanged(it) },
                onConfirmPasswordTextChanged = { viewModel.onConfirmPasswordTextChanged(it) },
            )
            Spacer(modifier = Modifier.height(16.dp))
            SignupButton(
                onSignupClick = {
                    viewModel.onClickSignup(
                        object : SignInCallback{
                            override fun onSignInSuccessful() {
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
                                // nothing
                            }

                            override fun onAuthCredentialsWrong() {
                                // nothing
                            }

                        }
                    )
                },
                isLoading = isLoading
            )
            LoginRow(navController = navController)
        }
    }
}

@Composable
private fun LoginRow(navController: NavController?){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ){
        Text(
            text = "Already have an account?",
            style = MaterialTheme.typography.subtitle2,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Login",
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.clickable(onClick = {
                navController?.navigate(loginScreenRoute){
                    popUpTo(signupScreenRoute){
                        inclusive = true
                    }
                }
            })
        )
    }
}

@Composable
private fun SignupButton(
    onSignupClick: () -> Unit = {},
    isLoading: Boolean
){
    Button(
        onClick = onSignupClick,
        shape = RoundedCornerShape(50),
        modifier = Modifier.fillMaxWidth(0.5f),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary
        )
    ) {
        if(!isLoading) {
            Text(
                text = "SIGN UP",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                textAlign = TextAlign.Center
            )
        } else{
            CircularProgressIndicator()
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun InfoFieldSection(
    nameText: String = "",
    nameHintText: String,
    nameIsError: Boolean,
    userNameText: String = "",
    userNameHintText: String,
    userNameIsError: Boolean,
    emailText: String = "",
    emailHintText: String,
    emailIsError: Boolean,
    passwordText: String = "",
    passwordHintText: String,
    passwordIsError: Boolean,
    confirmPasswordText: String = "",
    confirmPasswordHintText: String,
    confirmPasswordIsError: Boolean,
    onNameTextChanged: (String) -> Unit = {},
    onUserNameTextChanged: (String) -> Unit = {},
    onEmailTextChanged: (String) -> Unit = {},
    onPasswordTextChanged: (String) -> Unit = {},
    onConfirmPasswordTextChanged: (String) -> Unit = {},
){
    InfoTextField(
        hintText = nameHintText,
        leadingIcon = Icons.Outlined.Person,
        text = nameText,
        onTextChanged = { onNameTextChanged(it) },
        keyboardType = KeyboardType.Text,
        isError = nameIsError,
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoTextField(
        hintText = userNameHintText,
        leadingIcon = Icons.Outlined.AccountCircle,
        text = userNameText,
        onTextChanged = { onUserNameTextChanged(it) },
        keyboardType = KeyboardType.Text,
        isError = userNameIsError,
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoTextField(
        hintText = emailHintText,
        leadingIcon = Icons.Outlined.Email,
        text = emailText,
        onTextChanged = { onEmailTextChanged(it) },
        keyboardType = KeyboardType.Email,
        isError = emailIsError,
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoTextField(
        hintText = passwordHintText,
        leadingIcon = Icons.Outlined.Lock,
        text = passwordText,
        onTextChanged = { onPasswordTextChanged(it) },
        keyboardType = KeyboardType.Password,
        obfuscate = true,
        isError = passwordIsError,
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoTextField(
        hintText = confirmPasswordHintText,
        leadingIcon = Icons.Outlined.Lock,
        text = confirmPasswordText,
        onTextChanged = { onConfirmPasswordTextChanged(it) },
        keyboardType = KeyboardType.Password,
        obfuscate = true,
        isError = confirmPasswordIsError,
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun TitleBar(
    navController: NavController? = null
){
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        elevation = 0.dp,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = {
                navController?.navigate(loginScreenRoute) {
                    popUpTo(signupScreenRoute){
                        inclusive = true
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        }
    }
}
