package com.example.chaes.repository

import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map
import timber.log.Timber

private val Context.datastore by preferencesDataStore(name = "settings")

class DatastoreRepo(app: Context) {
    private val context: Context = app

    companion object PreferencesKeys{
        val USER_NAME_KEY = stringPreferencesKey("user_name_key")
    }

    suspend fun saveUserName(userName: String){
        Timber.d("Yo I just got called! $userName")
        context.datastore.edit { preferences ->
            preferences[USER_NAME_KEY] = userName
        }
    }

    fun getUserName(): Flow<String> = context.datastore.data.map{
            it[USER_NAME_KEY]?:"User"
    }
}