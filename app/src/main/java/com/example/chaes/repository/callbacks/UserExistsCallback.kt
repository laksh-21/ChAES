package com.example.chaes.repository.callbacks

interface UserExistsCallback {
    fun userExists(
        uid: String,
        name: String
    )
    fun userDoesNotExist()
    fun userCheckFailed()
}