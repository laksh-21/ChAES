package com.example.chaes.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthRepo(app: Context) {
    private var auth : FirebaseAuth = Firebase.auth
    private var userLoggedIn: MutableLiveData<Boolean> = MutableLiveData(false)
    private var context: Context

    init {
        userLoggedIn.value = auth.currentUser != null
        context = app
    }

    fun login(email: String, password: String){

    }

    fun register(email: String, password: String){

    }

    fun signOut(){
        auth.signOut()
        userLoggedIn.value = false
    }
}