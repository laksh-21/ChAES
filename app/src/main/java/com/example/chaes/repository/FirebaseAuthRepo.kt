package com.example.chaes.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chaes.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class FirebaseAuthRepo(app: Context) {
    private var auth : FirebaseAuth = Firebase.auth
    private var db: FirebaseFirestore = Firebase.firestore
    private var _userLoggedIn: MutableLiveData<Boolean> = MutableLiveData(false)
    private var context: Context
    private var firebaseUser: FirebaseUser? = auth.currentUser

    init {
        _userLoggedIn.value = auth.currentUser != null
        context = app
    }

    fun login(email: String?, password: String?){
        if(email != null && password != null){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        Timber.d("Sign-in Successful")
                        _userLoggedIn.value = true
                    } else {
                        Timber.d("Sign-in failed")
                    }
                }
        }
    }

    fun register(email: String?, password: String?, name: String?){
        if (email != null && password != null) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Timber.d("Sign-in Successful")
                        _userLoggedIn.value = true
                        addUserInfo(name)
                    } else {
                        Timber.d("Sign-in failed")
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

    // TODO: Implement addUser to Firestore implementation
    private fun addToFirestore(user: User){

    }

    fun signOut(){
        auth.signOut()
        _userLoggedIn.value = false
    }
}