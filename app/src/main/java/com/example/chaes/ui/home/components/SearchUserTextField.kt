package com.example.chaes.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@Composable
fun SearchUserTextField(
    searchUserText: String,
    onSearchUserTextChanged: (String) -> Unit,
    onSearchUserClicked: () -> Unit,
    isLoading: Boolean
){
    Box(
        contentAlignment = Alignment.CenterEnd,
    ) {
        val buttonSize = 55.dp
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchUserText,
            onValueChange = { onSearchUserTextChanged(it) },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
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
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearchUserClicked() }
            )
        )
        Button(
            onClick = { onSearchUserClicked() },
            shape = CircleShape,
            modifier = Modifier.size(buttonSize),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary
            ),
        ) {
            if(!isLoading) {
                Icon(
                    imageVector = Icons.Filled.ArrowForwardIos,
                    contentDescription = "Go"
                )
            } else{
                CircularProgressIndicator()
            }
        }
    }
}