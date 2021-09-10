package com.example.chaes.ui.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chaes.models.Message

@Preview
@Composable
fun MessageFrom(
    message: Message = Message()
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .padding(end = 64.dp)
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            MessageSurface(message = message)
            TimeText()
        }
    }
}

@Composable
private fun MessageSurface(
    message: Message
) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(bottom = 4.dp),
        shape = RoundedCornerShape(
            topStartPercent = 25,
            topEndPercent = 25,
            bottomStartPercent = 0,
            bottomEndPercent = 25
        )
    ) {
        Column(
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = 16.dp,
                start = 16.dp,
                end = 10.dp
            )
        ) {
            Text(
                text = message.content,
                style = MaterialTheme.typography.body2
            )
        }

    }
}


@Composable
private fun TimeText() {
    Text(
        text = "Time",
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth(),
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.caption,
        color = Color.Gray.copy(alpha = 0.75f)
    )
}