package com.example.chaes.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Conversation(
    val userName: String? = "empty_user",
    val isOpened: Boolean? = true,
    @ServerTimestamp
    val lastUpdated: Date? = null,
    val uid: String? = "empty123"
)
