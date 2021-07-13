package com.example.chaes.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.chaes.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class FirebaseAuthRepo(app: Context) {
    private var auth : FirebaseAuth = Firebase.auth
    private var userLoggedIn: MutableLiveData<Boolean> = MutableLiveData(false)
    private var context: Context
    private var firebaseUser: FirebaseUser? = auth.currentUser

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

    fun register(email: String?, password: String?, name: String?){
        if (email != null && password != null) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Sign-in successful", Toast.LENGTH_SHORT).show()
                        userLoggedIn.value = true
                        addUserInfo(name)
                    } else {
                        Toast.makeText(context, "Could not sign-in", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun addUserInfo(name: String? = "User"){
        firebaseUser = auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }

        firebaseUser?.updateProfile(profileUpdates)?.addOnCompleteListener{ task ->
            if(task.isSuccessful){
                Toast.makeText(context, "Profile Updated", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signOut(){
        auth.signOut()
        userLoggedIn.value = false
    }
}