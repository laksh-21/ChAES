package com.example.chaes.repository

import android.content.Context
import com.example.chaes.models.User
import com.example.chaes.utilities.Constants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class FirestoreRepo {
    private var db: FirebaseFirestore = Firebase.firestore
    private var auth : FirebaseAuth = Firebase.auth

    fun addUser(user: User){
        val docRef = auth.currentUser?.let { currentUser ->
            db.collection(Constants.usersCollectionName).document(currentUser.uid)
        }
        docRef?.set(user)?.apply {
            addOnSuccessListener { Timber.d("User Added") }
            addOnCanceledListener { Timber.d("User add failed") }
        }
    }
}