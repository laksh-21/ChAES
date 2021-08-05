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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun MessageTextField(
    modifier: Modifier = Modifier
){
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val buttonSize = 50.dp
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {  },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            maxLines = 1,
            singleLine = true,
            shape = RoundedCornerShape(100),
            placeholder = { Text(text = "Type your message..") },
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