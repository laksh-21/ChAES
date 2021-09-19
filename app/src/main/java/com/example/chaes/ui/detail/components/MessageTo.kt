package com.example.chaes.ui.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chaes.models.Message
import com.example.chaes.utilities.TimeUtils

@Preview
@Composable
fun MessageTo(
    message: Message = Message()
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        Column(
            modifier = Modifier
                .padding(start = 64.dp)
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.End
        ) {
            MessageSurface(message = message)
            if(message.messageTime == null){
                TimeText(time = "Sending")
            } else {
                TimeText(time = TimeUtils.convertDateToTime(message.messageTime))
            }
        }
    }
}

@Composable
private fun TimeText(time: String = "Time") {
    Text(
        text = time,
        textAlign = TextAlign.End,
        modifier = Modifier.fillMaxWidth(),
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.caption,
        color = Color.Gray.copy(alpha = 0.75f)
    )
}

@Composable
private fun MessageSurface(
    message: Message
) {
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
                text = message.content!!,
                style = MaterialTheme.typography.body2
            )
        }

    }
}