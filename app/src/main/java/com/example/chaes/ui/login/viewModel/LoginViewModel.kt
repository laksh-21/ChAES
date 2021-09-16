package com.example.chaes.ui.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaes.repository.FirebaseAuthRepo
import com.example.chaes.repository.callbacks.SignInCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuthRepo: FirebaseAuthRepo
) : ViewModel(){
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

    fun onClickLogin(callback: SignInCallback){
        firebaseAuthRepo.login(
            emailText.value,
            passwordText.value,
            object : SignInCallback{
                override fun onSignInSuccessful() {
                    callback.onSignInSuccessful()
                }

                override fun onSignInFailed() {
                    callback.onSignInFailed()
                }

            }
        )
    }
}