package com.example.chaes.login.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun InfoTextField(
    hintText: String = "Hint",
    leadingIcon: ImageVector,
    obfuscate: Boolean = false,
){
    var hidden by remember{ mutableStateOf(true) }
    val trailingIcon = if(hidden) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = "",
        label = { Text(text = hintText) },
        onValueChange = {},
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = hintText) },
        trailingIcon = {
                       if(obfuscate){
                           Icon(
                               imageVector = trailingIcon,
                               contentDescription = "Hide/Un-hide",
                               modifier = Modifier.clickable(enabled = true, onClick = { hidden = !hidden })
                           )
                       }
        },
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
        leadingIcon = Icons.Outlined.Email,
        obfuscate = true
    )
}