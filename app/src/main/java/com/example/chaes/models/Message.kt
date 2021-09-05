package com.example.chaes.models

import com.example.chaes.utilities.Constants
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Message(
    val content: String = "Hello!",
    val senderName: String = Constants.fromUserMessageName,
    @ServerTimestamp
    val messageTime: Date? = null,
)