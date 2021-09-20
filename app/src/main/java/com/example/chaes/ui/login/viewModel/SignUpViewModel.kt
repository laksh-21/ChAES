package com.example.chaes.ui.login.viewModel

import androidx.compose.runtime.mutableStateOf
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
import com.example.chaes.utilities.Validator
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
    val nameHintText = mutableStateOf("Name")
    val nameIsError = mutableStateOf(false)

    fun onNameTextChanged(text: String){
        _nameText.value = text
    }

    // phone_number text
    private val _userNameText = MutableLiveData("")
    var userNameText: LiveData<String> = _userNameText
    val userNameHintText = mutableStateOf("User Name")
    val userNameIsError = mutableStateOf(false)

    fun onPhoneTextChanged(text: String){
        _userNameText.value = text
    }

    // for email editText
    private val _emailText = MutableLiveData("")
    var emailText: LiveData<String> = _emailText
    val emailHintText = mutableStateOf("E-mail")
    val emailIsError = mutableStateOf(false)

    fun onEmailTextChanged(text: String){
        _emailText.value = text
    }

    // for password editText
    private val _passwordText = MutableLiveData("")
    var passwordText: LiveData<String> = _passwordText
    val passwordHintText = mutableStateOf("Password")
    val passwordIsError = mutableStateOf(false)

    fun onPasswordTextChanged(text: String){
        _passwordText.value = text
    }

    // for confirm_password text
    private val _confirmPasswordText = MutableLiveData("")
    var confirmPasswordText: LiveData<String> = _confirmPasswordText
    val confirmPasswordHintText = mutableStateOf("Confirm Password")
    val confirmPasswordIsError = mutableStateOf(false)

    fun onConfirmPasswordTextChanged(text: String){
        _confirmPasswordText.value = text
    }

    val isLoading = mutableStateOf(false)

    fun onClickSignup(
        callback: SignInCallback
    ){
        val emailInfo = Validator.signupEmailValidator(emailText = _emailText.value)
        emailHintText.value = emailInfo.hintText
        emailIsError.value = emailInfo.isError

        val passwordInfo = Validator.signupPasswordValidator(passwordText = _passwordText.value)
        passwordHintText.value = passwordInfo.hintText
        passwordIsError.value = passwordInfo.isError

        val confirmPasswordInfo = Validator.signupPasswordValidator(passwordText = _confirmPasswordText.value)
        confirmPasswordHintText.value = confirmPasswordInfo.hintText
        confirmPasswordIsError.value = confirmPasswordInfo.isError

        if(!_passwordText.value.isNullOrEmpty() && !_confirmPasswordText.value.isNullOrEmpty()){
            if(_passwordText.value != _confirmPasswordText.value){
                passwordsDoNotMatch()
            }
        }

        val nameInfo = Validator.nameValidator(nameText = _nameText.value)
        nameHintText.value = nameInfo.hintText
        nameIsError.value = nameInfo.isError

        val userNameInfo = Validator.userNameValidator(userNameText = _userNameText.value)
        userNameHintText.value = userNameInfo.hintText
        userNameIsError.value = userNameInfo.isError

        if(
            nameIsError.value ||
            userNameIsError.value ||
            emailIsError.value ||
            passwordIsError.value ||
            confirmPasswordIsError.value
        ){
            return
        }

        isLoading.value = true

        firebaseAuthRepo.register(
            // callback when user is logged in or it failed
            object : SignUpCallback{
                override fun onUserSignUpSuccessful(uid: String) {
                    Timber.d("Sign-in successful")
                    callback.onSignInSuccessful()
                    addUserToFirestore(uid)
                    saveUserNameToDataStore(userNameText.value)
                    isLoading.value = false
                }

                override fun onUserSignUpFailed() {
                    Timber.d("Sign-in failed")
                    callback.onSignInFailed()
                    isLoading.value = false
                }
            },
            emailText.value,
            passwordText.value,
            nameText.value,
        )
    }

    private fun passwordsDoNotMatch() {
        confirmPasswordHintText.value = "Passwords do not match"
        confirmPasswordIsError.value = true
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