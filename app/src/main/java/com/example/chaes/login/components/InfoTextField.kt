package com.example.chaes.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun InfoTextField(
    hintText: String = "Hint",
    leadingIcon: ImageVector,
){
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = "",
        label = { Text(text = hintText) },
        onValueChange = {},
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = hintText) },
        shape = RoundedCornerShape(25),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        maxLines = 1,
        singleLine = true,
    )
}

@Preview
@Composable
fun InfoTextFieldDemo(){
    InfoTextField(
        leadingIcon = Icons.Outlined.Email
    )
}