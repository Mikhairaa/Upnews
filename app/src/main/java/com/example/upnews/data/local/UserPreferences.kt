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
    private val USER_PASSWORD = stringPreferencesKey("user_password")

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

    fun getUser(): Flow<RegisterResponse?> {
        return dataStore.data.map { preferences ->
            val name = preferences[USER_NAME]
            val email = preferences[USER_EMAIL]
            val id = preferences[USER_ID]?.toIntOrNull()  // Konversi ID menjadi Int
            val alamat = preferences[USER_ADDRESS] ?: ""

            // Debug log untuk memeriksa data yang diambil
            Log.d("UserPreferences", "getUser: name=$name, email=$email, id=$id, alamat=$alamat")

            // Validasi data
            if (name != null && email != null && id != null && alamat.isNotEmpty()) {
                RegisterResponse(
                    nama = name,
                    id = id,
                    email = email,
                    alamat = alamat
                )
            } else {
                Log.e(
                    "UserPreferences",
                    "Data is missing or invalid: name=$name, email=$email, id=$id, alamat=$alamat"
                )
                null
            }
        }
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
    suspend fun savePassword(password: String) {
        dataStore.edit { preferences ->
            preferences[USER_PASSWORD] = password
        }
        Log.d("UserPreferences", "Password saved: $password")
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
