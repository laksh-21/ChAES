package com.example.chaes.repository.callbacks

interface SignInCallback {
    fun onSignInSuccessful()
    fun onSignInFailed()
    fun onUserDoesNotExist()
    fun onAuthCredentialsWrong()
}