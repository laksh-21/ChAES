package com.example.chaes.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.datastore by preferencesDataStore(name = "settings")

class DatastoreRepo(app: Context) {
    private val context: Context = app

    companion object PreferencesKeys{
        val USER_NAME_KEY = stringPreferencesKey("user_name_key")
    }

    suspend fun saveUserName(userName: String){
        context.datastore.edit { preferences ->
            preferences[USER_NAME_KEY] = userName
        }
    }

    suspend fun getUserName(): String{
        val userName = context.datastore.data.map { preferences ->
            preferences[USER_NAME_KEY]
        }
        return userName.first()?:"Laksh"
    }
}