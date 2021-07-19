package com.example.chaes.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ConversationCard(){
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        color = Color.Transparent,
        contentColor = MaterialTheme.colors.onPrimary,
    ){
        val faintColor = LocalContentColor.current.copy(alpha = 0.75f)
        Column {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = "Laksh Chauhan",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "Hello!",
                        style = MaterialTheme.typography.body2,
                        color = faintColor,
                    )
                }
                Text(
                    text = "12:47",
                    style = MaterialTheme.typography.body2,
                    color = faintColor
                )
            }
            Divider(
                modifier = Modifier.fillMaxHeight(),
                color = faintColor,
            )
        }
    }
}

@Preview
@Composable
fun ConversationCardDemo(){
    ConversationCard()
}