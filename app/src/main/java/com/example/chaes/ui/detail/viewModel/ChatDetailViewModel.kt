package com.example.chaes.ui.detail.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaes.models.Conversation
import com.example.chaes.models.Message
import com.example.chaes.repository.FirestoreRepo
import com.example.chaes.utilities.Constants.dummyUID
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    dbRepo: FirestoreRepo,
) : ViewModel(),
    EventListener<QuerySnapshot>
{

    // Listener code start
    private val query: Query = dbRepo.getMessagesQuery(dummyUID)
    private var registration: ListenerRegistration? = null

    private val _messages = MutableLiveData(ArrayList<Message>())
    val messages: LiveData<ArrayList<Message>> = _messages

    fun onCompose(){
        Timber.i("Messages are listened to")
        if(registration == null){
            registration = query.addSnapshotListener(this)
        }
    }

    fun deCompose(){
        Timber.i("Messages are not listened to")
        registration?.remove()
        registration = null
        _messages.value?.clear()
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
        _messages.value?.removeAt(change.oldIndex)
    }

    private fun onModified(change: DocumentChange) {
        val message: Message = change.document.toObject()

        if (change.oldIndex == change.newIndex) {
            // Item changed but remained in same position
            _messages.value?.set(change.oldIndex, message)
        } else {
            // Item changed and changed position
            _messages.value?.removeAt(change.oldIndex)
            _messages.value?.add(change.newIndex, message)
        }
    }

    private fun onAdded(change: DocumentChange) {
        val message: Message = change.document.toObject()
        _messages.value?.add(change.newIndex, message)
    }
    // listener code end

    // writer code start
    private val localReference: CollectionReference = dbRepo.getLocalMessagesReference(dummyUID)
    private val remoteReference: CollectionReference = dbRepo.getRemoteMessagesReference(dummyUID)
    fun addMessage(){
        Timber.i("Messages are being added")
        localReference.add(Message())
        remoteReference.add(Message())
    }
}