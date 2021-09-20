package com.example.chaes.ui.login.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaes.repository.DatastoreRepo
import com.example.chaes.repository.FirebaseAuthRepo
import com.example.chaes.repository.FirestoreRepo
import com.example.chaes.repository.callbacks.SignInCallback
import com.example.chaes.repository.callbacks.UserNameCallback
import com.example.chaes.utilities.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
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
    val emailHintText = mutableStateOf("E-mail")
    val emailIsError = mutableStateOf(false)

    fun onEmailTextChanged(text: String){
        _emailText.value = text
    }

    // for the password editText
    private val _passwordText = MutableLiveData("")
    var passwordText: LiveData<String> = _passwordText
    val passwordHintText = mutableStateOf("Password")
    val passwordIsError = mutableStateOf(false)

    fun onPasswordTextChanged(text: String){
        _passwordText.value = text
    }

    private fun incorrectCredentials(){
        emailHintText.value = "Invalid Credentials"
        emailIsError.value = true
        passwordHintText.value = "Invalid Credentials"
        passwordIsError.value = true
    }

    val isLoading = mutableStateOf(false)

    fun onClickLogin(callback: SignInCallback){
        val emailInfo = Validator.emailValidator(emailText = _emailText.value)
        emailHintText.value = emailInfo.hintText
        emailIsError.value = emailInfo.isError

        val passwordInfo = Validator.passwordValidator(passwordText = _passwordText.value)
        passwordHintText.value = passwordInfo.hintText
        passwordIsError.value = passwordInfo.isError

        if(emailIsError.value || passwordIsError.value) return

        isLoading.value = true
        // asserting non-null is fine because validator checks it beforehand
        firebaseAuthRepo.login(
            emailText.value!!,
            passwordText.value!!,
            object : SignInCallback{
                override fun onSignInSuccessful() {
                    storeUserNameToDataStore()
                    callback.onSignInSuccessful()
                    isLoading.value = false
                }

                override fun onSignInFailed() {
                    callback.onSignInFailed()
                    isLoading.value = false
                }

                override fun onUserDoesNotExist() {
                    incorrectCredentials()
                    isLoading.value = false
                }

                override fun onAuthCredentialsWrong() {
                    incorrectCredentials()
                    isLoading.value = false
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