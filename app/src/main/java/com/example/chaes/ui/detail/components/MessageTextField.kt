package com.example.chaes.ui.detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MessageTextField(
    modifier: Modifier = Modifier,
    messageText: String,
    onMessageTextChanged: (String) -> Unit,
    onClickSendButton: () -> Unit = {}
){
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            )
    ) {
        val buttonSize = 50.dp
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = messageText,
            onValueChange = { onMessageTextChanged(it) },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.White
            ),
            maxLines = 2,
            shape = RoundedCornerShape(100),
            placeholder = { Text(text = "Type your message..") },
        )
        Button(
            onClick = { onClickSendButton() },
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