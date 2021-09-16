package com.example.chaes.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.chaes.repository.callbacks.SignInCallback
import com.example.chaes.repository.callbacks.SignUpCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class FirebaseAuthRepo(app: Context) {
    private var auth : FirebaseAuth = Firebase.auth
    private var userLoggedIn: MutableLiveData<Boolean> = MutableLiveData(false)
    private var context: Context
    private var firebaseUser: FirebaseUser? = auth.currentUser

    init {
        userLoggedIn.value = auth.currentUser != null
        context = app
    }

    fun login(
        email: String?,
        password: String?,
        callback: SignInCallback
    ){
        if(email != null && password != null){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        Timber.d("Sign-in Successful")
                        userLoggedIn.value = true
                        callback.onSignInSuccessful()
                    } else {
                        Timber.d("Sign-in failed")
                        callback.onSignInFailed()
                    }
                }
        }
    }

    fun register(
        callback: SignUpCallback,
        email: String?,
        password: String?,
        name: String?
    ){
        if (email != null && password != null) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Timber.d("Sign-in Successful")
                        userLoggedIn.value = true
                        addUserInfo(name)
                        firebaseUser?.let { callback.onUserSignUpSuccessful(it.uid) }
                    } else {
                        Timber.d("Sign-in failed")
                        callback.onUserSignUpFailed()
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
                Timber.d("Profile Updated. Name: ${firebaseUser?.displayName}")
            }
        }
    }

    fun signOut(){
        auth.signOut()
        userLoggedIn.value = false
    }
}