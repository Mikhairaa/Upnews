package com.example.upnews.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upnews.data.local.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.upnews.data.response.GetProfileResponse
import com.example.upnews.data.response.UserProfile
import com.example.upnews.data.retrofit.ApiConfig
import retrofit2.HttpException
import java.io.IOException

class ProfileViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val _profileState = MutableStateFlow<UserProfile?>(null)
    val profileState: StateFlow<UserProfile?> get() = _profileState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _logoutState = MutableStateFlow(false)
    val logoutState: StateFlow<Boolean> get() = _logoutState

    fun fetchProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            // Mengambil token dari UserPreferences
            userPreferences.getToken().collect { token ->

                if (token.isNullOrEmpty()) {
                    _errorMessage.value = "No valid token found"
                    _isLoading.value = false
                    return@collect
                }
                try {
                    // Menggunakan token untuk mengambil data profil
                    val response = ApiConfig.apiService.getProfile("Bearer $token")
                    Log.d("ProfileViewModel", "API Response: ${response.message}")
                    val user = response.user
                    if (user != null) {
                        _profileState.value = user
                    } else {
                        _errorMessage.value = "Failed to fetch profile: ${response.message ?: "Unknown error"}"
                    }
                } catch (e: HttpException) {
                    _errorMessage.value = "HTTP Error: ${e.code()} - ${e.message()}"
                    Log.e("ProfileViewModel", "HTTP Error: ${e.response()?.errorBody()?.string()}", e)
                } catch (e: IOException) {
                    _errorMessage.value = "Network error: Please check your connection"
                    Log.e("ProfileViewModel", "Network Error", e)
                } catch (e: Exception) {
                    _errorMessage.value = "Unexpected error: ${e.localizedMessage}"
                    Log.e("ProfileViewModel", "Unexpected Error", e)
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.logout()
            _logoutState.value = true // Menandakan logout berhasil
            Log.d("ProfileViewModel", "User logged out successfully")
        }
    }
}
