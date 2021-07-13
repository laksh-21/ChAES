package com.example.chaes.models

// Users will be a collection and each document will be a separate user
data class User(
    val name:String? = "User",
    val phone:String? = "1234567890",
    val email:String? = "testing@email.com",
)
