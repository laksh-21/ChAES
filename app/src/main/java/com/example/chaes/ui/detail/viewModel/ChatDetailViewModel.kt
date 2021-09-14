package com.example.chaes.ui.detail.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chaes.models.Conversation
import com.example.chaes.models.Message
import com.example.chaes.repository.FirestoreRepo
import com.example.chaes.utilities.Constants.dummyUID
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val dbRepo: FirestoreRepo,
) : ViewModel(),
    EventListener<QuerySnapshot>
{
    private val auth: FirebaseAuth = Firebase.auth
    val messageText = mutableStateOf("")

    fun onMessageTextChanged(text: String){
        messageText.value = text
    }

    private var userUid: String? = dummyUID
    private var userName: String? = "Dummy Name"

    // Listener code start
    private lateinit var query: Query
    private lateinit var conversationQuery: Query
    private var registration: ListenerRegistration? = null

    val messages = mutableStateOf(listOf<Message>())

    fun handleInfo(
        uid: String?,
        name: String?
    ){
        userUid = uid
        userName = name
        attachListener(uid)
    }

    private fun attachListener(uid: String?){
        query = dbRepo.getMessagesQuery(uid)
        Timber.i("Messages are listened to")
        if(registration == null){
            registration = query.addSnapshotListener(this)
        }
    }

    fun detachListener(){
        Timber.i("Messages are not listened to")
        registration?.remove()
        registration = null
        messages.value = emptyList()
    }

    override fun onEvent(documentSnapshots: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if(error != null){
            Timber.w("onEvent:error $error")
            return
        }
        if(documentSnapshots == null){
            return
        }
        Timber.i("Changes found ${documentSnapshots.documentChanges.size}")

        for(change in documentSnapshots.documentChanges){
            when(change.type){
                DocumentChange.Type.ADDED -> onAdded(change)
                DocumentChange.Type.MODIFIED -> onModified(change)
                DocumentChange.Type.REMOVED -> onRemoved(change)
            }
        }
    }

    private fun onRemoved(change: DocumentChange) {
        val mutableMessageList = messages.value.toMutableList()
        mutableMessageList.removeAt(change.oldIndex)
        messages.value = mutableMessageList.toList()
    }

    private fun onModified(change: DocumentChange) {
        val message: Message = change.document.toObject()
        val mutableMessageList = messages.value.toMutableList()
        if (change.oldIndex == change.newIndex) {
            // Item changed but remained in same position
            mutableMessageList[change.oldIndex] = message
        } else {
            // Item changed and changed position
            mutableMessageList.removeAt(change.oldIndex)
            mutableMessageList.add(change.newIndex, message)
        }
        messages.value = mutableMessageList.toList()
    }

    private fun onAdded(change: DocumentChange) {
        val message: Message = change.document.toObject()
        val mutableMessageList = messages.value.toMutableList()
        mutableMessageList.add(change.newIndex, message)
        messages.value = mutableMessageList.toList()
        Timber.i("Message has been added: New Length is %d", messages.value.size)
    }
    // listener code end

    // writer code start
    private val localReference: CollectionReference = dbRepo.getLocalMessagesReference(dummyUID)
    private val remoteReference: CollectionReference = dbRepo.getRemoteMessagesReference(dummyUID)
    fun addMessage(){
        Timber.i("Messages are being added")
        val message = Message(
            content = messageText.value,
            senderName = Firebase.auth.uid!!
        )
        localReference.add(message)
        remoteReference.add(message)
        updateDocument()
        messageText.value = ""
    }

    private val localConversationRef: DocumentReference = dbRepo.getLocalConversationReference(dummyUID)
    private val remoteConversationRef: DocumentReference = dbRepo.getRemoteConversationReference(dummyUID)
    private fun updateDocument(){
        val localConversation = Conversation(
            name = userName!!,
            isOpened = false,
            uid = userUid!!,
            lastMessage = messageText.value
        )
        val remoteConversation = Conversation(
            name = auth.currentUser?.displayName!!,
            isOpened = false,
            uid = auth.uid!!,
            lastMessage = messageText.value
        )
        localConversationRef.set(
            localConversation,
            SetOptions.merge()
        )
        remoteConversationRef.set(
            remoteConversation,
            SetOptions.merge()
        )
    }
    // writer code end
}