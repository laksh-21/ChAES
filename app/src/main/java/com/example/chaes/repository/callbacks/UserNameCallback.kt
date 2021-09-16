package com.example.chaes.repository.callbacks

interface UserNameCallback {
    fun userNameGetSuccess(userName: String)
    fun userNameGetFailed()
}