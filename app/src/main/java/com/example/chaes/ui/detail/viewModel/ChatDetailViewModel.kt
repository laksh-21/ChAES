package com.example.chaes.ui.detail.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
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
    private val dbRepo: FirestoreRepo,
) : ViewModel(),
    EventListener<QuerySnapshot>
{
    // Listener code start
    private lateinit var query: Query
    private var registration: ListenerRegistration? = null

    val messages = mutableStateOf(ArrayList<Message>())
//    val messages: LiveData<ArrayList<Message>> = _messages


    fun attachListener(uid: String){
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
        messages.value.clear()
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
        messages.value.removeAt(change.oldIndex)
    }

    private fun onModified(change: DocumentChange) {
        val message: Message = change.document.toObject()

        if (change.oldIndex == change.newIndex) {
            // Item changed but remained in same position
            messages.value[change.oldIndex] = message
        } else {
            // Item changed and changed position
            messages.value.removeAt(change.oldIndex)
            messages.value.add(change.newIndex, message)
        }
    }

    private fun onAdded(change: DocumentChange) {
        val message: Message = change.document.toObject()
        messages.value.add(change.newIndex, message)
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
    // writer code end
}