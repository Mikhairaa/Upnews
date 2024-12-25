package com.example.upnews.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.upnews.data.response.RegisterResponse
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
    private val USER_ADDRESS = stringPreferencesKey("user_address")

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
        Log.d("UserPreferences", "Token saved: $token")
    }

    fun getToken(): Flow<String?> {
        return dataStore.data
            .map { preferences -> preferences[ACCESS_TOKEN] }
    }
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

    suspend fun saveRegisterResponse(registerResponse: RegisterResponse) {
        dataStore.edit { preferences ->
            preferences[USER_EMAIL] = registerResponse.email ?: ""
            preferences[USER_ID] = registerResponse.id?.toString() ?: ""
            preferences[USER_ADDRESS] = registerResponse.alamat ?: ""
            preferences[USER_NAME] = registerResponse.nama ?: ""
        }
        Log.d("saveRegisterResponse", "Register response saved: $registerResponse")
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

    fun getUserId(): Flow<String?> {
        return dataStore.data
            .map { preferences -> preferences[USER_ID] }
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

