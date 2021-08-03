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

@Preview
@Composable
fun MessageTo() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            modifier = Modifier
                .padding(start = 64.dp)
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            MessageSurface()
            TimeText()
        }
    }
}

@Composable
private fun TimeText() {
    Text(
        text = "Time",
        textAlign = TextAlign.End,
        modifier = Modifier.fillMaxWidth(),
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.caption,
        color = Color.Gray.copy(alpha = 0.75f)
    )
}

@Composable
private fun MessageSurface() {
    Surface(
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.padding(bottom = 4.dp),
        shape = RoundedCornerShape(
            topStartPercent = 25,
            topEndPercent = 25,
            bottomStartPercent = 25,
            bottomEndPercent = 0
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
                text = "You",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Message is this one where I shall get to know if it works properly or not",
                style = MaterialTheme.typography.body2
            )
        }

    }
}