package com.example.chaes.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Conversation(
    val name: String = "empty_user",
    @field:JvmField // because prefix = "is"
    val isOpened: Boolean = true,
    @ServerTimestamp
    val lastUpdated: Date? = null,
    val uid: String = "empty123",
    val lastMessage: String? = "last message",
    val lastMessageIv: String? = "hahaIv",
    val lastMessageSalt: String? = "hahaSalt"
)
