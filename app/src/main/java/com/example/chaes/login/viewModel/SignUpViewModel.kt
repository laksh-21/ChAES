package com.example.chaes.login.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaes.repository.FirebaseAuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val randomString: String
//    firebaseAuthRepo: FirebaseAuthRepo,
) : ViewModel() {
    // full_name text
    private val _nameText = MutableLiveData("")
    var nameText: LiveData<String> = _nameText

    fun onNameTextChanged(text: String){
        _nameText.value = text
        Log.d("AppDebug", randomString)
    }

    // phone_number text
    private val _phoneText = MutableLiveData("")
    var phoneText: LiveData<String> = _phoneText

    fun onPhoneTextChanged(text: String){
        _phoneText.value = text
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
}