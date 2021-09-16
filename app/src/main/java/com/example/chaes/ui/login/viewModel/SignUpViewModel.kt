package com.example.chaes.ui.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chaes.models.User
import com.example.chaes.repository.DatastoreRepo
import com.example.chaes.repository.FirebaseAuthRepo
import com.example.chaes.repository.FirestoreRepo
import com.example.chaes.repository.callbacks.SignInCallback
import com.example.chaes.repository.callbacks.SignUpCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuthRepo: FirebaseAuthRepo,
    private val dbRepo: FirestoreRepo,
    private val datastoreRepo: DatastoreRepo
) : ViewModel() {
    // full_name text
    private val _nameText = MutableLiveData("")
    var nameText: LiveData<String> = _nameText

    fun onNameTextChanged(text: String){
        _nameText.value = text
    }

    // phone_number text
    private val _userNameText = MutableLiveData("")
    var userNameText: LiveData<String> = _userNameText

    fun onPhoneTextChanged(text: String){
        _userNameText.value = text
    }

    // for email editText
    private val _emailText = MutableLiveData("")
    var emailText: LiveData<String> = _emailText

    fun onEmailTextChanged(text: String){
        _emailText.value = text
    }

    // for password editText
    private val _passwordText = MutableLiveData("")
    var passwordText: LiveData<String> = _passwordText

    fun onPasswordTextChanged(text: String){
        _passwordText.value = text
    }

    // for confirm_password text
    private val _confirmPasswordText = MutableLiveData("")
    var confirmPasswordText: LiveData<String> = _confirmPasswordText

    fun onConfirmPasswordTextChanged(text: String){
        _confirmPasswordText.value = text
    }

    fun onClickSignup(
        callback: SignInCallback
    ){
        firebaseAuthRepo.register(
            // callback when user is logged in or it failed
            object : SignUpCallback{
                override fun onUserSignUpSuccessful(uid: String) {
                    Timber.d("Sign-in successful")
                    callback.onSignInSuccessful()
                    addUserToFirestore(uid)
                    saveUserNameToDataStore(userNameText.value)
                }

                override fun onUserSignUpFailed() {
                    Timber.d("Sign-in failed")
                    callback.onSignInFailed()
                }
            },
            emailText.value,
            passwordText.value,
            nameText.value,
        )
    }

    private fun saveUserNameToDataStore(userName: String?) {
        viewModelScope.launch {
            if (userName != null) {
                datastoreRepo.saveUserName(userName)
            }
        }
    }

    fun addUserToFirestore(uid: String){
        val user = User(
            name = nameText.value,
            userName = userNameText.value,
            email = emailText.value,
            uid = uid,
        )
        dbRepo.addUser(user)
    }
}