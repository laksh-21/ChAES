package com.example.chaes.repository

import com.example.chaes.models.User
import com.example.chaes.repository.callbacks.UserExistsCallback
import com.example.chaes.utilities.Constants
import com.example.chaes.utilities.Constants.conversationsCollectionName
import com.example.chaes.utilities.Constants.conversationsPeopleCollectionName
import com.example.chaes.utilities.Constants.lastUpdatedFieldName
import com.example.chaes.utilities.Constants.messageTimeFieldName
import com.example.chaes.utilities.Constants.messagesCollectionName
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
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

    // conversations
    private var conversationsQuery: Query? = null

    fun getConversationQuery(): Query{
        if(conversationsQuery == null){
            conversationsQuery = db
                .collection(conversationsCollectionName).document(auth.uid!!)
                .collection(conversationsPeopleCollectionName).orderBy(lastUpdatedFieldName, Query.Direction.DESCENDING)
        }
        return conversationsQuery as Query
    }

    // messages
    fun getLocalMessagesReference(uid: String): CollectionReference {

        return db
            .collection(conversationsCollectionName).document(auth.uid!!)
            .collection(conversationsPeopleCollectionName).document(uid)
            .collection(messagesCollectionName)
    }

    fun getRemoteMessagesReference(uid: String): CollectionReference {

        return db
            .collection(conversationsCollectionName).document(uid)
            .collection(conversationsPeopleCollectionName).document(auth.uid!!)
            .collection(messagesCollectionName)
    }

    fun getMessagesQuery(uid: String?): Query{

        return db
            .collection(conversationsCollectionName).document(auth.uid!!)
            .collection(conversationsPeopleCollectionName).document(uid!!)
            .collection(messagesCollectionName)
            .orderBy(messageTimeFieldName, Query.Direction.DESCENDING)
    }

    fun doesUserExist(
        name: String?,
        callback: UserExistsCallback
    ){
        val userQuery = db
            .collection(Constants.usersCollectionName)
            .whereEqualTo("userName", name)
            .get()
        userQuery
            .addOnSuccessListener { documents ->
            if(documents == null || documents.isEmpty){
                callback.userDoesNotExist()
            } else{
                val uid = documents.documents[0].toObject<User>()?.uid
                callback.userExists(uid!!)
            } }
            .addOnFailureListener{
                callback.userCheckFailed()
            }
    }
}