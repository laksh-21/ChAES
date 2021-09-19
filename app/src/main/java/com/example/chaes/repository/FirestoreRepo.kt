package com.example.chaes.repository

import android.content.Context
import android.widget.Toast
import com.example.chaes.models.Conversation
import com.example.chaes.models.User
import com.example.chaes.repository.callbacks.UserExistsCallback
import com.example.chaes.repository.callbacks.UserNameCallback
import com.example.chaes.utilities.Constants
import com.example.chaes.utilities.Constants.conversationsCollectionName
import com.example.chaes.utilities.Constants.conversationsPeopleCollectionName
import com.example.chaes.utilities.Constants.lastUpdatedFieldName
import com.example.chaes.utilities.Constants.messageTimeFieldName
import com.example.chaes.utilities.Constants.messagesCollectionName
import com.example.chaes.utilities.Encryptor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class FirestoreRepo(app: Context) {
    private val context: Context = app
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
    fun getConversationQuery(): Query{
        return db
            .collection(conversationsCollectionName).document(auth.currentUser!!.uid)
            .collection(conversationsPeopleCollectionName).orderBy(lastUpdatedFieldName, Query.Direction.DESCENDING)
    }

    // messages
    private fun getLocalMessagesReference(uid: String): CollectionReference {

        return db
            .collection(conversationsCollectionName).document(auth.currentUser!!.uid)
            .collection(conversationsPeopleCollectionName).document(uid)
            .collection(messagesCollectionName)
    }

    private fun getRemoteMessagesReference(uid: String): CollectionReference {

        return db
            .collection(conversationsCollectionName).document(uid)
            .collection(conversationsPeopleCollectionName).document(auth.currentUser!!.uid)
            .collection(messagesCollectionName)
    }

    // conversations
    private fun getLocalConversationReference(uid: String): DocumentReference {

        return db
            .collection(conversationsCollectionName).document(auth.currentUser!!.uid)
            .collection(conversationsPeopleCollectionName).document(uid)
    }

    private fun getRemoteConversationReference(uid: String): DocumentReference {

        return db
            .collection(conversationsCollectionName).document(uid)
            .collection(conversationsPeopleCollectionName).document(auth.currentUser!!.uid)
    }


    fun getMessagesQuery(uid: String?): Query{

        return db
            .collection(conversationsCollectionName).document(auth.currentUser!!.uid)
            .collection(conversationsPeopleCollectionName).document(uid!!)
            .collection(messagesCollectionName)
            .orderBy(messageTimeFieldName, Query.Direction.DESCENDING)
    }

    fun doesUserExist(
        userName: String?,
        callback: UserExistsCallback
    ){
        val userQuery = db
            .collection(Constants.usersCollectionName)
            .whereEqualTo("userName", userName)
            .get()
        userQuery
            .addOnSuccessListener { documents ->
            if(documents == null || documents.isEmpty){
                Toast.makeText(context, "User $userName does not exist", Toast.LENGTH_SHORT).show()
                callback.userDoesNotExist()
            } else{
                val user = documents.documents[0].toObject<User>()
                val uid = user?.uid
                val name = user?.name
                callback.userExists(uid!!, name!!)
            } }
            .addOnFailureListener{
                callback.userCheckFailed()
                Toast.makeText(context, "Something went wrong. Try again", Toast.LENGTH_SHORT).show()
            }
    }

    fun addMessage(
        messageText: String,
        userName: String?,
        userUid: String?,
    ){
        val localMessagesReference: CollectionReference = getLocalMessagesReference(userUid!!)
        val remoteMessagesReference: CollectionReference = getRemoteMessagesReference(userUid)

        val message = Encryptor.encryptMessage(
            message = messageText,
            senderName = Firebase.auth.uid!!
        )
        localMessagesReference.add(message)
        remoteMessagesReference.add(message)

        val localConversation = Conversation(
            name = userName!!,
            isOpened = true,
            uid = userUid,
            lastMessage = messageText
        )
        val remoteConversation = Conversation(
            name = auth.currentUser?.displayName!!,
            isOpened = false,
            uid = auth.uid!!,
            lastMessage = messageText
        )
        val encryptedLocalConversation = Encryptor.encryptConversation(localConversation)
        val encryptedRemoteConversation = Encryptor.encryptConversation(remoteConversation)
        updateConversationDocument(
            uid = userUid,
            localConversation = encryptedLocalConversation,
            remoteConversation = encryptedRemoteConversation
        )
    }

    private fun updateConversationDocument(
        uid: String,
        localConversation: Conversation,
        remoteConversation: Conversation
    ){
        val localConversationReference: DocumentReference = getLocalConversationReference(uid)
        val remoteConversationReference: DocumentReference = getRemoteConversationReference(uid)

        localConversationReference.set(
            localConversation,
            SetOptions.merge()
        )
        remoteConversationReference.set(
            remoteConversation,
            SetOptions.merge()
        )
    }

    fun setConversationOpened(uid: String){
        Timber.i("Conversation set: $uid")
        val localConversationRef: DocumentReference = getLocalConversationReference(uid = uid)
        val updatedData = hashMapOf(
            Constants.conversationIsOpenedFieldName to true
        )
        localConversationRef.set(
            updatedData,
            SetOptions.merge()
        )
    }

    fun getUserName(callback: UserNameCallback) {
        val userQuery = db
            .collection(Constants.usersCollectionName)
            .whereEqualTo("uid", auth.currentUser!!.uid)
            .get()
        userQuery
            .addOnSuccessListener { documents ->
                if(documents == null || documents.isEmpty){
                    callback.userNameGetFailed()
                } else{
                    val user = documents.documents[0].toObject<User>()
                    val userName = if(user == null) "Fail" else user.userName
                    Timber.d("User: $userName")
                    if (userName != null) {
                        callback.userNameGetSuccess(userName = userName)
                    } else{
                        callback.userNameGetFailed()
                    }
                } }
            .addOnFailureListener{
                callback.userNameGetFailed()
            }
    }
}