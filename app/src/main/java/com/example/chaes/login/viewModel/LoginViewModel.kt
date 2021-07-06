package com.example.chaes.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel(){
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
}