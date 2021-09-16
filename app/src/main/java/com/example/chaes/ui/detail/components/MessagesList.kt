package com.example.chaes.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chaes.models.Message
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import timber.log.Timber

@Composable
fun MessagesList(
    modifier: Modifier = Modifier,
    messages: List<Message>,
){
    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        Timber.i("%s", (messages.size))
        items(messages) { message ->
            if (message.senderName == Firebase.auth.uid!!) {
                MessageTo(message = message)
            } else {
                MessageFrom(message = message)
            }
        }
    }
}