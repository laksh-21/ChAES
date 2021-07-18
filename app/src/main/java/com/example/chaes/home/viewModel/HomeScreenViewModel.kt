package com.example.chaes.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(

) : ViewModel() {
    // search user text
    private val _searchUserText = MutableLiveData("")
    var searchUserText: LiveData<String> = _searchUserText

    fun onSearchUserTextChanged(text: String){
        _searchUserText.value = text
    }
}