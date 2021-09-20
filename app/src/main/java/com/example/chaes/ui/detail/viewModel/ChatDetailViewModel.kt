package com.example.chaes.ui.detail.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chaes.models.Message
import com.example.chaes.repository.FirestoreRepo
import com.example.chaes.utilities.Constants.dummyUID
import com.example.chaes.utilities.Encryptor
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val dbRepo: FirestoreRepo,
) : ViewModel(),
    EventListener<QuerySnapshot>
{
    val messageText = mutableStateOf("")

    fun onMessageTextChanged(text: String){
        messageText.value = text
    }

    private var userUid: String? = dummyUID
    private var userName: String? = "Dummy Name"

    // Listener code start
    private lateinit var query: Query
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
        val decryptedMessage: Message = Encryptor.decryptMessage(encryptedMessage = message)
        val mutableMessageList = messages.value.toMutableList()
        if (change.oldIndex == change.newIndex) {
            // Item changed but remained in same position
            mutableMessageList[change.oldIndex] = decryptedMessage
        } else {
            // Item changed and changed position
            mutableMessageList.removeAt(change.oldIndex)
            mutableMessageList.add(change.newIndex, decryptedMessage)
        }
        messages.value = mutableMessageList.toList()
    }

    private fun onAdded(change: DocumentChange) {
        val message: Message = change.document.toObject()
        val decryptedMessage: Message = Encryptor.decryptMessage(encryptedMessage = message)
        val mutableMessageList = messages.value.toMutableList()
        mutableMessageList.add(change.newIndex, decryptedMessage)
        messages.value = mutableMessageList.toList()
        Timber.i("Message has been added: New Length is %d", messages.value.size)
    }
    // listener code end

    // writer code start
    fun addMessage(){
        if(messageText.value.isEmpty()){
            return
        }
        dbRepo.addMessage(
            messageText = messageText.value,
            userName = userName,
            userUid = userUid
        )
        messageText.value = ""
    }
    // writer code end
}