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

    fun getToken(): Flow<String?> {
        return dataStore.data
            .map { preferences -> preferences[ACCESS_TOKEN] }
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
                Log.e("UserPreferences", "Data is missing or invalid: name=$name, email=$email, id=$id, alamat=$alamat")
                null
            }
        }
    }


    fun isUserLoggedIn(): Flow<Boolean> {
        return dataStore.data
            .map { preferences -> preferences[IS_LOGGED_IN] == true }
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

