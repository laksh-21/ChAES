package com.example.chaes.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Message(
    val content: String = "Hello!",
    val senderName: String = "Sender",
    @ServerTimestamp
    val messageTime: Date? = null,
)