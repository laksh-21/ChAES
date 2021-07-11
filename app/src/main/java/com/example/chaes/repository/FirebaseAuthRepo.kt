package com.example.chaes.repository

import android.content.Context
import android.widget.Toast
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

    fun login(email: String?, password: String?){
        if(email != null && password != null){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Sign-in successful", Toast.LENGTH_SHORT).show()
                        userLoggedIn.value = true
                    } else {
                        Toast.makeText(context, "Could not sign-in", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    fun register(email: String?, password: String?){
        if (email != null && password != null) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Sign-in successful", Toast.LENGTH_SHORT).show()
                        userLoggedIn.value = true
                    } else {
                        Toast.makeText(context, "Could not sign-in", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    fun signOut(){
        auth.signOut()
        userLoggedIn.value = false
    }
}