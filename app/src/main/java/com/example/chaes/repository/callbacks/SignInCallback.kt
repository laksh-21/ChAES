package com.example.chaes.repository.callbacks

interface SignInCallback {
    fun onUserSignInSuccessful(uid: String)
    fun onUserSignInFailed()
}