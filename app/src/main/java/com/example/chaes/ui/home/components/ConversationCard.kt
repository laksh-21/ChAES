package com.example.chaes.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chaes.models.Conversation
import com.example.chaes.utilities.TimeUtils

@ExperimentalMaterialApi
@Composable
fun ConversationCard(
    conversation: Conversation = Conversation(),
    onConversationClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        color = Color.Transparent,
        contentColor = MaterialTheme.colors.onPrimary,
        onClick = {
            onConversationClick()
        }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            val brightColor = LocalContentColor.current
            val faintColor = brightColor.copy(alpha = 0.75f)
            val textColor = if(conversation.isOpened) faintColor else brightColor
            val textWeight = if(conversation.isOpened) FontWeight.Normal else FontWeight.SemiBold
            Column(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(
                    text = conversation.name,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = conversation.lastMessage!!,
                    style = MaterialTheme.typography.body2,
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = textWeight,
                )
            }
            val timeText = if(conversation.lastUpdated == null)"•" else TimeUtils.convertDateToTime(conversation.lastUpdated)
            Text(
                text = timeText,
                style = MaterialTheme.typography.body2,
                color = textColor,
                fontWeight = textWeight,
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun ConversationCardDemo() {
    ConversationCard(
        conversation = Conversation()
    )
}