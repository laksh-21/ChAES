package com.example.chaes.ui.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaes.models.User
import com.example.chaes.repository.FirebaseAuthRepo
import com.example.chaes.repository.FirestoreRepo
import com.example.chaes.repository.callbacks.SignInCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuthRepo: FirebaseAuthRepo,
    private val dbRepo: FirestoreRepo,
) : ViewModel() {
    var userLoggedIn: LiveData<Boolean> = firebaseAuthRepo.userLoggedIn
    private val _userNavigated = MutableLiveData(false)
    val userNavigated: LiveData<Boolean> = _userNavigated

    fun onUserNavigated(){
        _userNavigated.value = true
    }

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

    fun onClickSignup(){
        firebaseAuthRepo.register(
            // callback when user is logged in or it failed
            object : SignInCallback{
                override fun onUserSignInSuccessful(uid: String) {
                    Timber.d("Sign-in successful")
                    addUserToFirestore(uid)
                }

                override fun onUserSignInFailed() {
                    Timber.d("Sign-in failed")
                }
            },
            emailText.value,
            passwordText.value,
            nameText.value,
        )
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