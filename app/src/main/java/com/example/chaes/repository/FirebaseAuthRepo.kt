package com.example.chaes.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthRepo {
    private var auth : FirebaseAuth = Firebase.auth
    private lateinit var userLoggedIn: MutableLiveData<Boolean>

    init {
        userLoggedIn.value = auth.currentUser != null
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