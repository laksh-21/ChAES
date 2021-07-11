package com.example.chaes.login

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
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chaes.Screens
import com.example.chaes.login.components.HeaderSection
import com.example.chaes.login.components.InfoTextField
import com.example.chaes.login.viewModel.SignUpViewModel

@Composable
fun SignUpScreen(
    navController: NavController?,
    viewModel: SignUpViewModel = hiltViewModel(),
){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        val nameText: String by viewModel.nameText.observeAsState("")
        val phoneText: String by viewModel.phoneText.observeAsState("")
        val emailText: String by viewModel.emailText.observeAsState("")
        val passwordText: String by viewModel.passwordText.observeAsState("")
        val confirmPasswordText: String by viewModel.confirmPasswordText.observeAsState("")
        HeaderSection(
            mainText = "Create Account",
            subText = "Please fill the inputs below."
        )
        InfoFieldSection(
            nameText = nameText,
            phoneText = phoneText,
            emailText = emailText,
            passwordText = passwordText,
            confirmPasswordText = confirmPasswordText,
            onNameTextChanged = { viewModel.onNameTextChanged(it) },
            onPhoneTextChanged = { viewModel.onPhoneTextChanged(it) },
            onEmailTextChanged = { viewModel.onEmailTextChanged(it) },
            onPasswordTextChanged = { viewModel.onPasswordTextChanged(it) },
            onConfirmPasswordTextChanged = { viewModel.onConfirmPasswordTextChanged(it) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        SignupButton(onSignupClick = { viewModel.onClickSignup() })
        LoginRow(navController = navController)
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
                navController?.navigate(Screens.LoginFlowScreens.Login.route){
                    popUpTo(0)
                }
            })
        )
    }
}

@Composable
private fun SignupButton(onSignupClick: () -> Unit = {}){
    Button(
        onClick = onSignupClick,
        shape = RoundedCornerShape(50),
        modifier = Modifier.fillMaxWidth(0.5f)
    ) {
        Text(
            text = "SIGN UP",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            textAlign = TextAlign.Center
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun InfoFieldSection(
    nameText: String = "",
    phoneText: String = "",
    emailText: String = "",
    passwordText: String = "",
    confirmPasswordText: String = "",
    onNameTextChanged: (String) -> Unit = {},
    onPhoneTextChanged: (String) -> Unit = {},
    onEmailTextChanged: (String) -> Unit = {},
    onPasswordTextChanged: (String) -> Unit = {},
    onConfirmPasswordTextChanged: (String) -> Unit = {},
){
    InfoTextField(
        hintText = "Full Name",
        leadingIcon = Icons.Outlined.Person,
        text = nameText,
        onTextChanged = { onNameTextChanged(it) },
        keyboardType = KeyboardType.Text
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoTextField(
        hintText = "Phone",
        leadingIcon = Icons.Outlined.Phone,
        text = phoneText,
        onTextChanged = { onPhoneTextChanged(it) },
        keyboardType = KeyboardType.Phone
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoTextField(
        hintText = "Email",
        leadingIcon = Icons.Outlined.Email,
        text = emailText,
        onTextChanged = { onEmailTextChanged(it) },
        keyboardType = KeyboardType.Email
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoTextField(
        hintText = "Password",
        leadingIcon = Icons.Outlined.Lock,
        text = passwordText,
        onTextChanged = { onPasswordTextChanged(it) },
        keyboardType = KeyboardType.Password,
        obfuscate = true
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoTextField(
        hintText = "Confirm Password",
        leadingIcon = Icons.Outlined.Lock,
        text = confirmPasswordText,
        onTextChanged = { onConfirmPasswordTextChanged(it) },
        keyboardType = KeyboardType.Password,
        obfuscate = true
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview
@Composable
fun SignupScreenDemo(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        HeaderSection(
            mainText = "Signup",
            subText = "Demo"
        )
        InfoFieldSection()
        SignupButton()
        LoginRow(navController = null)
    }
}