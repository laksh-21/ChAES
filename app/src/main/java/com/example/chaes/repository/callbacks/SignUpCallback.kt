package com.example.chaes.repository.callbacks

interface SignUpCallback {
    fun onUserSignUpSuccessful(uid: String)
    fun onUserSignUpFailed()
}