package com.example.chaes.repository.callbacks

interface SignUpCallback {
    fun onUserSignInSuccessful(uid: String)
    fun onUserSignInFailed()
}