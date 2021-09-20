package com.example.chaes.ui.home.viewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaes.models.Conversation
import com.example.chaes.repository.DatastoreRepo
import com.example.chaes.repository.FirebaseAuthRepo
import com.example.chaes.repository.FirestoreRepo
import com.example.chaes.repository.callbacks.UserExistsCallback
import com.example.chaes.utilities.Encryptor
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val dbRepo: FirestoreRepo,
    private val authRepo: FirebaseAuthRepo,
    private val datastoreRepo: DatastoreRepo
) : ViewModel(),
    EventListener<QuerySnapshot>
{
    // user name
    @Composable
    fun getUserName(): State<String> {
        return datastoreRepo.getUserName().collectAsState(initial = "User")
    }

    // search user text
    private val _searchUserText = MutableLiveData("")
    var searchUserText: LiveData<String> = _searchUserText

    fun onSearchUserTextChanged(text: String){
        _searchUserText.value = text
    }

    // Drop-down menu
    val menuOpened = mutableStateOf(false)
    fun onClickMoreDots(){
        menuOpened.value = !menuOpened.value
    }

    fun onClickSignOut(){
        authRepo.signOut()
    }

    // conversations processing
    private val query = dbRepo.getConversationQuery()
    private var registration: ListenerRegistration? = null

    val conversations = mutableStateOf(listOf<Conversation>())
//    var conversations: LiveData<ArrayList<Conversation>> = _conversations

    fun attachListener(){
        if(registration == null){
            registration = query.addSnapshotListener(this)
        }
    }

    fun detachListener(){
        registration?.remove()
        registration = null
        conversations.value = emptyList()
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
        val mutableConversationList = conversations.value.toMutableList()
        val conversation: Conversation = change.document.toObject()
        val decryptedConversation: Conversation = Encryptor.decryptConversation(conversation = conversation)
        mutableConversationList.add(change.newIndex, decryptedConversation)
        conversations.value = mutableConversationList.toList()
    }
    private fun onModified(change: DocumentChange){
        val conversation: Conversation = change.document.toObject()
        val decryptedConversation: Conversation = Encryptor.decryptConversation(conversation = conversation)
        val mutableConversationList = conversations.value.toMutableList()
        if (change.oldIndex == change.newIndex) {
            // Item changed but remained in same position
            mutableConversationList[change.oldIndex] = decryptedConversation
        } else {
            // Item changed and changed position
            mutableConversationList.removeAt(change.oldIndex)
            mutableConversationList.add(change.newIndex, decryptedConversation)
        }
        conversations.value = mutableConversationList.toList()
    }
    private fun onRemoved(change: DocumentChange){
        val mutableConversationList = conversations.value.toMutableList()
        mutableConversationList.removeAt(change.oldIndex)
        conversations.value = mutableConversationList.toList()
    }

    val isLoading = mutableStateOf(false)
    fun onSearchUserClicked(callback: UserExistsCallback){
        if(_searchUserText.value.isNullOrEmpty()){
            return
        }
        isLoading.value = true
        dbRepo.doesUserExist(
            userName = searchUserText.value,
            callback = object : UserExistsCallback{
                override fun userExists(
                    uid: String,
                    name: String
                ) {
                    Timber.d("User does exist")
                    callback.userExists(uid, name)
                    isLoading.value = false
                }

                override fun userDoesNotExist() {
                    Timber.d("User does not exist")
                    callback.userDoesNotExist()
                    isLoading.value = false
                }

                override fun userCheckFailed() {
                    Timber.d("Something went wrong. Try again")
                    callback.userCheckFailed()
                    isLoading.value = false
                }

            }
        )
    }

    fun setConversationOpened(uid: String){
        dbRepo.setConversationOpened(uid)
    }
}