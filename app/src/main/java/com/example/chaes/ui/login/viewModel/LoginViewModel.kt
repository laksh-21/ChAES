package com.example.chaes.ui.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaes.repository.FirebaseAuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuthRepo: FirebaseAuthRepo
) : ViewModel(){
    var userLoggedIn: LiveData<Boolean> = firebaseAuthRepo.userLoggedIn
    private val _userNavigated = MutableLiveData(false)
    val userNavigated: LiveData<Boolean> = _userNavigated

    fun onUserNavigated(){
        _userNavigated.value = true
    }

    // for the E-mail editText
    private val _emailText = MutableLiveData("")
    var emailText: LiveData<String> = _emailText

    fun onEmailTextChanged(text: String){
        _emailText.value = text
    }

    // for the password editText
    private val _passwordText = MutableLiveData("")
    var passwordText: LiveData<String> = _passwordText

    fun onPasswordTextChanged(text: String){
        _passwordText.value = text
    }

    fun onClickLogin(){
        firebaseAuthRepo.login(emailText.value, passwordText.value)
    }
}