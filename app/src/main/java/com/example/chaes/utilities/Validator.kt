package com.example.chaes.utilities

import android.util.Patterns

object Validator {
    fun emailValidator(emailText: String?): InputInfo{
        if(emailText.isNullOrEmpty()){
            return InputInfo(
                hintText = "Enter an E-mail",
                isError = true
            )
        }
        return InputInfo(
            hintText = "E-mail",
            isError = false
        )
    }

    fun signupEmailValidator(emailText: String?): InputInfo{
        if(emailText.isNullOrEmpty()){
            return InputInfo(
                hintText = "Enter an E-mail",
                isError = true
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            return InputInfo(
                hintText = "Invalid E-mail",
                isError = true
            )
        }
        return InputInfo(
            hintText = "E-mail",
            isError = false
        )
    }

    fun passwordValidator(passwordText: String?): InputInfo{
        if(passwordText.isNullOrEmpty()){
            return InputInfo(
                hintText = "Enter a password",
                isError = true
            )
        }
        return InputInfo(
            hintText = "Password",
            isError = false
        )
    }

    fun signupPasswordValidator(passwordText: String?): InputInfo{
        if(passwordText.isNullOrEmpty()){
            return InputInfo(
                hintText = "Enter a password",
                isError = true
            )
        }
        if(passwordText.length < 6){
            return InputInfo(
                hintText = "Password too short",
                isError = true
            )
        }
        return InputInfo(
            hintText = "Password",
            isError = false
        )
    }

    fun nameValidator(nameText: String?): InputInfo{
        if(nameText.isNullOrEmpty()){
            return InputInfo(
                hintText = "Enter a name",
                isError = true
            )
        }
        return InputInfo(
            hintText = "Name",
            isError = false
        )
    }

    fun userNameValidator(userNameText: String?): InputInfo{
        if(userNameText.isNullOrEmpty()){
            return InputInfo(
                hintText = "Enter a User Name",
                isError = true
            )
        }
        return InputInfo(
            hintText = "User Name",
            isError = false
        )
    }


}

data class InputInfo(
    val hintText: String,
    val isError: Boolean
)