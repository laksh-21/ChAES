package com.example.chaes.login.components

import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun InfoTextField(
    hintText: String = "Hint",
    leadingIcon: ImageVector
){
    TextField(
        value = "",
        label = { Text(text = hintText) },
        onValueChange = {},
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = hintText) }
    )
}

@Preview
@Composable
fun InfoTextFieldDemo(){
    InfoTextField(
        leadingIcon = Icons.Outlined.Email
    )
}