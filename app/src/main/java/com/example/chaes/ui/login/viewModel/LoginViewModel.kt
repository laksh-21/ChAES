package com.example.chaes.ui.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chaes.repository.DatastoreRepo
import com.example.chaes.repository.FirebaseAuthRepo
import com.example.chaes.repository.FirestoreRepo
import com.example.chaes.repository.callbacks.SignInCallback
import com.example.chaes.repository.callbacks.UserNameCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuthRepo: FirebaseAuthRepo,
    private val dbRepo: FirestoreRepo,
    private val datastoreRepo: DatastoreRepo
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
                    storeUserNameToDataStore()
                    callback.onSignInSuccessful()
                }

                override fun onSignInFailed() {
                    callback.onSignInFailed()
                }

            }
        )
    }

    fun storeUserNameToDataStore(){
        dbRepo.getUserName(
            object : UserNameCallback{
                override fun userNameGetSuccess(userName: String) {
                    Timber.d(userName)
                    runBlocking {
                        Timber.d("Just got here")
                        datastoreRepo.saveUserName(userName = userName)
                    }
                }

                override fun userNameGetFailed() {
                    // nothing yet
                    Timber.d("Not found")
                }

            }
        )
    }
}