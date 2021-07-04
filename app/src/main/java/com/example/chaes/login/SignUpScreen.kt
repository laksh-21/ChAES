package com.example.chaes.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
    InfoFieldSection()
}