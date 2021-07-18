package com.example.chaes.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun SearchUserTextField(
    searchUserText: String,
    onSearchUserTextChanged: (String) -> Unit,
){
    Box(
        contentAlignment = Alignment.CenterEnd,
    ) {
        val buttonSize = 50.dp
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchUserText,
            onValueChange = { onSearchUserTextChanged(it) },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            maxLines = 1,
            singleLine = true,
            shape = RoundedCornerShape(100),
            placeholder = { Text(text = "Enter a username..") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search"
                )
            },
        )
        Button(
            onClick = {},
            shape = CircleShape,
            modifier = Modifier.size(buttonSize),
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowForwardIos,
                contentDescription = "Go"
            )
        }
    }
}