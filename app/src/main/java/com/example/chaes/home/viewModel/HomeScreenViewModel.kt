package com.example.chaes.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaes.models.Conversation
import com.example.chaes.repository.FirestoreRepo
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    dbRepo: FirestoreRepo
) : ViewModel(),
    EventListener<QuerySnapshot>
{
    // search user text
    private val _searchUserText = MutableLiveData("")
    var searchUserText: LiveData<String> = _searchUserText

    fun onSearchUserTextChanged(text: String){
        _searchUserText.value = text
    }

    // conversations processing
    private val query = dbRepo.getConversationQuery()
    private var registration: ListenerRegistration? = null

    private val _conversations = MutableLiveData(ArrayList<Conversation>())
    var conversations: LiveData<ArrayList<Conversation>> = _conversations

    fun onCompose(){
        if(registration == null){
            registration = query.addSnapshotListener(this)
        }
    }

    fun onDecompose(){
        registration?.remove()
        registration = null
        _conversations.value?.clear()
    }

    override fun onEvent(documentSnapshots: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if(error != null){
            Timber.w("onEvent:error $error")
            return
        }
        if(documentSnapshots == null){
            return
        }
        Timber.d("Changes found ${documentSnapshots.documentChanges.size}")

        for(change in documentSnapshots.documentChanges){
            when(change.type){
                DocumentChange.Type.ADDED -> onAdded(change)
                DocumentChange.Type.MODIFIED -> onModified(change)
                DocumentChange.Type.REMOVED -> onRemoved(change)
            }
        }
    }

    private fun onAdded(change: DocumentChange){
        val conversation: Conversation = change.document.toObject()
        _conversations.value?.add(change.newIndex, conversation)
    }
    private fun onModified(change: DocumentChange){
        val conversation: Conversation = change.document.toObject()
        if (change.oldIndex == change.newIndex) {
            // Item changed but remained in same position
            _conversations.value?.set(change.oldIndex, conversation)
        } else {
            // Item changed and changed position
            _conversations.value?.removeAt(change.oldIndex)
            _conversations.value?.add(change.newIndex, conversation)
        }
    }
    private fun onRemoved(change: DocumentChange){
        _conversations.value?.removeAt(change.oldIndex)
    }
}