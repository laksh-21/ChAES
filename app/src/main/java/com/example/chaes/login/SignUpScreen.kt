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
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chaes.Screens
import com.example.chaes.login.components.HeaderSection
import com.example.chaes.login.components.InfoTextField

@Composable
fun SignUpScreen(
    navController: NavController?
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        HeaderSection(
            mainText = "Create Account",
            subText = "Please fill the inputs below."
        )
        InfoFieldSection()
        Spacer(modifier = Modifier.height(16.dp))
        LoginButton()
        LoginRow(navController = navController)
    }
}

@Composable
fun LoginRow(navController: NavController?){
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ){
        Text(
            text = "Already hve an account?",
            style = MaterialTheme.typography.subtitle2,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Login",
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.clickable(onClick = {
                navController?.navigate(Screens.LoginFlowScreens.Login.route){
                    popUpTo(Screens.LoginFlowScreens.SignUp.route)
                }
            })
        )
    }
}

@Composable
fun LoginButton(){
    Button(
        onClick = {},
        shape = RoundedCornerShape(50),
        modifier = Modifier.fillMaxWidth(0.5f)
    ) {
        Text(
            text = "SIGN UP",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun InfoFieldSection(){
    InfoTextField(
        hintText = "Full Name",
        leadingIcon = Icons.Outlined.Person,
        text = "",
        onTextChanged = {},
        keyboardType = KeyboardType.Text
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoTextField(
        hintText = "Phone",
        leadingIcon = Icons.Outlined.Phone,
        text = "",
        onTextChanged = {},
        keyboardType = KeyboardType.Phone
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoTextField(
        hintText = "Email",
        leadingIcon = Icons.Outlined.Email,
        text = "",
        onTextChanged = {},
        keyboardType = KeyboardType.Email
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoTextField(
        hintText = "Password",
        leadingIcon = Icons.Outlined.Lock,
        text = "",
        onTextChanged = {},
        keyboardType = KeyboardType.Password,
        obfuscate = true
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoTextField(
        hintText = "Confirm Password",
        leadingIcon = Icons.Outlined.Lock,
        text = "",
        onTextChanged = {},
        keyboardType = KeyboardType.Password,
        obfuscate = true
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview
@Composable
fun SignupScreenDemo(){
    SignUpScreen(navController = null)
}