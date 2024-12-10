package com.example.upnews.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.upnews.data.response.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    private val ACCESS_TOKEN = stringPreferencesKey("access_token")
    private val USER_NAME = stringPreferencesKey("user_name")
    private val USER_EMAIL = stringPreferencesKey("user_email")
    private val USER_ID = stringPreferencesKey("user_id")


    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
            preferences[ACCESS_TOKEN] = user.token ?: ""
            preferences[USER_NAME] = user.name ?: ""
            preferences[USER_EMAIL] = user.email ?: ""
            preferences[USER_ID] = user.id?.toString() ?: ""
        }
        Log.d("UserPreferences", "User data saved: $user")
    }

    suspend fun saveIsUserLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
        Log.d("UserPreferences", "saveIsUserLoggedIn: $isLoggedIn")
    }

    fun isUserLoggedIn(): Flow<Boolean> {
        return dataStore.data
            .map { preferences -> preferences[IS_LOGGED_IN] == true }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = false
            preferences[ACCESS_TOKEN] = ""
            preferences[USER_NAME] = ""
            preferences[USER_EMAIL] = ""
            preferences[USER_ID] = ""
        }
        Log.d("UserPreferences", "User logged out")
    }
    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
    }

    fun getToken(): Flow<String?> {
        return dataStore.data
            .map { preferences -> preferences[ACCESS_TOKEN] }
    }
    fun getUser(): Flow<User?> {
        return dataStore.data.map { preferences ->
            val name = preferences[USER_NAME]
            val email = preferences[USER_EMAIL]
            val id = preferences[USER_ID]?.toIntOrNull()
            val token = preferences[ACCESS_TOKEN]

            if (name != null && email != null && token != null) {
                User(name = name, email = email, id = id, token = token)
            } else {
                null
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(context: Context): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserPreferences(context.dataStore).also { INSTANCE = it }
            }
        }
    }
}

